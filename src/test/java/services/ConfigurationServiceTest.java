
package services;

import java.util.Collection;

import org.hibernate.engine.config.spi.ConfigurationService;
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
