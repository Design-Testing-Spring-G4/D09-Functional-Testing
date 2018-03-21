
package controllers;

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

import services.ActorService;
import services.ManagerService;
import domain.Manager;

@Controller
@RequestMapping("manager")
public class ManagerController extends AbstractController {

	//Services

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private ActorService	actorService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Manager> managers;

		managers = this.managerService.findAll();

		result = new ModelAndView("manager/list");
		result.addObject("managers", managers);
		result.addObject("rendezvousId", 0);
		result.addObject("requestURI", "manager/list.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Manager manager;

		manager = this.managerService.create();
		result = this.createEditModelAndView(manager);

		return result;
	}
	//Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		Manager manager;
		manager = (Manager) this.actorService.findByPrincipal();
		Assert.notNull(manager);
		result = this.createEditModelAndView(manager);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Manager manager, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(manager);
		else
			try {
				this.actorService.hashPassword(manager);
				this.managerService.save(manager);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(manager, "manager.commit.error");
			}
		return result;
	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		ModelAndView result;
		Manager manager;

		manager = this.managerService.findOne(varId);
		Assert.notNull(manager);

		result = new ModelAndView("manager/display");
		result.addObject("manager", manager);
		result.addObject("requestURI", "manager/display.do");

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Manager manager) {
		ModelAndView result;

		result = this.createEditModelAndView(manager, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Manager manager, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("manager/edit");
		result.addObject("manager", manager);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "manager/edit.do");

		return result;

	}
}
