
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	//Managed repository

	@Autowired
	private ConfigurationRepository	configurationRepository;


	//Simple CRUD methods

	public Configuration create() {

		final Configuration c = new Configuration();
		c.setBanner("http://www.theadventuretravelsite.com/blog-data/themes/theadventuretravelsite/images/main/banner.png");
		c.setCompanyName("Adventure meetups");
		c.setWelcomeEN("Your place to organise your adventure meetups!");
		c.setWelcomeES("Tu sitio para organizar quedadas de aventura");

		return c;
	}

	public Collection<Configuration> findAll() {
		return this.configurationRepository.findAll();
	}

	public Configuration findOne(final int id) {
		Assert.notNull(id);

		return this.configurationRepository.findOne(id);
	}

	public Configuration save(final Configuration configuration) {
		Assert.notNull(configuration);

		return this.configurationRepository.save(configuration);
	}

	public void delete(final Configuration configuration) {
		Assert.notNull(configuration);

		this.configurationRepository.delete(configuration);
	}

	//Other methods
}
