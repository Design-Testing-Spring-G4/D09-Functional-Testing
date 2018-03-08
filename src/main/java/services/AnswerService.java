
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AnswerRepository;
import domain.Answer;

@Service
@Transactional
public class AnswerService {

	//Managed repository

	@Autowired
	private AnswerRepository	answerRepository;

	@Autowired
	private QuestionService		questionService;


	public Answer create() {
		final Answer a = new Answer();
		a.setQuestion(this.questionService.findAll().iterator().next());
		return a;
	}

	public Collection<Answer> findAll() {
		return this.answerRepository.findAll();
	}

	public Answer findOne(final int id) {
		Assert.notNull(id);

		return this.answerRepository.findOne(id);
	}

	public Answer save(final Answer a) {
		Assert.notNull(a);
		final Answer saved = this.answerRepository.save(a);
		return saved;
	}

	public void delete(final Answer a) {
		Assert.notNull(a);
		this.answerRepository.delete(a);
	}

}
