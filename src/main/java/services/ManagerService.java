
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ManagerRepository;
import security.Authority;
import security.UserAccount;
import domain.Manager;

@Service
@Transactional
public class ManagerService {

	//Managed repository ---------------------------------

	@Autowired
	private ManagerRepository	managerRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService		actorService;


	//Simple CRUD Methods --------------------------------

	public Manager create() {
		final Authority a = new Authority();
		a.setAuthority(Authority.MANAGER);
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));

		final Manager m = new Manager();

		m.setUserAccount(account);
		m.setServices(new ArrayList<domain.Service>());

		return m;
	}

	public Collection<Manager> findAll() {
		return this.managerRepository.findAll();
	}

	public Manager findOne(final int id) {
		Assert.notNull(id);

		return this.managerRepository.findOne(id);
	}

	public Manager save(final Manager manager) {
		Assert.notNull(manager);

		//Assertion that the user modifying this user has the correct privilege.
		if (manager.getId() != 0)
			Assert.isTrue(this.actorService.findByPrincipal().getId() == manager.getId());

		final Manager m = this.managerRepository.save(manager);
		return m;
	}

	//Saving method for internal processes.
	public Manager saveInternal(final Manager manager) {
		Assert.notNull(manager);
		final Manager m = this.managerRepository.save(manager);
		return m;
	}

	public void delete(final Manager manager) {
		Assert.notNull(manager);

		//Assertion that the user deleting this user has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == manager.getId());

		this.managerRepository.delete(manager);
	}

	//Ancillary methods

}
