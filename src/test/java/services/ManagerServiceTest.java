
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

import domain.Manager;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ManagerServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private ManagerService	managerService;


	//Test template

	protected void Template(final String username, final String address, final Date birthDate, final String email, final String name, final String surname, final String phone, final String vat, final String address2, final Date birthDate2,
		final String email2, final String name2, final String surname2, final String phone2, final String vat2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation
			final Manager manager = this.managerService.create();
			manager.setAddress(address);
			manager.setBirthDate(birthDate);
			manager.setEmail(email);
			manager.setName(name);
			manager.setSurname(surname);
			manager.setPhone(phone);
			manager.setVat(vat);
			final Manager saved = this.managerService.save(manager);

			//Listing
			Collection<Manager> cl = this.managerService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.managerService.findOne(saved.getId()));

			//Edition
			saved.setAddress(address2);
			saved.setBirthDate(birthDate2);
			saved.setEmail(email2);
			saved.setName(name2);
			saved.setSurname(surname2);
			saved.setPhone(phone2);
			saved.setVat(vat2);
			final Manager saved2 = this.managerService.save(saved);

			//Deletion
			this.managerService.delete(saved2);
			cl = this.managerService.findAll();
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
