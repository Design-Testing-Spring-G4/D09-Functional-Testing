
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
import services.AnswerService;
import controllers.AbstractController;
import domain.Answer;
import domain.Rendezvous;
import domain.User;

@Controller
@RequestMapping("answer/user")
public class AnswerUserController extends AbstractController {

	//Services

	@Autowired
	private ActorService	actorService;

	@Autowired
	private AnswerService	answerService;


	//Creation

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Answer> answers;
		User user;

		user = (User) this.actorService.findByPrincipal();
		answers = user.getAnswers();

		result = new ModelAndView("answer/list");
		result.addObject("answers", answers);
		result.addObject("requestURI", "answer/user/list.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Answer answer;

		answer = this.answerService.create();
		result = this.createEditModelAndView(answer);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		Answer answer;

		answer = this.answerService.findOne(varId);
		Assert.notNull(answer);
		result = this.createEditModelAndView(answer);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Answer answer, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(answer);
		else
			try {
				this.answerService.save(answer);
				result = new ModelAndView("redirect:/answer/user/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(answer, "answer.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Answer answer, final BindingResult binding) {
		ModelAndView result;

		try {
			this.answerService.delete(answer);
			result = new ModelAndView("redirect:/answer/user/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(answer, "answer.commit.error");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Answer answer) {
		ModelAndView result;

		result = this.createEditModelAndView(answer, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Answer answer, final String messageCode) {
		ModelAndView result;
		Collection<Rendezvous> rendezvouses;

		rendezvouses = ((User) this.actorService.findByPrincipal()).getAttendance();

		result = new ModelAndView("rendezvous/edit");
		result.addObject("answer", answer);
		result.addObject("rendezvouses", rendezvouses);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "rendezvous/user/edit.do");

		return result;

	}
}
