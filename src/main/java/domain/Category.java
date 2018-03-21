
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	//Attributes

	private String				name;
	private String				description;

	//Relationships

	private Category			parent;
	private Collection<Integer>	children;
	private Collection<Service>	services;


	//Getters

	@NotBlank
	public String getName() {
		return this.name;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	@ManyToOne(optional = true)
	public Category getParent() {
		return this.parent;
	}

	@NotNull
	@ElementCollection
	public Collection<Integer> getChildren() {
		return this.children;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "category")
	public Collection<Service> getServices() {
		return this.services;
	}

	//Setters

	public void setName(final String name) {
		this.name = name;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setParent(final Category parent) {
		this.parent = parent;
	}

	public void setChildren(final Collection<Integer> children) {
		this.children = children;
	}

	public void setServices(final Collection<Service> services) {
		this.services = services;
	}
}
