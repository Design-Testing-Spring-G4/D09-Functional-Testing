
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
import domain.Rendezvous;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class RendezvousServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private RendezvousService	rendezvousService;


	//Setting up the authority to execute services.
	@Test
	public void testCreateRendezvous() {
		//Setting up the authority to execute services.
		this.authenticate("user1");

		//Using create() to initialise a new entity. Necessary Id's taken from populated database.
		final Rendezvous rendezvous = this.rendezvousService.create();

		rendezvous.setName("Rendezvous Prueba");
		rendezvous.setDescription("Esto es una prueba");
		rendezvous.setPicture("dsfasdfasdfafda");
		rendezvous.setCoordinates("+90, -180");

		//Saving entity to database and confirming it exists with findAll().
		final Rendezvous saved = this.rendezvousService.save(rendezvous);
		System.out.println(saved);

		final Collection<Rendezvous> rendezvouses = this.rendezvousService.findAll();
		Assert.isTrue(rendezvouses.contains(saved));
	}

	@Test
	public void testListDeleteRendezvous() {
		//Setting up the authority to execute services.
		this.authenticate("user1");

		//We retrieve a list of all notes, and obtain the Id of one of them.
		Collection<Rendezvous> rendezvouses = this.rendezvousService.findAll();
		final int id = rendezvouses.iterator().next().getId();

		//Using findOne() to retrieve a particular entity and verifying it.
		final Rendezvous rendezvous = this.rendezvousService.findOne(id);
		Assert.notNull(id);

		//Using delete() to delete the entity we retrieved.
		this.rendezvousService.delete(rendezvous);

		//Verifying the entity has been removed from the database.
		rendezvouses = this.rendezvousService.findAll();
		Assert.isTrue(!rendezvouses.contains(rendezvous));
	}
}
