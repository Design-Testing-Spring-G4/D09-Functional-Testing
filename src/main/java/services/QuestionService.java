
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.QuestionRepository;
import domain.Question;

@Service
@Transactional
public class QuestionService {

	//Managed repository

	@Autowired
	private QuestionRepository	questionRepository;


	public Question create() {
		final Question q = new Question();
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
		final Question saved = this.questionRepository.save(q);
		System.out.println("Question for save: " + q);
		System.out.println("List questions: " + this.questionRepository.findAll());
		return saved;
	}
	public void delete(final Question q) {
		System.out.println("question en el service" + q);
		Assert.notNull(q);
		System.out.println("lista antes de borrarla " + this.questionRepository.findAll());
		this.questionRepository.delete(q);
		System.out.println("lista despues de borrarla " + this.questionRepository.findAll());

	}
}
