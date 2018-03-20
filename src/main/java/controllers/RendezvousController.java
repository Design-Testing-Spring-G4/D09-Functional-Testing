
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.RendezvousService;
import services.UserService;
import domain.Category;
import domain.Rendezvous;

@Controller
@RequestMapping("rendezvous")
public class RendezvousController extends AbstractController {

	//Services

	@Autowired
	private RendezvousService	rendezvousService;

	@Autowired
	private UserService			userService;

	@Autowired
	private CategoryService		categoryService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Rendezvous> rendezvouses;

		rendezvouses = this.rendezvousService.findAll();

		result = new ModelAndView("rendezvous/list");
		result.addObject("rendezvouses", rendezvouses);
		result.addObject("requestURI", "rendezvous/list.do");

		return result;
	}

	@RequestMapping(value = "/rendezvousUserList", method = RequestMethod.GET)
	public ModelAndView rendezvousUserList(@RequestParam final int varId) {
		final ModelAndView result;
		Collection<Rendezvous> rendezvouses;

		rendezvouses = this.userService.findOne(varId).getAttendance();

		result = new ModelAndView("rendezvous/list");
		result.addObject("rendezvouses", rendezvouses);
		result.addObject("requestURI", "rendezvous/list.do");

		return result;
	}

	@RequestMapping(value = "/listRelated", method = RequestMethod.GET)
	public ModelAndView listRelated(@RequestParam final int varId) {
		final ModelAndView result;
		Collection<Rendezvous> rendezvouses;
		Rendezvous rendezvous;

		rendezvous = this.rendezvousService.findOne(varId);
		rendezvouses = rendezvous.getLinks();

		result = new ModelAndView("rendezvous/list");
		result.addObject("rendezvouses", rendezvouses);
		result.addObject("requestURI", "rendezvous/list.do");

		return result;
	}

	@RequestMapping(value = "/listCategory", method = RequestMethod.GET)
	public ModelAndView listCategory(@RequestParam final int varId) {
		final ModelAndView result;
		final Collection<Rendezvous> rendezvouses;
		final Category category;

		category = this.categoryService.findOne(varId);
		rendezvouses = this.rendezvousService.findByCategory(category);

		result = new ModelAndView("rendezvous/list");
		result.addObject("rendezvouses", rendezvouses);
		result.addObject("requestURI", "rendezvous/list.do");

		return result;
	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		ModelAndView result;
		Rendezvous rendezvous;

		rendezvous = this.rendezvousService.findOne(varId);
		Assert.notNull(rendezvous);

		result = new ModelAndView("rendezvous/display");
		result.addObject("rendezvous", rendezvous);
		result.addObject("requestURI", "rendezvous/display.do");

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Rendezvous rendezvous) {
		ModelAndView result;

		result = this.createEditModelAndView(rendezvous, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Rendezvous rendezvous, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("rendezvous/edit");
		result.addObject("rendezvous", rendezvous);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "rendezvous/edit.do");

		return result;

	}
}
