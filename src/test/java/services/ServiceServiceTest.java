
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
import domain.Service;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ServiceServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private ServiceService	serviceService;

	//Supporting Services

	@Autowired
	private CategoryService	categoryService;


	//Test template

	protected void Template(final String username, final String name, final String description, final String picture, final String name2, final String description2, final String picture2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation
			final Service service = this.serviceService.create();
			service.setName(name);
			service.setDescription(description);
			service.setPicture(picture);
			final Category category = this.categoryService.findOne(this.getEntityId("category1"));
			service.setCategory(category);
			final Service saved = this.serviceService.save(service);

			//Listing
			Collection<Service> cl = this.serviceService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.serviceService.findOne(saved.getId()));

			//Edition
			saved.setName(name2);
			saved.setDescription(description2);
			saved.setPicture(picture2);
			final Service saved2 = this.serviceService.save(saved);

			//Deletion
			this.serviceService.delete(saved2);
			cl = this.serviceService.findAll();
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
