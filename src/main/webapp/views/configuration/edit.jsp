<%--
 * create.jsp
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
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<%-- Stored message variables --%>

<spring:message code="configuration.companyName" var="name" />
<spring:message code="configuration.banner" var="banner" />
<spring:message code="configuration.welcomeEN" var="welcomeEN" />
<spring:message code="configuration.welcomeES" var="welcomeES" />
<spring:message code="configuration.save" var="save" />
<spring:message code="configuration.cancel" var="cancel" />

<security:authorize access="hasRole('ADMIN')">

<form:form action="${requestURI}" modelAttribute="configuration">

	<%-- Forms --%>
	
	<form:hidden path="id" />
	<form:hidden path="version" />
		
	<form:label path="companyName">
		<jstl:out value="${name}" />:
	</form:label>
			<form:input path="companyName" />
			<form:errors cssClass="error" path="companyName" />
	<br />
	
	<form:label path="banner">
		<jstl:out value="${banner}" />:
	</form:label>
			<form:input path="banner" />
			<form:errors cssClass="banner" path="banner" />
	<br />
	
	<form:label path="welcomeEN">
		<jstl:out value="${welcomeEN}" />:
	</form:label>
			<form:input path="welcomeEN" />
			<form:errors cssClass="error" path="welcomeEN" />
	<br />
	
	<form:label path="welcomeES">
		<jstl:out value="${welcomeES}" />:
	</form:label>
			<form:input path="welcomeES" />
			<form:errors cssClass="error" path="welcomeES" />
	<br />
	
	<%-- Buttons --%>
	
	<input type="submit" name="save" value="${save}" /> 
	
	<input type="button" name="cancel" value="${cancel}"
		onclick="javascript: relativeRedir('welcome/index.do');" />
		
</form:form>
</security:authorize>
