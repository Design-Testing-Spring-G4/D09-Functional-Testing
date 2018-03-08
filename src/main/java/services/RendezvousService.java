
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AnnouncementRepository;
import repositories.CommentRepository;
import repositories.QuestionRepository;
import repositories.RendezvousRepository;
import domain.Announcement;
import domain.Comment;
import domain.Question;
import domain.Rendezvous;
import domain.User;

@Service
@Transactional
public class RendezvousService {

	@Autowired
	private RendezvousRepository	rendezvousRepository;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private UserService				userService;

	@Autowired
	private AnnouncementRepository	announcementRepository;

	@Autowired
	private CommentRepository		commentRepository;

	@Autowired
	private QuestionRepository		questionRepository;


	public Rendezvous create() {
		final Rendezvous r = new Rendezvous();
		final User u = (User) this.actorService.findByPrincipal();

		r.setCreator(u);
		r.setAttendants(new ArrayList<User>());
		r.setLinks(new ArrayList<Rendezvous>());
		r.setAnnouncements(new ArrayList<Announcement>());
		r.setComments(new ArrayList<Comment>());
		r.setQuestions(new ArrayList<Question>());
		return r;

	}

	public Rendezvous findOne(final int id) {
		Assert.notNull(id);

		return this.rendezvousRepository.findOne(id);
	}

	public Collection<Rendezvous> findAll() {
		return this.rendezvousRepository.findAll();
	}

	public Rendezvous save(final Rendezvous r) {
		Assert.notNull(r);
		Assert.isTrue(this.actorService.findByPrincipal() == r.getCreator());
		final User u = (User) this.actorService.findByPrincipal();
		final Rendezvous saved = this.rendezvousRepository.save(r);
		u.getAttendance().add(saved);
		this.userService.save(u);
		return saved;
	}
	public void delete(final Rendezvous r) {
		Assert.notNull(r);
		Assert.isTrue(r.getFinalMode() == false);
		if (!(r.getAnnouncements()).isEmpty())
			for (final Announcement a : r.getAnnouncements())
				this.announcementRepository.delete(a);
		if (!(r.getComments()).isEmpty())
			for (final Comment c : r.getComments())
				this.commentRepository.delete(c);
		if (!(r.getQuestions()).isEmpty())
			for (final Question q : r.getQuestions())
				this.questionRepository.delete(q);
		for (final Rendezvous x : this.rendezvousRepository.findAll())
			if (x.getLinks().contains(r))
				x.getLinks().remove(r);
		this.rendezvousRepository.delete(r);
	}

	public Rendezvous cancel(final Rendezvous r) {
		Assert.notNull(r);

		//Business rule: a trip can only be cancelled when it has been published and it hasn't started.
		final Date now = new Date(System.currentTimeMillis());
		Assert.isTrue(r.getFinalMode() == false && r.getMoment().after(now));

		//Assertion that the user cancelling this trip has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == r.getCreator().getId());

		final Rendezvous saved = this.rendezvousRepository.save(r);

		return saved;

	}

	public void addRendezvous(final Rendezvous r) {
		Assert.notNull(r);
		User user;

		user = ((User) this.actorService.findByPrincipal());
		user.getAttendance().add(r);
		r.getAttendants().add(user);
		this.rendezvousRepository.save(r);
		this.userService.save(user);
	}

	public void deleteRendezvous(final Rendezvous rendezvous) {
		Assert.notNull(rendezvous);
		User user;

		user = ((User) this.actorService.findByPrincipal());
		if (user.getAttendance().contains(rendezvous)) {
			user.getAttendance().remove(rendezvous);
			rendezvous.getAttendants().remove(user);
			this.rendezvousRepository.save(rendezvous);
			this.userService.save(user);
		}

	}

	public Collection<Question> questionsByUser(final User user) {
		final Collection<Question> questions = new ArrayList<>();
		for (final Rendezvous r : user.getRendezvous())
			questions.addAll(r.getQuestions());
		return questions;
	}

	public Double[] avgStddevUserPerRendezvous() {
		return this.rendezvousRepository.avgStddevUserPerRendezvous();
	}

	public Double[] ratioRendezvousVsNotRendezvous() {
		return this.rendezvousRepository.ratioRendezvousVsNotRendezvous();
	}

	public Collection<Rendezvous> topTenRendezvous() {
		return this.rendezvousRepository.topTenRendezvous(new PageRequest(0, 10));
	}

	public Double[] avgStddevAnnouncementsPerRendezvous() {
		return this.rendezvousRepository.avgStddevAnnouncementsPerRendezvous();
	}

	public Collection<Rendezvous> announcementsWithAboveAverageRendezvous() {
		return this.rendezvousRepository.announcementsWithAboveAverageRendezvous();
	}
	public Collection<Rendezvous> announcementsWithLinksAboveAverageRendezvous() {
		return this.rendezvousRepository.announcementsWithLinksAboveAverageRendezvous();
	}

	public Double[] avgStddevQuestionsPerRendezvous() {
		return this.rendezvousRepository.avgStddevQuestionsPerRendezvous();
	}

	/*
	 * public Double[] avgStddevAnswersPerQuestiosnPerRendezvous() {
	 * return this.rendezvousRepository.avgStddevAnswersPerQuestiosnPerRendezvous();
	 * }
	 */

}
