
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
import domain.Administrator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private AdministratorService	administratorService;


	//Test template

	protected void Template(final String username, final String address, final Date birthDate, final String email, final String name, final String surname, final String phone, final String address2, final Date birthDate2, final String email2,
		final String name2, final String surname2, final String phone2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation
			final Administrator administrator = this.administratorService.create();
			administrator.setAddress(address);
			administrator.setBirthDate(birthDate);
			administrator.setEmail(email);
			administrator.setName(name);
			administrator.setSurname(surname);
			administrator.setPhone(phone);
			final Administrator saved = this.administratorService.save(administrator);

			//Listing
			Collection<Administrator> cl = this.administratorService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.administratorService.findOne(saved.getId()));

			//Edition
			saved.setAddress(address2);
			saved.setBirthDate(birthDate2);
			saved.setEmail(email2);
			saved.setName(name2);
			saved.setSurname(surname2);
			saved.setPhone(phone2);
			final Administrator saved2 = this.administratorService.save(saved);

			//Deletion
			this.administratorService.delete(saved2);
			cl = this.administratorService.findAll();
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
