
package services;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Configuration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ConfigurationServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private services.ConfigurationService	configurationService;


	//Test template

	protected void Template(final String username, final String companyName, final String banner, final String welcomeEN, final String welcomeES, final String companyName2, final String banner2, final String welcomeEN2, final String welcomeES2,
		final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation
			final Configuration configuration = this.configurationService.create();
			configuration.setCompanyName(companyName);
			configuration.setBanner(banner);
			configuration.setWelcomeEN(welcomeEN);
			configuration.setWelcomeES(welcomeES);
			final Configuration saved = this.configurationService.save(configuration);

			//Listing
			Collection<Configuration> cl = this.configurationService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.configurationService.findOne(saved.getId()));

			//Edition
			saved.setCompanyName(companyName2);
			saved.setBanner(banner2);
			saved.setWelcomeEN(welcomeEN2);
			saved.setWelcomeES(welcomeES2);
			final Configuration saved2 = this.configurationService.save(saved);

			//Deletion
			this.configurationService.delete(saved2);
			cl = this.configurationService.findAll();
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
				"admin", "testConfiguration", "http://eskipaper.com/images/savannah-5.jpg", "testWelcomeEN", "testWelcomeES", "editConfiguration", "https://tinyurl.com/adventure-meetup", "testwelcomeEN2", "testWelcomeES2", null
			},

			//Test #02: Attempt to create a configuration with a blank company name. Expected false.
			{
				"admin", "", "http://eskipaper.com/images/savannah-5.jpg", "testWelcomeEN", "testWelcomeES", "editConfiguration", "https://tinyurl.com/adventure-meetup", "testwelcomeEN2", "testWelcomeES2", ConstraintViolationException.class
			},

			//Test #03: Attempt to edit a configuration with a blank company name. Expected false.
			{
				"admin", "testConfiguration", "http://eskipaper.com/images/savannah-5.jpg", "testWelcomeEN", "testWelcomeES", "", "https://tinyurl.com/adventure-meetup", "testwelcomeEN2", "testWelcomeES2", ConstraintViolationException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(String) testingData[i][8], (Class<?>) testingData[i][9]);
	}
}
