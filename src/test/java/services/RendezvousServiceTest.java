
package services;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Rendezvous;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RendezvousServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private RendezvousService	rendezvousService;


	//Test template

	protected void Template(final String username, final String name, final String description, final Date moment, final String picture, final String coordinates, final boolean finalMode, final boolean adultOnly, final String name2,
		final String description2, final Date moment2, final String picture2, final String coordinates2, final boolean finalMode2, final boolean adultOnly2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation
			final Rendezvous rendezvous = this.rendezvousService.create();
			rendezvous.setName(name);
			rendezvous.setDescription(description);
			rendezvous.setMoment(moment);
			rendezvous.setPicture(picture);
			rendezvous.setCoordinates(coordinates);
			rendezvous.setFinalMode(finalMode);
			rendezvous.setAdultOnly(adultOnly);
			final Rendezvous saved = this.rendezvousService.save(rendezvous);

			//Listing
			Collection<Rendezvous> cl = this.rendezvousService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.rendezvousService.findOne(saved.getId()));

			//Edition
			saved.setName(name2);
			saved.setDescription(description2);
			saved.setMoment(moment2);
			saved.setPicture(picture2);
			saved.setCoordinates(coordinates2);
			saved.setFinalMode(finalMode2);
			saved.setAdultOnly(adultOnly2);
			final Rendezvous saved2 = this.rendezvousService.save(saved);

			//Deletion
			this.rendezvousService.delete(saved2);
			cl = this.rendezvousService.findAll();
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
				"user1", "testRendezvous", "testDescription", new Date(1539158400000L), "https://tinyurl.com/adventure-meetup", "+90, +90", false, true, "editRendezvous", "editDescription", new Date(1539158400000L),
				"http://eskipaper.com/images/savannah-5.jpg", "+0, 180", false, false, null
			},

			//Test #02: Attempt to execute the test by anonymous user. Expected false.
			{
				null, "testRendezvous", "testDescription", new Date(1539158400000L), "https://tinyurl.com/adventure-meetup", "+90, +90", false, true, "editRendezvous", "editDescription", new Date(1539158400000L),
				"http://eskipaper.com/images/savannah-5.jpg", "+0, 180", false, false, IllegalArgumentException.class
			},

			//Test #03: Attempt to execute the test by unauthorized user. Expected false.
			{
				"manager1", "testRendezvous", "testDescription", new Date(1539158400000L), "https://tinyurl.com/adventure-meetup", "+90, +90", false, true, "editRendezvous", "editDescription", new Date(1539158400000L),
				"http://eskipaper.com/images/savannah-5.jpg", "+0, 180", false, false, IllegalArgumentException.class
			},

			//Test #04: Attempt to create a rendezvous with blank text. Expected false.
			{
				"user1", "", "", new Date(1539158400000L), "https://tinyurl.com/adventure-meetup", "+90, +90", false, true, "editRendezvous", "editDescription", new Date(1539158400000L), "http://eskipaper.com/images/savannah-5.jpg", "+0, 180", false,
				false, IllegalArgumentException.class
			},

			//Test #05: Attempt to create a rendezvous with a past date. Expected false.
			{
				"user1", "testRendezvous", "testDescription", new Date(1239158400000L), "https://tinyurl.com/adventure-meetup", "+90, +90", false, true, "editRendezvous", "editDescription", new Date(1539158400000L),
				"http://eskipaper.com/images/savannah-5.jpg", "+0, 180", false, false, IllegalArgumentException.class
			},

			//Test #06: Creation of a rendezvous on final mode and posterior attempt to edit and delete it. Expected false.
			{
				"user1", "testRendezvous", "testDescription", new Date(1539158400000L), "https://tinyurl.com/adventure-meetup", "+90, +90", true, true, "editRendezvous", "editDescription", new Date(1539158400000L),
				"http://eskipaper.com/images/savannah-5.jpg", "+0, 180", false, false, IllegalArgumentException.class
			},

			//Test #07: Attempt to edit a rendezvous with null values. Expected false.
			{
				"user1", "testRendezvous", "testDescription", new Date(1539158400000L), "https://tinyurl.com/adventure-meetup", "+90, +90", false, true, null, null, new Date(1539158400000L), "http://eskipaper.com/images/savannah-5.jpg", "+0, 180", false,
				false, IllegalArgumentException.class
			},

			//Test #08: Editing a rendezvous into final mode and attempting to delete it. Expected false.
			{
				"user1", "testRendezvous", "testDescription", new Date(1539158400000L), "https://tinyurl.com/adventure-meetup", "+90, +90", false, true, "editRendezvous", "editDescription", new Date(1539158400000L),
				"http://eskipaper.com/images/savannah-5.jpg", "+0, 180", true, false, IllegalArgumentException.class
			},

			//Test #09: Attempt to create a rendezvous with a null date. Expected false.
			{
				"user1", "testRendezvous", "testDescription", null, "https://tinyurl.com/adventure-meetup", "+90, +90", false, true, "editRendezvous", "editDescription", new Date(1539158400000L), "http://eskipaper.com/images/savannah-5.jpg", "+0, 180",
				false, false, IllegalArgumentException.class
			},

			//Test #10: Attempt to edit a rendezvous with an invalid picture. Expected false.
			{
				"user1", "testRendezvous", "testDescription", new Date(1539158400000L), "https://tinyurl.com/adventure-meetup", "+90, +90", false, true, "editRendezvous", "editDescription", new Date(1539158400000L), "invalidPicture", "+0, 180", false,
				false, IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (boolean) testingData[i][6], (boolean) testingData[i][7],
				(String) testingData[i][8], (String) testingData[i][9], (Date) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (boolean) testingData[i][13], (boolean) testingData[i][14], (Class<?>) testingData[i][15]);
	}
}
