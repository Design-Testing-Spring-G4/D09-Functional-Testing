
package domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Question extends DomainEntity {

	//Attributes

	private String		text;

	//Relationships

	private Rendezvous	rendezvous;


	//Getters

	@NotBlank
	public String getText() {
		return this.text;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Rendezvous getRendezvous() {
		return this.rendezvous;
	}

	//Setters

	public void setText(final String text) {
		this.text = text;
	}

	public void setRendezvous(final Rendezvous rendezvous) {
		this.rendezvous = rendezvous;
	}
}
