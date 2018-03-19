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

<spring:message code="request.service" var="service" />
<spring:message code="request.rendezvous" var="rendezvous" />
<spring:message code="request.creditCard.holder" var="ccHolder" />
<spring:message code="request.creditCard.brand" var="ccBrand" />
<spring:message code="request.creditCard.number" var="ccNumber" />
<spring:message code="request.creditCard.expMonth" var="ccExpMonth" />
<spring:message code="request.creditCard.expYear" var="ccExpYear" />
<spring:message code="request.creditCard.cvv" var="ccCvv" />
<spring:message code="request.comments" var="comments" />
<spring:message code="request.save" var="save" />
<spring:message code="request.cancel" var="cancel" />

<security:authorize access="hasRole('USER')">

<form:form action="${requestURI}" modelAttribute="request">

	<%-- Forms --%>
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:label path="service">
		<jstl:out value="${service}" />:
	</form:label>
	
	<form:select path="service">
		<form:option label="----" value="0" />
		<form:options items="${services}" itemLabel="name"/>
	</form:select>
		<form:errors cssClass="error" path="service" />
		
	<form:label path="rendezvous">
		<jstl:out value="${rendezvous}" />:
	</form:label>
	
	<form:select path="rendezvous">
		<form:option label="----" value="0" />
		<form:options items="${rendezvouses}" itemLabel="name"/>
	</form:select>
		<form:errors cssClass="error" path="rendezvous" />
		
	<form:label path="creditCard.holder">
		<jstl:out value="${ccHolder}" />:
	</form:label>
			<form:input path="creditCard.holder" />
			<form:errors cssClass="error" path="creditCard.holder" />
	<br />
	
	<form:label path="creditCard.brand">
		<jstl:out value="${ccBrand}" />:
	</form:label>
			<form:input path="creditCard.brand" />
			<form:errors cssClass="error" path="creditCard.brand" />
	<br />
	
	<form:label path="creditCard.number">
		<jstl:out value="${ccNumber}" />:
	</form:label>
			<form:input path="creditCard.number" />
			<form:errors cssClass="error" path="creditCard.number" />
	<br />
	
	<form:label path="creditCard.expMonth">
		<jstl:out value="${ccExpMonth}" />:
	</form:label>
			<form:input path="creditCard.expMonth" />
			<form:errors cssClass="error" path="creditCard.expMonth" />
	<br />
	
	<form:label path="creditCard.expYear">
		<jstl:out value="${ccExpYear}" />:
	</form:label>
			<form:input path="creditCard.expYear" />
			<form:errors cssClass="error" path="creditCard.expYear" />
	<br />
	
	<form:label path="creditCard.cvv">
		<jstl:out value="${ccCvv}" />:
	</form:label>
			<form:input path="creditCard.cvv" />
			<form:errors cssClass="error" path="creditCard.cvv" />
	<br />
	
	<form:label path="comments">
		<jstl:out value="${comments}" />:
	</form:label>
			<form:input path="comments" />
			<form:errors cssClass="error" path="comments" />
	<br />
	
	<%-- Buttons --%>
	
	<input type="submit" name="save" value="${save}" />
	
	<input type="button" name="cancel" value="${cancel}"
		onclick="javascript: relativeRedir('service/list.do');" />
		
</form:form>
</security:authorize>
