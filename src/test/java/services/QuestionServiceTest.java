
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
import domain.Question;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class QuestionServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private QuestionService	questionService;


	//Test template

	protected void Template(final String username, final String text, final String text2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation
			final Question question = this.questionService.create(this.getEntityId("rendezvous1"));
			question.setText(text);
			final Question saved = this.questionService.save(question);

			//Listing
			Collection<Question> cl = this.questionService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.questionService.findOne(saved.getId()));

			//Edition
			saved.setText(text2);
			final Question saved2 = this.questionService.save(saved);

			//Deletion
			this.questionService.delete(saved2);
			cl = this.questionService.findAll();
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
				"user1", "testQuestion", "testQuestion", null
			},

			//Test #02:  Attempt to execute the test by anonymous user. Expected false.
			{
				null, "testQuestion", "testQuestion", IllegalArgumentException.class
			},

			//Test #03:  Attempt to execute the test by unauthorized user. Expected false.
			{
				"manager1", "testQuestion", "testQuestion", IllegalArgumentException.class
			},

			//Test #04: Attempt to create a question with blank text. Expected false.
			{
				"user1", "", "testQuestion", IllegalArgumentException.class
			},

			//Test #05: Attempt to edit a question with null values. Expected false.
			{
				"user1", "testQuestion", null, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
}
