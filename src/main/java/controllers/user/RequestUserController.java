
package controllers.user;

import java.util.Collection;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import services.RendezvousService;
import services.RequestService;
import controllers.AbstractController;
import converters.CreditCardToStringConverter;
import converters.StringToCreditCardConverter;
import domain.CreditCard;
import domain.Rendezvous;
import domain.Request;

@Controller
@RequestMapping("request/user")
public class RequestUserController extends AbstractController {

	//Services

	@Autowired
	private RequestService		requestService;

	@Autowired
	private RendezvousService	rendezvousService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int varId) {
		final ModelAndView result;
		Request request;

		request = this.requestService.create(varId);
		result = this.createEditModelAndView(request);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Request request, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(request);
		else
			try {
				final Request saved = this.requestService.save(request);

				//Creation of cookie with credit card info for user.
				final CreditCard cc = saved.getCreditCard();
				final CreditCardToStringConverter converter = new CreditCardToStringConverter();
				final Cookie cookie = new Cookie("ccard", converter.convert(cc));
				cookie.setSecure(true);
				cookie.setMaxAge(1000000);
				cookie.setPath("/");
				//response.addCookie(cookie);

				result = new ModelAndView("redirect:/service/actor/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(request, "request.commit.error");
			}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Request request) {
		ModelAndView result;

		result = this.createEditModelAndView(request, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Request request, final String messageCode) {
		ModelAndView result;
		final Collection<Rendezvous> rendezvouses = this.rendezvousService.findAll();
		CreditCard creditCard;

		//Recovery of cookie with credit card info.
		final HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		final StringToCreditCardConverter converter = new StringToCreditCardConverter();
		if (this.getCookieByName(req, "ccard") != null)
			creditCard = converter.convert(this.getCookieByName(req, "ccard").getValue());
		else
			creditCard = new CreditCard();

		result = new ModelAndView("request/create");
		result.addObject("request", request);
		result.addObject("rendezvouses", rendezvouses);
		result.addObject("creditCard", creditCard);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "rendezvous/user/create.do");

		return result;
	}

	protected Cookie getCookieByName(final HttpServletRequest request, final String name) {
		if (request.getCookies() == null)
			return null;
		for (int i = 0; i < request.getCookies().length; i++)
			if (request.getCookies()[i].getName().equals(name))
				return request.getCookies()[i];
		return null;
	}
}
