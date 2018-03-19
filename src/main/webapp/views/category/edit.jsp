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

<%-- Stored message variables --%>
<security:authorize access="hasRole('ADMIN')">

	<spring:message code="category.create" var="create" />
	<spring:message code="category.edit" var="edit" />
	<spring:message code="category.name" var="name" />
	<spring:message code="category.parent" var="parent" />
	<spring:message code="category.save" var="save" />
	<spring:message code="category.delete" var="delete" />
	<spring:message code="category.confirm.delete" var="confirm" />
	<spring:message code="category.cancel" var="cancel" />


	<form:form action="${requestURI}" modelAttribute="category">

		<%-- Forms --%>

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="trip" />
		<form:hidden path="children" />

		<form:label path="name">
			<jstl:out value="${name}" />:
	</form:label>
		<form:input path="name" />
		<form:errors cssClass="error" path="name" />
		<br />

	<jstl:if test="${category.id!=0}">
	<form:hidden path="parent"/>
	</jstl:if>
	<jstl:if test="${category.id==0}">
	
		<form:label path="parent">
			<jstl:out value="${parent}" />:
	</form:label>
		<form:select path="parent">
			<form:option label="----" value="0" />
			<form:options items="${categories}" itemLabel="name" itemValue="id" />
		</form:select>
		<form:errors cssClass="error" path="parent" />
		<br />
		</jstl:if>
		
		<br />
		<br />
		<%-- Buttons --%>

		<input type="submit" name="save" value="${save}" />&nbsp; 

		
	<jstl:if test="${category.id!=0}">
			<input type="submit" name="delete" value="${delete}"
				onclick="return confirm('${confirm}')" />&nbsp;
	</jstl:if>

		<input type="button" name="cancel" value="${cancel}"
			onclick="javascript: relativeRedir('category/administrator/list.do');" />
	</form:form>

</security:authorize>