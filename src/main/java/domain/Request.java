
package domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class Request extends DomainEntity {

	//Attributes

	private CreditCard	creditCard;
	private String		comments;

	//Relationships

	private User		user;
	private Service		service;
	private Rendezvous	rendezvous;


	//Getters

	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public String getComments() {
		return this.comments;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public User getUser() {
		return this.user;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Service getService() {
		return this.service;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Rendezvous getRendezvous() {
		return this.rendezvous;
	}

	//Setters

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public void setService(final Service service) {
		this.service = service;
	}

	public void setRendezvous(final Rendezvous rendezvous) {
		this.rendezvous = rendezvous;
	}
}
