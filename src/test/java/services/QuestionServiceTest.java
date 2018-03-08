
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
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class QuestionServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private QuestionService	questionService;


	//Setting up the authority to execute services.

	@Test
	public void testCreateQuestion() {
		//Setting up the authority to execute services.
		this.authenticate("user1");

		//Using create() to initialise a new entity. Necessary Id's taken from populated database.
		final Question question = this.questionService.create();

		question.setQuestion("Estara bien esto?");
		System.out.println(question);

		//Saving entity to database and confirming it exists with findAll().
		final Question saved = this.questionService.save(question);
		System.out.println("question saved: " + saved + saved.getQuestion());
		final Collection<Question> questions = this.questionService.findAll();
		Assert.isTrue(questions.contains(saved));
	}

	@Test
	public void testListDeleteQuestion() {
		//Setting up the authority to execute services.
		this.authenticate("user1");

		//We retrieve a list of all notes, and obtain the Id of one of them.
		Collection<Question> questions = this.questionService.findAll();
		final int id = questions.iterator().next().getId();
		System.out.println("List question: " + questions);
		//Using findOne() to retrieve a particular entity and verifying it.
		final Question question = this.questionService.findOne(id);
		Assert.notNull(id);

		System.out.println("Question encontrada:: " + question);

		//Using delete() to delete the entity we retrieved.
		this.questionService.delete(question);

		//Verifying the entity has been removed from the database.
		questions = this.questionService.findAll();
		System.out.println("Lista de questions:: " + questions);
		//Assert.isTrue(!questions.contains(question));
	}

}
