
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.UserRepository;
import security.Authority;
import security.UserAccount;
import domain.Announcement;
import domain.Answer;
import domain.Comment;
import domain.Rendezvous;
import domain.User;

@Service
@Transactional
public class UserService {

	//Managed repository ---------------------------------

	@Autowired
	private UserRepository	userRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService	actorService;


	//Simple CRUD Methods --------------------------------

	public User create() {
		final Authority a = new Authority();
		a.setAuthority(Authority.USER);
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));

		final User u = new User();

		u.setUserAccount(account);
		u.setRendezvous(new ArrayList<Rendezvous>());
		u.setAttendance(new ArrayList<Rendezvous>());
		u.setAnnouncements(new ArrayList<Announcement>());
		u.setComments(new ArrayList<Comment>());
		u.setAnswers(new ArrayList<Answer>());

		return u;
	}

	public Collection<User> findAll() {
		return this.userRepository.findAll();
	}

	public User findOne(final int id) {
		Assert.notNull(id);

		return this.userRepository.findOne(id);
	}

	public User save(final User user) {
		//System.out.println("service" + u.getName() + u.getSurname() + u.getPhone() + u.getAddress() + u.getEmail() + u.getUserAccount() + u.getRendezvous() + u.getAttendance() + u.getAnnouncements() + u.getComments() + u.getAnswers());
		Assert.notNull(user);

		//Assertion that the user modifying this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == user.getId());

		final User u = this.userRepository.save(user);
		System.out.println("service" + u.getName() + u.getSurname() + u.getPhone() + u.getAddress() + u.getEmail() + u.getUserAccount() + u.getRendezvous() + u.getAttendance() + u.getAnnouncements() + u.getComments() + u.getAnswers());
		return u;
	}

	public void delete(final User user) {
		Assert.notNull(user);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == user.getId());

		this.userRepository.delete(user);
	}

	public Collection<Announcement> announcementsByUser(final User user) {
		final Collection<Announcement> announcements = new ArrayList<>();
		for (final Rendezvous r : user.getAttendance())
			announcements.addAll(r.getAnnouncements());
		return announcements;
	}

	public Double[] avgStddevRendezvousPerUser() {
		return this.userRepository.avgStddevRendezvousPerUser();
	}

	public Double[] ratioRendezvousVsNotRendezvous() {
		return this.userRepository.ratioRendezvousVsNotRendezvous();
	}

	public Double[] avgStddevAttendancePerUser() {
		return this.userRepository.avgStddevAttendancePerUser();
	}

}
