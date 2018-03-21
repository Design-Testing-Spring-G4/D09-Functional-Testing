
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
import domain.Comment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CommentServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private CommentService	commentService;


	//Test template

	protected void Template(final String username, final String text, final String picture, final String text2, final String picture2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation
			final Comment comment = this.commentService.create();
			comment.setText(text);
			comment.setPicture(picture);
			final Comment saved = this.commentService.save(comment);

			//Listing
			Collection<Comment> cl = this.commentService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.commentService.findOne(saved.getId()));

			//Edition
			saved.setText(text2);
			saved.setPicture(picture2);
			final Comment saved2 = this.commentService.save(saved);

			//Deletion
			this.commentService.delete(saved2);
			cl = this.commentService.findAll();
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
					
			//Test #01: . Expected true.
			{, null},
				
			//Test #02: . Expected false.
			{, IllegalArgumentException.class},
				
			//Test #03: . Expected false.
			{, IllegalArgumentException.class}

		};
		for (int i = 0; i < testingData.length; i++)
			this.Template(() testingData[i][0], (Class<?>) testingData[i][]);
	}
}
