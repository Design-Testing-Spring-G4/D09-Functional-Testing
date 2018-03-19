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

<spring:message code="manager.userAccount.username" var="username" />
<spring:message code="manager.userAccount.password" var="password" />
<spring:message code="manager.name" var="name" />
<spring:message code="manager.surname" var="surname" />
<spring:message code="manager.email" var="email" />
<spring:message code="manager.birthDate" var="birthDate" />
<spring:message code="manager.phone" var="phone" />
<spring:message code="manager.address" var="address" />
<spring:message code="manager.vat" var="vat" />
<spring:message code="manager.save" var="save" />
<spring:message code="manager.cancel" var="cancel" />

<security:authorize access="isAnonymous()">

<form:form id="form" action="${requestURI}" modelAttribute="manager">

	<%-- Forms --%>
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="userAccount" />
	<form:hidden path="userAccount.authorities" />
	<form:hidden path="services" />
	
	
	<form:label path="userAccount.username">
		<jstl:out value="${username}" />:
	</form:label>
			<form:input path="userAccount.username" />
			<form:errors cssClass="error" path="userAccount.username" />
	<br />
	
	<form:label path="userAccount.password">
		<jstl:out value="${password}" />:
	</form:label>
			<form:password path="userAccount.password" />
			<form:errors cssClass="error" path="userAccount.password" />
	<br />
		
	<form:label path="name">
		<jstl:out value="${name}" />:
	</form:label>
			<form:input path="name" />
			<form:errors cssClass="error" path="name" />
	<br />
	
	<form:label path="surname">
		<jstl:out value="${surname}" />:
	</form:label>
			<form:input path="surname" />
			<form:errors cssClass="error" path="surname" />
	<br />
	
	<form:label path="birthDate">
		<jstl:out value="${birthDate}" />:
	</form:label>
			<form:input path="birthDate" placeholder="dd/MM/yy"/>
			<form:errors cssClass="error" path="birthDate" />
	<br />
	
	<form:label path="email">
		<jstl:out value="${email}" />:
	</form:label>
			<form:input path="email" placeholder="mail@mail.com"/>
			<form:errors cssClass="error" path="email" />
	<br />
	
	<form:label path="phone">
		<jstl:out value="${phone}" />:
	</form:label>
			<form:input id="phone" path="phone" placeholder="+CC 654654654"/>
			<form:errors cssClass="error" path="phone" />
	<br />
	
	<form:label path="address">
		<jstl:out value="${address}" />:
	</form:label>
			<form:input path="address" />
			<form:errors cssClass="error" path="address" />
	<br />
	
	<form:label path="vat">
		<jstl:out value="${vat}" />:
	</form:label>
			<form:input path="vat" />
			<form:errors cssClass="error" path="vat" />
	<br />
	
	<%-- Buttons --%>
	
	<input type="submit" name="save" value="${save}" /> 
	
	<input type="button" name="cancel" value="${cancel}"
		onclick="javascript: relativeRedir('welcome/index.do');" />
		
</form:form>
</security:authorize>
