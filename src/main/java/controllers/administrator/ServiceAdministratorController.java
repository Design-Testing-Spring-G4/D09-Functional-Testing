
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import repositories.ServiceRepository;
import controllers.AbstractController;
import domain.Service;

@Controller
@RequestMapping("service/administrator")
public class ServiceAdministratorController extends AbstractController {

	//Services

	@Autowired
	private ServiceRepository	serviceRepository;


	//Listing	

	//Creation

	//Edition
	//Cancellation

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	//	public ModelAndView cancel(@RequestParam final int varId) {
	////		final ModelAndView result;
	////		Service service;
	////
	////		service = this.serviceService.findOne(varId);
	////		Assert.notNull(service);
	////		result = this.createCancelModelAndView(service);
	////
	////		return result;
	//	}
	public Service cancel(final domain.Service service) {
		Assert.notNull(service);

		service.setCancelled(true);

		final domain.Service saved = this.serviceRepository.save(service);

		return saved;
	}
	//
	//	@RequestMapping(value = "/cancel", method = RequestMethod.POST, params = "cancel")
	//	public ModelAndView saveCancel(@Valid final Service service, final BindingResult binding) {
	//		ModelAndView result;
	//
	//		if (binding.hasErrors())
	//			result = this.createCancelModelAndView(service);
	//		else
	//			try {
	//				this.serviceService.cancel(service);
	//				result = new ModelAndView("redirect:/service/actor/list.do");
	//			} catch (final Throwable oops) {
	//				result = this.createCancelModelAndView(service, "service.cancel.error");
	//			}
	//		return result;
	//	}

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

	protected ModelAndView createCancelModelAndView(final Service service) {
		ModelAndView result;

		result = this.createCancelModelAndView(service, null);

		return result;
	}

	protected ModelAndView createCancelModelAndView(final Service service, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("service/cancel");
		result.addObject("servicio", service);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "service/administrator/cancel.do");

		return result;
	}
}
