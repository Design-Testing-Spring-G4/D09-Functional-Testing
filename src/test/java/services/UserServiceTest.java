
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class UserServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private UserService	userService;


	//Tests

	@Test
	public void testCreateUser() {
		//Using create() to initialise a new entity.
		final User u = this.userService.create();

		u.setName("Sergio");
		u.setSurname("Morales");
		u.setPhone("649514875");
		u.setAddress("c/test, 1");
		u.setEmail("testddd@mail.com");
		u.getUserAccount().setUsername("Probador");
		u.getUserAccount().setPassword("asdfa54548");

		System.out.println(u.getName() + u.getSurname() + u.getPhone() + u.getAddress() + u.getEmail() + u.getUserAccount() + u.getRendezvous() + u.getAttendance() + u.getAnnouncements() + u.getComments() + u.getAnswers());
		final User a = this.userService.save(u);

		System.out.println("guardado" + a.getName() + a.getSurname() + a.getPhone() + a.getAddress() + a.getEmail() + a.getUserAccount() + a.getRendezvous() + a.getAttendance() + a.getAnnouncements() + a.getComments() + a.getAnswers());
		final User bbdd = this.userService.findOne(a.getId());
		Assert.notNull(bbdd);
	}

}
