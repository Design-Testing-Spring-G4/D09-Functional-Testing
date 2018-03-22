
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

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

			//Test #01: Correct execution of test. Expected true.

			{
				"admin", "testAddress", new GregorianCalendar(2010, Calendar.FEBRUARY, 11).getTime(), "testemail@alum.com", "testAdministrator", "testSurname", "testPhone", "editAddress", new GregorianCalendar(2010, Calendar.FEBRUARY, 11).getTime(),
				"editemail@alum.com", "editAdministrator", "editSurname", "editPhone", null
			},

			//Test #02: Attempt to execute the test by anonymous user. Expected false.

			{
				null, "testAddress", new GregorianCalendar(2010, Calendar.FEBRUARY, 11).getTime(), "testemail@alum.com", "testAdministrator", "testSurname", "testPhone", "editAddress", new GregorianCalendar(2010, Calendar.FEBRUARY, 11).getTime(),
				"editemail@alum.com", "editAdministrator", "editSurname", "editPhone", IllegalArgumentException.class
			},

			//Test #03: Attempt to execute the test by unauthorized user. Expected false.

			{
				"user1", "testAddress", new GregorianCalendar(2010, Calendar.FEBRUARY, 11).getTime(), "testemail@alum.com", "testAdministrator", "testSurname", "testPhone", "editAddress", new GregorianCalendar(2010, Calendar.FEBRUARY, 11).getTime(),
				"editemail@alum.com", "editAdministrator", "editSurname", "editPhone", ClassCastException.class
			},

			//Test #04: Attempt to create an administrator with blank text. Expected false.

			{
				"admin", "testAddress", new GregorianCalendar(2010, Calendar.FEBRUARY, 11).getTime(), "", "", "", "testPhone", "editAddress", new GregorianCalendar(2010, Calendar.FEBRUARY, 11).getTime(), "editemail@alum.com", "editAdministrator",
				"editSurname", "editPhone", IllegalArgumentException.class
			},

			//Test #05: Attempt to create an administrator with a future birth date. Expected false.

			{
				"admin", "testAddress", new GregorianCalendar(2030, Calendar.FEBRUARY, 11).getTime(), "testemail@alum.com", "testAdministrator", "testSurname", "testPhone", "editAddress", new GregorianCalendar(2010, Calendar.FEBRUARY, 11).getTime(),
				"editemail@alum.com", "editAdministrator", "editSurname", "editPhone", IllegalArgumentException.class
			},

			//Test #06: Attempt to edit an administrator with a null birth date. Expected false.

			{
				"admin", "testAddress", null, "testemail@alum.com", "testAdministrator", "testSurname", "testPhone", "editAddress", new GregorianCalendar(2010, Calendar.FEBRUARY, 11).getTime(), "editemail@alum.com", "editAdministrator", "editSurname",
				"editPhone", IllegalArgumentException.class
			},

			//Test #07: Attempt to edit an administrator with an invalid email. Expected true.

			{
				"admin", "testAddress", new GregorianCalendar(2010, Calendar.FEBRUARY, 11).getTime(), "testemail@alum.com", "testAdministrator", "testSurname", "testPhone", "editAddress", new GregorianCalendar(2010, Calendar.FEBRUARY, 11).getTime(),
				"invalidEmail", "editAdministrator", "editSurname", "editPhone", IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (Date) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(Date) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (Class<?>) testingData[i][13]);
	}
}
