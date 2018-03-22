
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ServiceService;
import controllers.AbstractController;
import domain.Service;

@Controller
@RequestMapping("service/administrator")
public class ServiceAdministratorController extends AbstractController {

	//Services

	@Autowired
	private ServiceService	serviceService;


	//Cancellation

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int varId) {
		ModelAndView result;
		final Service service;

		service = this.serviceService.findOne(varId);
		service.setCancelled(true);

		try {
			this.serviceService.saveInternal(service);
			result = new ModelAndView("redirect:/service/actor/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/service/actor/list.do");
		}

		return result;
	}
}
