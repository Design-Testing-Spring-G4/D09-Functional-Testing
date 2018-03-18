
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
import services.QuestionService;
import services.RendezvousService;
import services.UserService;
import controllers.AbstractController;
import domain.Answer;
import domain.Question;
import domain.User;

@Controller
@RequestMapping("answer/user")
public class AnswerUserController extends AbstractController {

	//Services

	@Autowired
	private AnswerService		answerService;

	@Autowired
	private QuestionService		questionService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private UserService			userService;

	@Autowired
	private RendezvousService	rendezvousService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int varId, final int var2Id) {
		final ModelAndView result;
		final Answer answer;
		Question question;

		question = this.questionService.findOne(varId);

		answer = this.answerService.create(question.getId());
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
		final User user;

		user = (User) this.actorService.findByPrincipal();
		if (binding.hasErrors())
			result = this.createEditModelAndView(answer);
		else
			try {
				final Answer saved = this.answerService.save(answer);
				final Collection<Answer> answers = user.getAnswers();
				answers.add(saved);
				this.userService.save(user);
				result = new ModelAndView("redirect:/rendezvous/user/rsvp.do");
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

		result = new ModelAndView("answer/edit");
		result.addObject("answer", answer);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "answer/user/edit.do");

		return result;

	}
}
