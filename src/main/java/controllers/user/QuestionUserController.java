
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

import services.QuestionService;
import services.RendezvousService;
import controllers.AbstractController;
import domain.Question;
import domain.Rendezvous;

@Controller
@RequestMapping("question/user")
public class QuestionUserController extends AbstractController {

	//Services

	@Autowired
	private RendezvousService	rendezvousService;

	@Autowired
	private QuestionService		questionService;


	//Creation

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int varId) {
		final ModelAndView result;
		Collection<Question> questions;
		Rendezvous rendezvous;

		rendezvous = this.rendezvousService.findOne(varId);
		questions = rendezvous.getQuestions();

		result = new ModelAndView("question/list");
		result.addObject("questions", questions);
		result.addObject("rendezvousId", varId);
		result.addObject("requestURI", "question/user/list.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int varId) {
		final ModelAndView result;
		Question question;

		question = this.questionService.create(varId);
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
				result = new ModelAndView("redirect:/rendezvous/user/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(question, "question.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		Question question;

		question = this.questionService.findOne(varId);
		Assert.notNull(question);
		try {
			this.questionService.delete(question);
			result = new ModelAndView("redirect:/rendezvous/user/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/rendezvous/user/list.do");
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

		result = new ModelAndView("question/edit");
		result.addObject("modelQuestion", question);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "question/user/edit.do");

		return result;

	}

}
