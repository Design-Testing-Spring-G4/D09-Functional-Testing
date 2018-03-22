
package services;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.UserAccount;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	//Managed repository ---------------------------------

	@Autowired
	private AdministratorRepository	administratorRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService			actorService;


	//Simple CRUD Methods --------------------------------

	public Administrator create() {
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));

		final Administrator administrator = new Administrator();
		administrator.setUserAccount(account);

		return administrator;
	}

	public Collection<Administrator> findAll() {
		return this.administratorRepository.findAll();
	}

	public Administrator findOne(final int id) {
		Assert.notNull(id);

		return this.administratorRepository.findOne(id);
	}

	public Administrator save(final Administrator administrator) {
		Assert.notNull(administrator);

		//Assertion that the user modifying this administrator has the correct privilege.
		if (administrator.getId() != 0)
			Assert.isTrue(this.actorService.findByPrincipal().getId() == administrator.getId());

		final Administrator saved = this.administratorRepository.save(administrator);

		return saved;
	}

	public void delete(final Administrator administrator) {
		Assert.notNull(administrator);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == administrator.getId());

		this.administratorRepository.delete(administrator);
	}
}
