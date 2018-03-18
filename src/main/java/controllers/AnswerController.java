
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.RendezvousService;
import domain.Answer;
import domain.Rendezvous;
import domain.User;

@Controller
@RequestMapping("answer")
public class AnswerController extends AbstractController {

	//Services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private RendezvousService	rendezvousService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int varId, final int var2Id) {
		final ModelAndView result;
		final Collection<Answer> answers = new ArrayList<Answer>();
		User user;
		Rendezvous rendezvous;

		user = (User) this.actorService.findOne(var2Id);
		rendezvous = this.rendezvousService.findOne(varId);
		System.out.println(rendezvous.getId());
		Assert.notNull(rendezvous);
		for (final Answer a : user.getAnswers()) {
			System.out.println(a.getQuestion().getRendezvous().getId());
			if (a.getQuestion().getRendezvous() == rendezvous)
				answers.add(a);
		}

		System.out.println(answers.toString());

		result = new ModelAndView("answer/list");
		result.addObject("answers", answers);
		result.addObject("requestURI", "answer/list.do");

		return result;
	}
}
