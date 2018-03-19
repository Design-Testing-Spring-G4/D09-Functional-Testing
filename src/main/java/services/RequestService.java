
package services;

import java.util.Collection;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import domain.Rendezvous;
import domain.Request;
import domain.User;

@Service
@Transactional
public class RequestService {

	//Managed repository

	@Autowired
	private RequestRepository	requestRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ServiceService		serviceService;

	@Autowired
	private RendezvousService	rendezvousService;


	//Simple CRUD methods

	public Request create(final int serviceId) {
		final Request r = new Request();
		final domain.Service s = (domain.Service) this.serviceService.findOne(serviceId);
		r.setService(s);
		final User u = (User) this.actorService.findByPrincipal();
		final Collection<Rendezvous> rendezvouses = u.getRendezvous();
		final Random rnd = new Random();
		final int i = rnd.nextInt(rendezvouses.size());
		r.setRendezvous((Rendezvous) rendezvouses.toArray()[i]);

		return r;
	}

	public Request findOne(final int id) {
		Assert.notNull(id);
		return this.requestRepository.findOne(id);
	}

	public Collection<Request> findAll() {
		return this.requestRepository.findAll();
	}

	public Request save(final Request r) {
		Assert.notNull(r);
		final User u = (User) this.actorService.findByPrincipal();
		Assert.isTrue(u.getRendezvous().contains(r.getRendezvous()));

		final Request saved = this.requestRepository.save(r);
		return saved;
	}

	public void delete(final Request r) {
		Assert.notNull(r);
		final User u = (User) this.actorService.findByPrincipal();
		final Collection<Rendezvous> rendezvous = u.getRendezvous();
		for (final Rendezvous e : rendezvous) {
			final Collection<Request> requests = e.getRequests();
			if (requests.contains(r)) {
				requests.remove(r);
				e.setRequests(requests);
				this.rendezvousService.saveInternal(e);
			}
		}

		this.requestRepository.delete(r);
	}

	//Ancillary methods
}
