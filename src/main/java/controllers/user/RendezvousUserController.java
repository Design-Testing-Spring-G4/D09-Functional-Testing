
package controllers.user;

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

import services.ActorService;
import services.RendezvousService;
import controllers.AbstractController;
import domain.Question;
import domain.Rendezvous;
import domain.User;

@Controller
@RequestMapping("rendezvous/user")
public class RendezvousUserController extends AbstractController {

	//Services

	@Autowired
	private RendezvousService		rendezvousService;

	@Autowired
	private ActorService			actorService;

	//Ancillary attributes

	private Rendezvous				current;
	private Collection<Question>	rsvpQuestions;


	public Collection<Question> getRsvpQuestions() {
		return this.rsvpQuestions;
	}

	public void setRsvpQuestions(final Collection<Question> rsvpQuestions) {
		this.rsvpQuestions = rsvpQuestions;
	}

	public Rendezvous getCurrent() {
		return this.current;
	}

	public void setCurrent(final Rendezvous current) {
		this.current = current;
	}

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Rendezvous> rendezvouses;
		User u;

		u = ((User) this.actorService.findByPrincipal());
		rendezvouses = u.getAttendance();

		result = new ModelAndView("rendezvous/list");
		result.addObject("rendezvouses", rendezvouses);
		result.addObject("userId", u.getId());
		result.addObject("requestURI", "rendezvous/user/list.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Rendezvous rendezvous;

		rendezvous = this.rendezvousService.create();
		result = this.createEditModelAndView(rendezvous);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		Rendezvous rendezvous;
		rendezvous = this.rendezvousService.findOne(varId);
		Assert.notNull(rendezvous);

		if (rendezvous.getFinalMode() == true || rendezvous.getDeleted() == true)
			result = new ModelAndView("redirect:/rendezvous/user/list.do");
		else
			result = this.createEditModelAndView(rendezvous);
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		final ModelAndView result;
		Rendezvous rendezvous;
		rendezvous = this.rendezvousService.findOne(varId);
		Assert.notNull(rendezvous);

		if (rendezvous.getFinalMode() == true)
			result = new ModelAndView("redirect:/rendezvous/user/list.do");
		else {
			rendezvous.setDeleted(true);
			this.rendezvousService.save(rendezvous);
			result = new ModelAndView("redirect:/rendezvous/user/list.do");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Rendezvous rendezvous, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(rendezvous);
		else
			try {
				final Rendezvous saved = this.rendezvousService.save(rendezvous);
				this.rendezvousService.addRendezvous(saved);
				result = new ModelAndView("redirect:/rendezvous/user/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(rendezvous, "rendezvous.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Rendezvous rendezvous, final BindingResult binding) {
		ModelAndView result;

		try {
			this.rendezvousService.delete(rendezvous);
			result = new ModelAndView("redirect:/rendezvous/user/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(rendezvous, "rendezvous.commit.error");
		}
		return result;
	}

	//Cancellation

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int varId) {
		final ModelAndView result;
		Rendezvous rendezvous;

		rendezvous = this.rendezvousService.findOne(varId);
		this.rendezvousService.deleteRendezvous(rendezvous);
		result = new ModelAndView("redirect:/rendezvous/user/list.do");
		return result;
	}

	//RSVP

	@RequestMapping(value = "/rsvp", method = RequestMethod.GET)
	public ModelAndView rsvp(@Valid final int varId) {
		ModelAndView result;
		Rendezvous rendezvous;
		Collection<Question> questions = new ArrayList<Question>();

		rendezvous = this.rendezvousService.findOne(varId);
		if (this.getCurrent() != rendezvous) {
			this.setCurrent(rendezvous);
			this.setRsvpQuestions(rendezvous.getQuestions());
		}
		questions = this.getRsvpQuestions();
		if (!this.getRsvpQuestions().isEmpty()) {
			final Question question = questions.iterator().next();
			result = new ModelAndView("redirect:/answer/user/create.do?varId=" + question.getId());
			questions.remove(question);
			this.setRsvpQuestions(questions);
		} else
			result = new ModelAndView("redirect:/rendezvous/user/list.do");

		return result;
	}
	//Linking

	@RequestMapping(value = "/link", method = RequestMethod.GET)
	public ModelAndView link(@Valid final int varId) {
		ModelAndView result;
		Rendezvous r;
		Collection<Rendezvous> rendezvouses;

		r = this.rendezvousService.findOne(varId);
		this.setCurrent(r);
		rendezvouses = ((User) this.actorService.findByPrincipal()).getRendezvous();
		result = new ModelAndView("rendezvous/link");
		result.addObject("rendezvouses", rendezvouses);
		result.addObject("requestURI", "rendezvous/user/link.do");

		return result;
	}

	@RequestMapping(value = "/linkSave", method = RequestMethod.GET)
	public ModelAndView linkSave(@Valid final int varId) {
		ModelAndView result;
		final Rendezvous current = this.getCurrent();
		final Rendezvous r = this.rendezvousService.findOne(varId);

		final Collection<Rendezvous> links = r.getLinks();
		if (!links.contains(current) && current.getCreator().getId() != this.actorService.findByPrincipal().getId()) {
			links.add(current);
			r.setLinks(links);
			this.rendezvousService.saveInternal(r);
		}
		result = new ModelAndView("redirect:/rendezvous/user/list.do");
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
		result.addObject("requestURI", "rendezvous/user/edit.do");

		return result;

	}
}
