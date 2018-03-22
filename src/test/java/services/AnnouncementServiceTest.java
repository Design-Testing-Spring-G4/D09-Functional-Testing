
package services;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Announcement;
import domain.Rendezvous;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AnnouncementServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private AnnouncementService	announcementService;

	//Supporting services

	@Autowired
	private RendezvousService	rendezvousService;


	//Test template

	protected void Template(final String username, final String title, final String description, final String title2, final String description2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation
			final Announcement announcement = this.announcementService.create();
			announcement.setTitle(title);
			announcement.setDescription(description);
			final Rendezvous rendezvous = this.rendezvousService.findOne(this.getEntityId("rendezvous1"));
			announcement.setRendezvous(rendezvous);
			final Announcement saved = this.announcementService.save(announcement);

			//Listing
			Collection<Announcement> cl = this.announcementService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.announcementService.findOne(saved.getId()));

			//Edition
			saved.setTitle(title2);
			saved.setDescription(description2);
			final Announcement saved2 = this.announcementService.save(saved);

			//Deletion
			this.announcementService.delete(saved2);
			cl = this.announcementService.findAll();
			Assert.isTrue(!cl.contains(saved));

			this.unauthenticate();
		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	//Driver for multiple tests under the same template.

	@Test
	public void Driver() {

		final Object testingData[][] = {

			//Test #01: Correct execution of test. Expected true.
			{
				"user1", "testAnnouncement", "testDescription", "editAnnouncement", "editDescription", null
			},

			//Test #02: Attempt to execute the test by anonymous user. Expected false.
			{
				null, "testAnnouncement", "testDescription", "editAnnouncement", "editDescription", IllegalArgumentException.class
			},

			//Test #03: Attempt to create an announcement with blank values. Expected false.
			{
				"user1", "", "", "editAnnouncement", "editDescription", ConstraintViolationException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}
}
