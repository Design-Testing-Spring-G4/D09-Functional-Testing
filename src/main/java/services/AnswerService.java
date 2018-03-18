
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AnswerRepository;
import domain.Answer;
import domain.User;

@Service
@Transactional
public class AnswerService {

	//Managed repository

	@Autowired
	private AnswerRepository	answerRepository;

	//Supporting services

	@Autowired
	private QuestionService		questionService;

	@Autowired
	private UserService			userService;


	//Simple CRUD methods

	public Answer create(final int questionId) {
		final Answer a = new Answer();
		a.setQuestion(this.questionService.findOne(questionId));
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

		for (final User u : this.userService.findAll()) {
			final Collection<Answer> answers = u.getAnswers();
			if (answers.contains(a)) {
				answers.remove(a);
				u.setAnswers(answers);
				this.userService.saveInternal(u);
			}
		}

		this.answerRepository.delete(a);
	}
	//Ancillary methods

	public Double[] avgStddevAnswer() {
		return this.answerRepository.avgStddevAnswer();
	}

}
