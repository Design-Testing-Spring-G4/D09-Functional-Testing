
package controllers.user;

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
import services.QuestionService;
import services.RendezvousService;
import controllers.AbstractController;
import domain.Question;
import domain.Rendezvous;
import domain.User;

@Controller
@RequestMapping("question/user")
public class QuestionUserController extends AbstractController {

	//Services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private RendezvousService	rendezvousService;

	@Autowired
	private QuestionService		questionService;


	//Creation

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Question> questions;
		User user;

		user = (User) this.actorService.findByPrincipal();
		questions = this.rendezvousService.questionsByUser(user);

		result = new ModelAndView("question/list");
		result.addObject("questions", questions);
		result.addObject("requestURI", "question/user/list.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Question question;

		question = this.questionService.create();
		result = this.createEditModelAndView(question);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		Question question;

		question = this.questionService.findOne(varId);
		Assert.notNull(question);
		result = this.createEditModelAndView(question);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Question question, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(question);
		else
			try {
				this.questionService.save(question);
				result = new ModelAndView("redirect:/question/user/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(question, "question.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Question question, final BindingResult binding) {
		ModelAndView result;

		try {
			this.questionService.delete(question);
			result = new ModelAndView("redirect:/question/user/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(question, "question.commit.error");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Question question) {
		ModelAndView result;

		result = this.createEditModelAndView(question, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Question question, final String messageCode) {
		ModelAndView result;
		Collection<Rendezvous> rendezvouses;

		rendezvouses = ((User) this.actorService.findByPrincipal()).getRendezvous();

		result = new ModelAndView("rendezvous/edit");
		result.addObject("question", question);
		result.addObject("rendezvouses", rendezvouses);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "rendezvous/user/edit.do");

		return result;

	}

}
