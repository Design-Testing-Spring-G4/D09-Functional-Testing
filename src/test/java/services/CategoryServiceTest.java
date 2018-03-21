
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
import domain.Category;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CategoryServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private CategoryService	categoryService;


	//Test template

	protected void Template(final String username, final String name, final String description, final String name2, final String description2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation
			final Category category = this.categoryService.create();
			category.setName(name);
			category.setDescription(description);
			final Category parent = this.categoryService.findOne(this.getEntityId("category1"));
			category.setParent(parent);
			final Category saved = this.categoryService.save(category);

			//Listing
			Collection<Category> cl = this.categoryService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.categoryService.findOne(saved.getId()));

			//Edition
			saved.setName(name2);
			saved.setDescription(description2);
			final Category saved2 = this.categoryService.save(saved);

			//Deletion
			this.categoryService.delete(saved2);
			cl = this.categoryService.findAll();
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
