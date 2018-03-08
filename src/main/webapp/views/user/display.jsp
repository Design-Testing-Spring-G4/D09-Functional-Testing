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

<spring:message code="user.name" var="msgName" />
<spring:message code="user.surname" var="msgSurname" />
<spring:message code="user.address"  var="msgAddress" />
<spring:message code="user.phone"  var="msgPhone"/>
<spring:message code="user.email"  var="msgEmail"/>
<spring:message code="user.return"  var="msgReturn"/>
<spring:message code="user.rendezvouses"  var="msgRendezvouses"/>

	
	<jstl:out value="${msgName}" />:
	<jstl:out value="${user.name}" />
	<br />
	
	<jstl:out value="${msgSurname}" />:
	<jstl:out value="${user.surname}" />
	<br />
		
	<jstl:out value="${msgAddress}" />:
	<jstl:out value="${user.address}" />
	<br />
	
	<jstl:out value="${msgPhone}" />:
	<jstl:out value="${user.phone}" />
	<br />
	
	<jstl:out value="${msgEmail}" />:
	<jstl:out value="${user.email}" />
	<br />
	
	<spring:url var="rendezvousesUrl"
		value="rendezvous/rendezvousUserList.do">
		<spring:param name="varId"
			value="${user.id}"/>
	</spring:url>

	<a href="${rendezvousesUrl}"><jstl:out value="${msgRendezvouses}" /></a>
	<br />
	