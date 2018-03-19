
package domain;

import java.util.Collection;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class User extends Actor {

	//Attributes

	private Collection<CreditCard>		creditCards;

	//Relationships

	private Collection<Rendezvous>		rendezvous;
	private Collection<Rendezvous>		attendance;
	private Collection<Announcement>	announcements;
	private Collection<Comment>			comments;
	private Collection<Answer>			answers;


	//Getters

	@Valid
	@NotNull
	@OneToMany(mappedBy = "creator")
	public Collection<Rendezvous> getRendezvous() {
		return this.rendezvous;
	}

	@Valid
	@NotNull
	@ManyToMany(mappedBy = "attendants")
	public Collection<Rendezvous> getAttendance() {
		return this.attendance;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "user")
	public Collection<Announcement> getAnnouncements() {
		return this.announcements;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "user")
	public Collection<Comment> getComments() {
		return this.comments;
	}

	@Valid
	@NotNull
	@OneToMany
	public Collection<Answer> getAnswers() {
		return this.answers;
	}

	@NotNull
	@Valid
	@ElementCollection
	public Collection<CreditCard> getCreditCards() {
		return this.creditCards;
	}

	//Setters

	public void setRendezvous(final Collection<Rendezvous> rendezvous) {
		this.rendezvous = rendezvous;
	}

	public void setAttendance(final Collection<Rendezvous> attendance) {
		this.attendance = attendance;
	}

	public void setAnnouncements(final Collection<Announcement> announcements) {
		this.announcements = announcements;
	}

	public void setComments(final Collection<Comment> comments) {
		this.comments = comments;
	}

	public void setAnswers(final Collection<Answer> answers) {
		this.answers = answers;
	}

	public void setCreditCards(final Collection<CreditCard> creditCards) {
		this.creditCards = creditCards;
	}

}
