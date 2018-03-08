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

<spring:message code="answer.list" var="msgList" />
<spring:message code="answer.answer" var="msgAnswer" />
<spring:message code="answer.question" var="msgQuestion" />
<spring:message code="answer.return" var="msgReturn"/>

<security:authorize access="hasRole('USER')">

<display:table pagesize="5" class="displaytag" name="answers" requestURI="answer/user/list.do" id="row">
	
	<%-- Attributes --%>
	
	<display:column property="question" title="${msgQuestion}" sortable="true" />

	<display:column property="answer" title="${msgAnswer}" sortable="true" />

</display:table>
<br />
</security:authorize>