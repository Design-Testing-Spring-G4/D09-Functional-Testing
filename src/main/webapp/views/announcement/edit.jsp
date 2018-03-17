<%--
 * edit.jsp
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<%-- Stored message variables --%>

<spring:message code="announcement.title" var="title" />
<spring:message code="announcement.description" var="description" />
<spring:message code="announcement.save" var="save" />
<spring:message code="announcement.cancel" var="cancel" />

<security:authorize access="isAuthenticated()">

	<form:form action="${requestURI}" modelAttribute="announcement">

		<%-- Form fields --%>

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="user" />
		<form:hidden path="moment" />

		<form:label path="title">
			<jstl:out value="${title}" />
		</form:label>
		<form:input path="title" />
		<form:errors cssClass="error" path="title" />
		<br />

		<form:label path="description">
			<jstl:out value="${msgDescription}" />
		</form:label>
		<form:textarea path="description" />
		<form:errors cssClass="error" path="description" />
		<br />
		
		<form:select path="rendezvous">
				<form:option label="----" value="0" />
				<form:options items="${rendezvouses}" itemLabel="name"/>
			</form:select>
			<form:errors cssClass="error" path="rendezvous" />

		<br />


		<%-- Buttons --%>

		<input type="submit" name="save" value="${save}">

		<input type="button" name="cancel" value="${cancel}"
			onclick="javascript: relativeRedir('welcome/index.do');" />
			
	</form:form>

</security:authorize>