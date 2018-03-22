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

<spring:message code="service.name" var="name" />
<spring:message code="service.description" var="description" />
<spring:message code="service.picture" var="picture" />
<spring:message code="service.category" var="category" />
<spring:message code="service.save" var="save" />
<spring:message code="service.cancel" var="cancel" />

<security:authorize access="hasRole('MANAGER')">

<form:form action="${requestURI}" modelAttribute="service">

	<%-- Forms --%>
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="cancelled" />
	<form:hidden path="manager" />
	<form:hidden path="requests" />
		
	<form:label path="name">
		<jstl:out value="${name}" />:
	</form:label>
			<form:input path="name" />
			<form:errors cssClass="error" path="name" />
	<br />
	
	<form:label path="description">
		<jstl:out value="${description}" />:
	</form:label>
			<form:input path="description" />
			<form:errors cssClass="error" path="description" />
	<br />
	
	<form:label path="picture">
		<jstl:out value="${picture}" />:
	</form:label>
			<form:input path="picture" />
			<form:errors cssClass="error" path="picture" />
	<br />
	
	<form:label path="category">
		<jstl:out value="${category}" />:
	</form:label>
	
	<form:select path="category">
		<form:option label="----" value="0" />
		<form:options items="${categories}" itemLabel="name"/>
	</form:select>
		<form:errors cssClass="error" path="category" />
	
	<%-- Buttons --%>
	
	<br/>
	<input type="submit" name="save" value="${save}" /> 
	
	<input type="button" name="cancel" value="${cancel}"
		onclick="javascript: relativeRedir('service/manager/list.do');" />
		
</form:form>
</security:authorize>
