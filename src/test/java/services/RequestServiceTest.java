
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Rendezvous;
import domain.Request;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RequestServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private RequestService		requestService;

	//Supporting services

	@Autowired
	private RendezvousService	rendezvousService;


	//Test template

	protected void Template(final String username, final String comments, final String comments2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation
			final Request request = this.requestService.create(this.getEntityId("service1"));
			request.setComments(comments);
			final Rendezvous rendezvous = this.rendezvousService.findOne(this.getEntityId("rendezvous1"));
			request.setRendezvous(rendezvous);
			final Request saved = this.requestService.save(request);

			//Listing
			Collection<Request> cl = this.requestService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.requestService.findOne(saved.getId()));

			//Edition
			saved.setComments(comments2);
			final Request saved2 = this.requestService.save(saved);

			//Deletion
			this.requestService.delete(saved2);
			cl = this.requestService.findAll();
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
				"user2", "This is a test", "This is a test", null
			},

			//Test #03: Attempt to execute the test by anonymous user. Expected false.
			{
				null, "This is a test", "This is a test", IllegalArgumentException.class
			},

			//Test #04: Attempt to execute the test by unauthorized user. Expected false.
			{
				"manager1", "This is a test", "This is a test", ClassCastException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
}
