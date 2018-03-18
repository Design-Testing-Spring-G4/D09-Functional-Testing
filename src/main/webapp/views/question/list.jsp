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

<spring:message code="question.list" var="msgList" />
<spring:message code="question.text" var="msgQuestion" />
<spring:message code="question.display" var="msgDisplay" />
<spring:message code="question.edit" var="msgEdit" />
<spring:message code="question.delete" var="msgDelete" />
<spring:message code="question.answer" var="msgAnswer"/>
<spring:message code="question.return" var="msgReturn"/>
<spring:message code="question.create" var="msgCreate"/>

<security:authorize access="hasRole('USER')">

<display:table pagesize="5" class="displaytag" name="questions" requestURI="${requestURI}" id="row">
	
	<%-- Attributes --%>

	<display:column property="text" title="${msgQuestion}" sortable="true" />
	
	<spring:url var="editUrl" value="question/user/edit.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column>
		<a href="${editUrl}"><jstl:out value="${msgEdit}" /></a>
	</display:column>
	
	<spring:url var="deleteUrl" value="question/user/delete.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column>
		<a href="${deleteUrl}"><jstl:out value="${msgDelete}" /></a>
	</display:column>

</display:table>

<br/>
<spring:url var="createUrl" value="question/user/create.do">
	<spring:param name="varId" value="${rendezvousId}" />
</spring:url>
	<a href="${createUrl}"><jstl:out value="${msgCreate}" /></a>
	
<br/>
<input type="button" name="return" value="${msgReturn}"
	onclick="javascript: relativeRedir('rendezvous/user/list.do');" />
</security:authorize>