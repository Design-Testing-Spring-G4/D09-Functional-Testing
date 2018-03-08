
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Comment extends DomainEntity {

	//Attributes

	private Date				moment;
	private String				text;
	private String				picture;

	//Relationships

	private User				user;
	private Collection<Comment>	replies;


	//Getters

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	@NotBlank
	public String getText() {
		return this.text;
	}

	@URL
	public String getPicture() {
		return this.picture;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public User getUser() {
		return this.user;
	}

	@Valid
	@NotNull
	@OneToMany
	public Collection<Comment> getReplies() {
		return this.replies;
	}

	//Setters

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public void setReplies(final Collection<Comment> replies) {
		this.replies = replies;
	}
}
