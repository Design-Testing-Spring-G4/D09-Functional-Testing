
package domain;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Question extends DomainEntity {

	//Attributes

	private String	question;


	//Getters

	@NotBlank
	public String getQuestion() {
		return this.question;
	}

	//Setters

	public void setQuestion(final String question) {
		this.question = question;
	}
}
