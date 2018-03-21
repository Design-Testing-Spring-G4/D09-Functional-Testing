
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Category;

@Service
@Transactional
public class CategoryService {

	//Managed repository

	@Autowired
	private CategoryRepository	categoryRepository;

	//Supporting services

	@Autowired
	private ServiceService		serviceService;


	//Simple CRUD methods

	public Category create() {
		final Category c = new Category();

		c.setChildren(new ArrayList<Integer>());
		c.setServices(new ArrayList<domain.Service>());

		return c;
	}

	public Category findOne(final int id) {
		Assert.notNull(id);

		return this.categoryRepository.findOne(id);
	}

	public Collection<Category> findAll() {
		return this.categoryRepository.findAll();
	}

	public Category save(final Category c) {
		Assert.notNull(c);

		//Business rule: two categories with the same parent cannot have the same name.
		if (c.getId() == 0)
			for (final Category a : this.findAll())
				Assert.isTrue(!(a.getParent() == (c.getParent()) && a.getName().equals(c.getName())));

		final Category saved = this.categoryRepository.save(c);
		return saved;
	}

	public void delete(final Category c) {
		Assert.notNull(c);

		if (c.getParent() != null) {
			final Category parent = c.getParent();
			parent.getChildren().remove(c);
			this.categoryRepository.save(parent);
		}

		final Category defaultCat = this.findAll().iterator().next();
		for (final domain.Service s : c.getServices()) {
			s.setCategory(defaultCat);
			this.serviceService.saveInternal(s);
		}

		this.categoryRepository.delete(c);

	}

}
