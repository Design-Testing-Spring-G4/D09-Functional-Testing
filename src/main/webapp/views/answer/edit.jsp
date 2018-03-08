<%--
 * edit.jsp
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

<spring:message code="answer.edit" var="msgEdit" />
<spring:message code="answer.answer" var="msgAnswer" />
<spring:message code="answer.save" var="msgSave" />
<spring:message code="answer.cancel" var="msgCancel" />

<security:authorize access="hasRole('USER')">

<form:form action="${requestURI}" modelAttribute="answer">

	<%-- Form fields --%>
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
		<form:label path="answer">
		<jstl:out value="${msgAnswer}" />:
	</form:label>
			<form:input path="answer" />
			<form:errors cssClass="error" path="answer" />
	<br /><br />
	
	<%-- Buttons --%>
	
	<input type="submit" name="save" value="${msgSave}">
	
	<input type="button" name="cancel" value="${msgCancel}"
			onclick="javascript: relativeRedir('question/list.do');" />

</form:form>

</security:authorize>