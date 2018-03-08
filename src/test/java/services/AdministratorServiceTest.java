
package services;

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
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private AdministratorService	administratorService;


	//Tests

	@Test
	public void testCreateAdministrator() {
		//Using create() to initialise a new entity.
		//this.authenticate("admin");

		final Administrator administrator = this.administratorService.create();

		administrator.setName("testNameAdmin");
		administrator.setSurname("testSurnameAdmin");
		administrator.setPhone("991188777");
		administrator.setAddress("c/testAdmin, 1");
		administrator.setEmail("testAdmin@mail.com");
		administrator.getUserAccount().setUsername("admin");
		administrator.getUserAccount().setPassword("abc45678");
		System.out.println(administrator.getName());
		final Administrator saved = this.administratorService.save(administrator);
		final Administrator bbdd = this.administratorService.findOne(saved.getId());
		Assert.notNull(bbdd);
	}

}
