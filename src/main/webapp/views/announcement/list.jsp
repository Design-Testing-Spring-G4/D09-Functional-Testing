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

<spring:message code="announcement.title" var="title" />
<spring:message code="announcement.moment" var="moment" />
<spring:message code="announcement.description" var="description" />
<spring:message code="announcement.delete" var="delete" />
<spring:message code="announcement.return" var="msgReturn" />
<spring:message code="announcement.create" var="create" />
<spring:message code="announcement.dateint" var="formatDate" />

<security:authorize access="permitAll">

	<display:table pagesize="5" class="displaytag" name="announcements" requestURI="${requestURI}" id="row">
		
		<%-- Attributes --%>
	
		<jstl:if test="${requestURI == 'announcement/user/list.do'}">
			<display:column title="${moment}" sortable="false" defaultorder="descending">
				<fmt:formatDate value="${row.moment}" pattern="${formatDate}"/>
			</display:column>
			
			<display:column title="${title}" sortable="false">
				<jstl:out value="${row.title}"/>
			</display:column>
			
			<display:column title="${description}" sortable="false">
				<jstl:out value="${row.description}"/>
			</display:column>
		</jstl:if>
		
		<jstl:if test="${requestURI != 'announcement/user/list.do'}">
			<display:column title="${moment}" sortable="true">
				<fmt:formatDate value="${row.moment}" pattern="${formatDate}"/>
			</display:column>
		
			<display:column title="${title}" sortable="true">
				<jstl:out value="${row.title}"/>
			</display:column>
			
			<display:column title="${description}" sortable="true">
				<jstl:out value="${row.description}"/>
			</display:column>
		</jstl:if>
		
		<security:authorize access="hasRole('ADMIN')">
		
		<spring:url var="deleteUrl" value="announcement/administrator/delete.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
	
		<display:column>
			<a href="${deleteUrl}"><jstl:out value="${delete}" /></a>
		</display:column>
		</security:authorize>
	
	</display:table>
	<br />
	
	<security:authorize access="hasRole('USER')">
	<spring:url var="createUrl" value="announcement/user/create.do"/>
		<a href="${createUrl}"><jstl:out value="${create}"/></a>
		
	</security:authorize>
	
	<a href="rendezvous/list.do"><jstl:out value="${msgReturn}" /></a>
</security:authorize>