<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Acme, Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
			<li><a class="fNiv" href="user/create.do"><spring:message
						code="master.page.register" /></a></li>
		</security:authorize>



		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>

					<security:authorize access="hasRole('USER')">
						<li><a href="user/edit.do"><spring:message
									code="master.page.user.edit" /></a></li>
						<li><a href="rendezvous/user/list.do"><spring:message
									code="master.page.user.rendezvousList" /></a></li>
						<li><a href="announcement/user/list.do"><spring:message
									code="master.page.user.announcementList" /></a></li>
					</security:authorize>

					<security:authorize access="hasRole('ADMIN')">
						<li><a href="administrator/dashboard.do"><spring:message
									code="master.page.dashboard" /></a></li>
					</security:authorize>

					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>

				</ul></li>

		</security:authorize>


		<security:authorize access="permitAll">
			<li><a class="fNiv" href="user/list.do"><spring:message
						code="master.page.userList" /></a></li>
			<li><a class="fNiv" href="rendezvous/list.do"><spring:message
						code="master.page.rendezvousList" /></a></li>
		</security:authorize>

	</ul>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>
<!-- <div>
	<jsp:useBean id="configurationInfo" class="domain.Configuration"></jsp:useBean>
	<jsp:getProperty property="banner" name="configurationInfo" /><br>
</div>-->