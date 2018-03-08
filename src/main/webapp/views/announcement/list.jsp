<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- Stored message variables --%>

<spring:message code="announcement.title" var="msgList" />
<spring:message code="announcement.moment" var="msgMoment" />
<spring:message code="announcement.delete" var="msgDelete" />
<spring:message code="announcement.return" var="msgReturn" />
<spring:message code="announcement.create" var="create" />
<spring:message code="announcement.dateint" var="formatDate" />


<display:table pagesize="5" class="displaytag" name="announcements" requestURI="announcement/rendezvous/list.do" id="row">
	
	<%-- Attributes --%>

	<display:column title="${msgMoment}" sortable="true">
		<fmt:formatDate value="${row.moment}" pattern="${formatDate}"/>
	</display:column>

	<display:column title="${msgTitle}" sortable="true">
		<fmt:formatDate value="${row.title}"/>
	</display:column>
	
	<security:authorize access="hasRole('ADMIN')">
	
	<spring:url var="deleteUrl" value="announcement/delete.do">
		<spring:param name="announcementId" value="${row.id}" />
	</spring:url>

	<display:column>
		<a href="${deleteUrl}"><jstl:out value="${msgDelete}" /></a>
	</display:column>
	</security:authorize>

</display:table>
<br />

<security:authorize access="hasRole('USER')">
<spring:url var="createUrl" value="announcement/user/create.do"/>
	<a href="${createUrl}"><jstl:out value="${create}"/></a>
	
</security:authorize>

<a href="announcement/list.do"><jstl:out value="${msgReturn}" /></a>
