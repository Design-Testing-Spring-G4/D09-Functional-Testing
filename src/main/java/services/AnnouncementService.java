
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AnnouncementRepository;
import domain.Announcement;
import domain.Rendezvous;
import domain.User;

@Service
@Transactional
public class AnnouncementService {

	//Managed repository

	@Autowired
	private AnnouncementRepository	announcementRepository;

	//Supporting service

	@Autowired
	private ActorService			actorService;

	@Autowired
	private RendezvousService		rendezvousService;


	//Simple CRUD methods

	public Announcement create() {
		final Announcement a = new Announcement();
		final User u = (User) this.actorService.findByPrincipal();
		a.setUser(u);
		a.setMoment(new Date(System.currentTimeMillis() - 1));
		return a;
	}

	public Announcement findOne(final int id) {
		Assert.notNull(id);
		return this.announcementRepository.findOne(id);
	}

	public Collection<Announcement> findAll() {
		return this.announcementRepository.findAll();
	}

	public Announcement save(final Announcement a) {
		Assert.notNull(a);
		Assert.isTrue(this.actorService.findByPrincipal() == a.getUser());
		final Announcement saved = this.announcementRepository.save(a);
		return saved;
	}

	public void delete(final Announcement a) {
		Assert.notNull(a);

		final Rendezvous r = a.getRendezvous();
		final Collection<Announcement> ca = r.getAnnouncements();
		ca.remove(a);
		r.setAnnouncements(ca);
		this.rendezvousService.saveInternal(r);

		this.announcementRepository.delete(a);
	}
}
