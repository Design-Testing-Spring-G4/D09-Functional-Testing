
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Manager;

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
		final String email2, final String name2, final String surname2, final String phone2, final String vat2, final String username2, final Class<?> expected) {
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
			manager.getUserAccount().setUsername(username2);
			manager.getUserAccount().setPassword(username2);
			final Manager saved = this.managerService.save(manager);

			this.unauthenticate();
			this.authenticate(username2);

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

			//Test #01: Correct execution of test. Expected true.
			{
				"manager1", "testAddress", new GregorianCalendar(2010, Calendar.FEBRUARY, 11).getTime(), "testemail@alum.com", "testManager", "testSurname", "testPhone", "123-abc", "editAddress",
				new GregorianCalendar(2010, Calendar.FEBRUARY, 11).getTime(), "editemail@alum.com", "editManager", "editSurname", "editPhone", "123-abc", "manager9", null
			},

			//Test #02: Attempt to save a manager without proper credentials. Expected false.
			{
				"manager1", "testAddress", new GregorianCalendar(2010, Calendar.FEBRUARY, 11).getTime(), "testemail@alum.com", "testManager", "testSurname", "testPhone", "123-abc", "editAddress",
				new GregorianCalendar(2010, Calendar.FEBRUARY, 11).getTime(), "editemail@alum.com", "editManager", "editSurname", "editPhone", "123-abc", null, IllegalArgumentException.class
			},

			//Test #03: Attempt to create an administrator with an incorrect vat. Expected false.
			{
				"manager1", "testAddress", new GregorianCalendar(2010, Calendar.FEBRUARY, 11).getTime(), "testemail@alum.com", "testManager", "testSurname", "testPhone", "123_abc", "editAddress",
				new GregorianCalendar(2010, Calendar.FEBRUARY, 11).getTime(), "editemail@alum.com", "editManager", "editSurname", "editPhone", "123-abc", "manager9", ConstraintViolationException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (Date) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(String) testingData[i][8], (Date) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (String) testingData[i][13], (String) testingData[i][14], (String) testingData[i][15],
				(Class<?>) testingData[i][16]);
	}
}
