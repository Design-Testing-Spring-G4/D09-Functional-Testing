
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RendezvousService;
import services.UserService;
import domain.User;

@Controller
@RequestMapping("user")
public class UserController extends AbstractController {

	//Services

	@Autowired
	private UserService			userService;

	@Autowired
	private RendezvousService	rendezvousService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<User> users;

		users = this.userService.findAll();

		result = new ModelAndView("user/list");
		result.addObject("users", users);
		result.addObject("rendezvousId", 0);
		result.addObject("requestURI", "user/list.do");

		return result;
	}

	@RequestMapping(value = "/listAttendants", method = RequestMethod.GET)
	public ModelAndView listAttendants(@RequestParam final int varId) {
		final ModelAndView result;
		final Collection<User> users;

		users = this.rendezvousService.findOne(varId).getAttendants();

		result = new ModelAndView("user/list");
		result.addObject("users", users);
		result.addObject("rendezvousId", varId);
		result.addObject("requestURI", "user/list.do");

		return result;
	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		ModelAndView result;
		User user;

		user = this.userService.findOne(varId);
		Assert.notNull(user);

		result = new ModelAndView("user/display");
		result.addObject("user", user);
		result.addObject("requestURI", "user/display.do");

		return result;
	}
}
