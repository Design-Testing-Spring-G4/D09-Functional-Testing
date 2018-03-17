<%--
 * list.jsp
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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- Stored message variables --%>

<spring:message code="rendezvous.name" var="name" />
<spring:message code="rendezvous.description" var="description" />
<spring:message code="rendezvous.link" var="link" />
<spring:message code="rendezvous.return" var="msgReturn" />

<jsp:useBean id="now" class="java.util.Date"/>

<%-- Listing grid --%>
<security:authorize access="hasRole('USER')">

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="rendezvouses" requestURI="${requestURI}" id="row">

	<%-- Attributes --%>

	<display:column property="name" title="${name}" sortable="true" />

	<display:column property="description" title="${description}"
		sortable="true" />

	<spring:url var="linkUrl" value="rendezvous/user/linkSave.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column>
		<a href="${linkUrl}"><jstl:out value="${link}" /></a>
	</display:column>

</display:table>

<input type="button" name="return" value="${msgReturn}"
	onclick="javascript: relativeRedir('rendezvous/user/list.do');" />

</security:authorize>