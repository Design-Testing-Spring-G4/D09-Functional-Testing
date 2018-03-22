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
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AnswerService;
import services.CategoryService;
import services.CommentService;
import services.ManagerService;
import services.RendezvousService;
import services.ServiceService;
import services.UserService;
import domain.Manager;
import domain.Rendezvous;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}


	//Supporting services

	@Autowired
	private UserService			userService;

	@Autowired
	private RendezvousService	rendezvousService;

	@Autowired
	private CommentService		commentService;

	@Autowired
	private AnswerService		answerService;

	@Autowired
	private ServiceService		serviceService;

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private CategoryService		categoryService;


	//Dashboard
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;
		final Collection<String> topTenRendezvous = new ArrayList<String>();
		final Collection<String> announcementsWithAboveAverageRendezvous = new ArrayList<String>();
		final Collection<String> announcementsWithLinksAboveAverageRendezvous = new ArrayList<String>();
		final Collection<String> bestSellingServices = new ArrayList<String>();
		final Collection<String> managerWithAboveAverageServices = new ArrayList<String>();
		final List<String> topSellingServices = new ArrayList<String>();

		//Parse the collections to display the entities' names.
		for (final Rendezvous r : this.rendezvousService.topTenRendezvous())
			topTenRendezvous.add(r.getName());

		for (final Rendezvous r : this.rendezvousService.announcementsWithAboveAverageRendezvous())
			announcementsWithAboveAverageRendezvous.add(r.getName());

		for (final Rendezvous r : this.rendezvousService.announcementsWithLinksAboveAverageRendezvous())
			announcementsWithLinksAboveAverageRendezvous.add(r.getName());

		for (final domain.Service s : this.serviceService.bestSellingServices())
			bestSellingServices.add(s.getName());

		for (final Manager m : this.managerService.managerWithAboveAverageServices())
			managerWithAboveAverageServices.add(m.getName());

		for (final domain.Service s : this.serviceService.topSellingServices(5))
			topSellingServices.add(s.getName());

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
		result.addObject("avgStddevAnswer", Arrays.toString(this.answerService.avgStddevAnswer()));
		result.addObject("avgStddevRepliesPerComment", Arrays.toString(this.commentService.avgStddevRepliesPerComment()));
		result.addObject("bestSellingServices", bestSellingServices);
		result.addObject("managerWithAboveAverageServices", managerWithAboveAverageServices);
		result.addObject("managerWithMoreServiceCancelled", this.managerService.managerWithMoreServiceCancelled().getName());
		result.addObject("avgCategoriesPerRendezvous", this.rendezvousService.avgCategoriesPerRendezvous());
		result.addObject("avgRatioServiceByCategory", this.categoryService.avgRatioServiceByCategory());
		result.addObject("avgMinMaxStddevRequestsPerRendezvous", Arrays.toString(this.rendezvousService.avgMinMaxStddevRequestsPerRendezvous()));
		result.addObject("topSellingServices", topSellingServices);

		result.addObject("requestURI", "administrator/dashboard.do");

		return result;
	}
}
