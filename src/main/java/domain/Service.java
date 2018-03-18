
package domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
public class Service extends DomainEntity {

	//Attributes

	private String				name;
	private String				description;
	private String				picture;
	private boolean				cancelled;

	//Relationships

	private Manager				manager;
	private Category			category;
	private Collection<Request>	requests;


	//Getters

	@NotBlank
	public String getName() {
		return this.name;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	@URL
	public String getPicture() {
		return this.picture;
	}

	public boolean getCancelled() {
		return this.cancelled;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Manager getManager() {
		return this.manager;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Category getCategory() {
		return this.category;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "service")
	public Collection<Request> getRequests() {
		return this.requests;
	}

	//Setters

	public void setName(final String name) {
		this.name = name;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	public void setCancelled(final boolean cancelled) {
		this.cancelled = cancelled;
	}

	public void setManager(final Manager manager) {
		this.manager = manager;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	public void setRequests(final Collection<Request> requests) {
		this.requests = requests;
	}
}
