
package controllers.manager;

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
import services.ServiceService;
import controllers.AbstractController;
import domain.Manager;
import domain.Service;

@Controller
@RequestMapping("service/manager")
public class ServiceManagerController extends AbstractController {

	//Services

	@Autowired
	private ServiceService	serviceService;

	@Autowired
	private ActorService	actorService;


	//Listing	

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Service> services;

		Manager m;

		m = (Manager) this.actorService.findByPrincipal();
		services = this.serviceService.servicesByManager(m);

		result = new ModelAndView("service/list");
		result.addObject("services", services);
		result.addObject("requestURI", "service/manager/list.do");

		return result;
	}
	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Service service;

		service = this.serviceService.create();
		result = this.createEditModelAndView(service);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		Service service;
		service = this.serviceService.findOne(varId);
		Assert.notNull(service);

		result = this.createEditModelAndView(service);
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		final ModelAndView result;
		Service service;
		service = this.serviceService.findOne(varId);
		Assert.notNull(service);

		if (!service.getRequests().isEmpty())
			result = new ModelAndView("redirect:/service/manager/list.do");
		else {
			this.serviceService.delete(service);
			result = new ModelAndView("redirect:/service/manager/list.do");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Service service, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println("Error: " + binding.getAllErrors());
			result = this.createEditModelAndView(service);
		} else
			try {
				this.serviceService.save(service);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(service, "service.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Service service, final BindingResult binding) {
		ModelAndView result;

		try {
			this.serviceService.delete(service);
			result = new ModelAndView("redirect:/rendezvous/user/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(service, "service.commit.error");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Service service) {
		ModelAndView result;

		result = this.createEditModelAndView(service, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Service service, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("service/edit");
		result.addObject("service", service);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "service/Manager/edit.do");

		return result;

	}
}
