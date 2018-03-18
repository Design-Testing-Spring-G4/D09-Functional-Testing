
package domain;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
public class Configuration extends DomainEntity {

	//Attributes

	private String	companyName;
	private String	banner;
	private String	welcomeEN;
	private String	welcomeES;


	//Getters

	@NotBlank
	public String getCompanyName() {
		return this.companyName;
	}

	@URL
	public String getBanner() {
		return this.banner;
	}

	public String getWelcomeEN() {
		return this.welcomeEN;
	}

	public String getWelcomeES() {
		return this.welcomeES;
	}

	//Setters

	public void setCompanyName(final String companyName) {
		this.companyName = companyName;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	public void setWelcomeEN(final String welcomeEN) {
		this.welcomeEN = welcomeEN;
	}

	public void setWelcomeES(final String welcomeES) {
		this.welcomeES = welcomeES;
	}
}
