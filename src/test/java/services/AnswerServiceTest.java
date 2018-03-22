
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
import domain.Answer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AnswerServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private AnswerService	answerService;


	//Test template

	protected void Template(final String username, final String ans, final String ans2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation
			final Answer answer = this.answerService.create(this.getEntityId("question1"));
			answer.setAnswer(ans);
			final Answer saved = this.answerService.save(answer);

			//Listing
			Collection<Answer> cl = this.answerService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.answerService.findOne(saved.getId()));

			//Edition
			saved.setAnswer(ans2);
			final Answer saved2 = this.answerService.save(saved);

			//Deletion
			this.answerService.delete(saved2);
			cl = this.answerService.findAll();
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
				"user1", "testAnswer", "editAnswer", null
			},

			//Test #02:  Attempt to execute the test by anonymous user. Expected false.
			{
				null, "testAnswer", "editAnswer", IllegalArgumentException.class
			},

			//Test #03:  Attempt to execute the test by unauthorized user. Expected false.
			{
				"manager1", "testAnswer", "editAnswer", IllegalArgumentException.class
			},

			//Test #04: Attempt to create a answer with blank text. Expected false.
			{
				"user1", "", "editAnswer", IllegalArgumentException.class
			},

			//Test #05: Attempt to edit a answer with null values. Expected false.
			{
				"user1", "testAnswer", null, IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
}
