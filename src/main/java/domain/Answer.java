
package domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Answer extends DomainEntity {

	//Attributes

	private String		answer;

	//Relationships

	private Question	question;


	//Getters

	@NotBlank
	public String getAnswer() {
		return this.answer;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Question getQuestion() {
		return this.question;
	}

	//Setters

	public void setAnswer(final String answer) {
		this.answer = answer;
	}

	public void setQuestion(final Question question) {
		this.question = question;
	}
}
