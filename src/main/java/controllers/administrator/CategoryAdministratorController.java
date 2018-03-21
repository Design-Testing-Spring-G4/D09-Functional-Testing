
package controllers.administrator;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import controllers.AbstractController;
import domain.Category;

@Controller
@RequestMapping("category/administrator")
public class CategoryAdministratorController extends AbstractController {

	//Services

	@Autowired
	private CategoryService	categoryService;

	//Ancillary attributes

	private Category		current;


	public Category getCurrent() {
		return this.current;
	}

	public void setCurrent(final Category current) {
		this.current = current;
	}

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Category> categories;

		categories = this.categoryService.findAll();

		result = new ModelAndView("category/list");
		result.addObject("categories", categories);
		result.addObject("requestURI", "category/administrator/list.do");

		return result;
	}

	@RequestMapping(value = "/childrenList", method = RequestMethod.GET)
	public ModelAndView childrenList(@RequestParam final int varId) {
		final ModelAndView result;
		final Collection<Category> categories = new ArrayList<Category>();
		Category category;

		category = this.categoryService.findOne(varId);
		for (final Integer i : category.getChildren())
			categories.add(this.categoryService.findOne(i));

		result = new ModelAndView("category/list");
		result.addObject("categories", categories);
		result.addObject("requestURI", "category/administrator/childrenList.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Category category;

		category = this.categoryService.create();
		result = this.createEditModelAndView(category);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		Category category;
		category = this.categoryService.findOne(varId);
		Assert.notNull(category);

		result = this.createEditModelAndView(category);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Category category, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(category);
		else
			try {
				this.categoryService.save(category);
				result = new ModelAndView("redirect:/category/administrator/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(category, "category.name.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Category category, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = this.createEditModelAndView(category);
		} else
			try {
				this.categoryService.delete(category);
				result = new ModelAndView("redirect:/category/administrator/list.do");
			} catch (final Throwable oops) {
				System.out.println(oops.toString());
				result = this.createEditModelAndView(category, "category.delete.error");
			}
		return result;
	}

	//Management

	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public ModelAndView manage(@RequestParam final int varId) {
		final ModelAndView result;
		Collection<Category> categories;
		Category category;

		categories = this.categoryService.findAll();
		category = this.categoryService.findOne(varId);
		categories.remove(category);
		this.setCurrent(category);

		result = new ModelAndView("category/manage");
		result.addObject("categories", categories);
		result.addObject("category", category);
		result.addObject("requestURI", "category/administrator/manage.do");

		return result;
	}

	@RequestMapping(value = "/setParent", method = RequestMethod.GET)
	public ModelAndView setParent(@RequestParam final int varId) {
		final ModelAndView result;
		Category category;

		category = this.categoryService.findOne(varId);
		this.getCurrent().setParent(category);
		this.categoryService.save(this.getCurrent());
		final int catId = this.getCurrent().getId();
		result = new ModelAndView("redirect:/category/administrator/manage.do?varId=" + catId);

		return result;
	}

	@RequestMapping(value = "/setChild", method = RequestMethod.GET)
	public ModelAndView setChild(@RequestParam final int varId) {
		final ModelAndView result;
		Category category;

		category = this.categoryService.findOne(varId);
		this.getCurrent().getChildren().add(category.getId());
		this.categoryService.save(this.getCurrent());
		final int catId = this.getCurrent().getId();
		result = new ModelAndView("redirect:/category/administrator/manage.do?varId=" + catId);

		return result;
	}

	@RequestMapping(value = "/clear", method = RequestMethod.GET)
	public ModelAndView clear() {
		final ModelAndView result;

		this.getCurrent().setParent(null);
		this.categoryService.save(this.getCurrent());
		final int catId = this.getCurrent().getId();
		result = new ModelAndView("redirect:/category/administrator/manage.do?varId=" + catId);

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Category category) {
		ModelAndView result;

		result = this.createEditModelAndView(category, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Category category, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("category/edit");
		result.addObject("category", category);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "category/administrator/edit.do");

		return result;

	}
}
