<%--
 * display.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- Stored message variables --%>

<spring:message code="rendezvous.details" var="msgDisplay" />
<spring:message code="rendezvous.name"  var="msgName" />
<spring:message code="rendezvous.description"  var="msgDescription"/>
<spring:message code="rendezvous.moment"  var="msgMoment"/>
<spring:message code="rendezvous.picture"  var="msgPicture"/>
<spring:message code="rendezvous.coordinates" var="msgCoordinates" />
<spring:message code="rendezvous.finalMode" var="msgFinalMode" />
<spring:message code="rendezvous.deleted" var="msgDeleted" />
<spring:message code="rendezvous.adultOnly" var="msgAdultOnly" />
<spring:message code="rendezvous.creator" var="msgCreator" />
<spring:message code="rendezvous.links" var="msgLinks" />
<spring:message code="rendezvous.announcements" var="msgAnnouncements" />
<spring:message code="rendezvous.attendants" var="msgAttendants" />
<spring:message code="rendezvous.comments" var="msgComments" />
<spring:message code="rendezvous.questions" var="msgQuestions" />
<spring:message code="rendezvous.related" var="msgRelated" />
<spring:message code="rendezvous.return" var="msgReturn" />
<spring:message code="rendezvous.dateint" var="formatDate" />

	<%-- For the selected rendezvous in the list received as model, display the following information: --%>
	
	<security:authorize access="permitAll()">
	<jstl:out value="${msgName}" />:
	<jstl:out value="${rendezvous.name}" />
	<br />
	
		
	<jstl:out value="${msgDescription}" />:
	<jstl:out value="${rendezvous.description}" />
	<br />
	
	<jstl:out value="${msgMoment}" />:
	<fmt:formatDate value="${rendezvous.moment}" pattern="${formatDate}" />
	<br />
	
	<jstl:out value="${msgPicture}" />:
	<jstl:out value="${rendezvous.picture}" />
	<br />
	
	<jstl:out value="${msgCoordinates}" />:
	<jstl:out value="${rendezvous.coordinates}" />
	<br />
	
	<jstl:out value="${msgFinalMode}" />:
	<jstl:out value="${rendezvous.finalMode}" />
	<br />
	
	<jstl:out value="${msgDeleted}" />:
	<jstl:out value="${rendezvous.deleted}" />
	<br />
	
	<jstl:out value="${msgAdultOnly}" />:
	<jstl:out value="${rendezvous.adultOnly}" />
	<br />
	
	<spring:url var="displayUrl"
		value="user/display.do">
		<spring:param name="varId"
			value="${rendezvous.creator.id}"/>
	</spring:url>
	
	<jstl:out value="${msgCreator}" />:
	<a href="${displayUrl}"><jstl:out value="${rendezvous.creator.name} ${rendezvous.creator.surname}" /></a>
	<br />
	
	<spring:url var="listUrl"
		value="user/listAttendants.do">
		<spring:param name="varId"
			value="${rendezvous.id}"/>
	</spring:url>

	<a href="${listUrl}"><jstl:out value="${msgAttendants}" /></a>
	<br />
		
	<spring:url var="relatedUrl"
		value="rendezvous/listRelated.do">
		<spring:param name="varId"
			value="${rendezvous.id}"/>
	</spring:url>

	<a href="${relatedUrl}"><jstl:out value="${msgRelated}" /></a>
	<br />
	
	</security:authorize>

<a href="rendezvous/list.do"><jstl:out value="${msgReturn}" /></a>