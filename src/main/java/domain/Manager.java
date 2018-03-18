
package domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Manager extends Actor {

	//Attributes

	private String				vat;

	//Relationships

	private Collection<Service>	services;


	//Getters

	public void setServices(final Collection<Service> services) {
		this.services = services;
	}

	@NotBlank
	@Pattern(regexp = "[a-zA-Z0-9-]{1,}")
	public String getVat() {
		return this.vat;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "manager")
	public Collection<Service> getServices() {
		return this.services;
	}

	//Setters

	public void setVat(final String vat) {
		this.vat = vat;
	}

}
