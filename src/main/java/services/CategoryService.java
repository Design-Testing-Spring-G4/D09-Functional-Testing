
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


	//Simple CRUD methods

	public Category create() {
		final Category c = new Category();

		c.setChildren(new ArrayList<Category>());
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
		//Since the only category without parent must be the root "CATEGORY", all others must have a parent ID.
		Assert.notNull(c.getParent());
		//Business rule: two categories with the same parent cannot have the same name.
		if (c.getId() == 0)
			for (final Category a : this.findAll())
				Assert.isTrue(!(a.getParent() == (c.getParent()) && a.getName().equals(c.getName())));

		final Category saved = this.categoryRepository.save(c);

		if (c.getId() == 0) {
			Category parent = this.categoryRepository.findOne(c.getParent().getId());
			parent.getChildren().add(saved);
			parent = this.categoryRepository.save(parent);
		}
		return saved;
	}

	public void delete(final Category c) {
		Assert.notNull(c);
		//The root category should not be deleted.
		Assert.isTrue(!(c.getParent() == null));

		this.categoryRepository.delete(c);

		final Category parent = c.getParent();
		parent.getChildren().remove(c);
		this.categoryRepository.save(parent);

	}

	//Other methods

	//Returns the category to which a certain trip is associated.
	public Category categoryOfService(final domain.Service s) {
		return this.categoryRepository.categoryOfService(s);
	}
}
