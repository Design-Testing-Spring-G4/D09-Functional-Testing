
package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ServiceService;
import controllers.AbstractController;
import domain.Service;

@Controller
@RequestMapping("service/actor")
public class ServiceActorController extends AbstractController {

	//Services

	@Autowired
	private ServiceService	serviceService;


	//Listing	

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Service> services;

		services = this.serviceService.findAll();

		result = new ModelAndView("service/list");
		result.addObject("services", services);
		result.addObject("requestURI", "service/actor/list.do");

		return result;
	}
	//Creation

	//Edition

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
		result.addObject("requestURI", "service/edit.do");

		return result;

	}
}
