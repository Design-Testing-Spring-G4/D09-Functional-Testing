/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.RendezvousService;
import services.UserService;
import domain.Rendezvous;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}


	@Autowired
	private UserService			userService;

	@Autowired
	private RendezvousService	rendezvousService;

	@Autowired
	private CommentService		commentService;


	//Dashboard

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;
		final Collection<String> topTenRendezvous = new ArrayList<String>();
		final Collection<String> announcementsWithAboveAverageRendezvous = new ArrayList<String>();
		final Collection<String> announcementsWithLinksAboveAverageRendezvous = new ArrayList<String>();

		//Parse the collection to display the trips' names.
		for (final Rendezvous r : this.rendezvousService.topTenRendezvous())
			topTenRendezvous.add(r.getName());

		for (final Rendezvous r : this.rendezvousService.announcementsWithAboveAverageRendezvous())
			announcementsWithAboveAverageRendezvous.add(r.getName());

		for (final Rendezvous r : this.rendezvousService.announcementsWithLinksAboveAverageRendezvous())
			announcementsWithLinksAboveAverageRendezvous.add(r.getName());

		result = new ModelAndView("administrator/dashboard");

		result.addObject("avgStddevRendezvousPerUser", Arrays.toString(this.userService.avgStddevRendezvousPerUser()));
		result.addObject("ratioRendezvousVsNotRendezvous", Arrays.toString(this.userService.ratioRendezvousVsNotRendezvous()));
		result.addObject("avgStddevUserPerRendezvous", Arrays.toString(this.rendezvousService.avgStddevUserPerRendezvous()));
		result.addObject("avgStddevAttendancePerUser", Arrays.toString(this.userService.avgStddevAttendancePerUser()));
		result.addObject("topTenRendezvous", topTenRendezvous);
		result.addObject("avgStddevAnnouncementsPerRendezvous", Arrays.toString(this.rendezvousService.avgStddevAnnouncementsPerRendezvous()));
		result.addObject("announcementsWithAboveAverageRendezvous", announcementsWithAboveAverageRendezvous);
		result.addObject("announcementsWithLinksAboveAverageRendezvous", announcementsWithLinksAboveAverageRendezvous);
		result.addObject("avgStddevQuestionsPerRendezvous", Arrays.toString(this.rendezvousService.avgStddevQuestionsPerRendezvous()));
		//result.addObject("avgStddevAnswersPerQuestiosnPerRendezvous", Arrays.toString(this.rendezvousService.avgStddevAnswersPerQuestiosnPerRendezvous()));
		result.addObject("avgStddevRepliesPerComment", Arrays.toString(this.commentService.avgStddevRepliesPerComment()));

		result.addObject("requestURI", "administrator/dashboard.do");

		return result;
	}
}
