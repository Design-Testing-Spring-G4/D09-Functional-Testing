
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.QuestionRepository;
import domain.Answer;
import domain.Question;
import domain.Rendezvous;

@Service
@Transactional
public class QuestionService {

	//Managed repository

	@Autowired
	private QuestionRepository	questionRepository;

	//Supporting services

	@Autowired
	private RendezvousService	rendezvousService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private AnswerService		answerService;


	public Question create(final int rendezvousId) {
		final Question q = new Question();

		final Rendezvous r = this.rendezvousService.findOne(rendezvousId);
		q.setRendezvous(r);

		return q;
	}

	public Question findOne(final int id) {
		Assert.notNull(id);
		return this.questionRepository.findOne(id);
	}

	public Collection<Question> findAll() {
		return this.questionRepository.findAll();
	}

	public Question save(final Question q) {
		Assert.notNull(q);
		Assert.isTrue(this.actorService.findByPrincipal() == q.getRendezvous().getCreator());

		final Question saved = this.questionRepository.save(q);
		return saved;
	}
	public void delete(final Question q) {
		Assert.notNull(q);
		Assert.isTrue(this.actorService.findByPrincipal() == q.getRendezvous().getCreator());

		for (final Answer a : this.answerService.findAll())
			if (a.getQuestion() == q)
				this.answerService.delete(a);

		this.questionRepository.delete(q);
	}
}
