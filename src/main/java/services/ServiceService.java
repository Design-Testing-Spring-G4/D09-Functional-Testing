
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ServiceRepository;
import domain.Manager;
import domain.Request;

@Service
@Transactional
public class ServiceService {

	//Managed repository

	@Autowired
	private ServiceRepository	serviceRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private RequestService		requestService;


	//Simple CRUD methods

	public domain.Service create() {
		final domain.Service s = new domain.Service();
		final Manager m = (Manager) this.actorService.findByPrincipal();
		s.setManager(m);
		s.setRequests(new ArrayList<Request>());
		return s;
	}

	public domain.Service findOne(final int id) {
		Assert.notNull(id);
		return this.serviceRepository.findOne(id);
	}

	public Collection<domain.Service> findAll() {
		return this.serviceRepository.findAll();
	}

	public domain.Service save(final domain.Service s) {
		Assert.notNull(s);

		Assert.isTrue(this.actorService.findByPrincipal().getId() == s.getManager().getId());

		final domain.Service saved = this.serviceRepository.save(s);
		return saved;
	}

	public void delete(final domain.Service s) {
		Assert.notNull(s);

		final Collection<Request> requests = s.getRequests();
		for (final Request r : requests) {
			requests.remove(r);
			this.requestService.save(r);
		}
		this.serviceRepository.delete(s);
	}

	//Ancillary methods

}
