<%--
 * list.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- Stored message variables --%>

<spring:message code="service.name" var="name" />
<spring:message code="service.description" var="description" />
<spring:message code="service.picture" var="picture" />
<spring:message code="service.cancelled" var="cancelled" />
<spring:message code="service.manager" var="manager" />
<spring:message code="service.edit" var="edit" />
<spring:message code="service.delete" var="delete" />
<spring:message code="service.cancel" var="cancel" />
<spring:message code="service.create" var="create" />
<spring:message code="service.return" var="msgReturn" />

<%-- Listing grid --%>
<security:authorize access="permitAll()">

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="services" requestURI="${requestURI}" id="row">

	<%-- Attributes --%>

	<display:column property="name" title="${name}" sortable="true" />

	<display:column property="description" title="${description}" sortable="true" />
	
	<display:column property="picture" title="${picture}" sortable="true" />
	
	<display:column property="cancelled" title="${cancelled}" sortable="true" />
	
	<display:column property="manager" title="${manager}" sortable="true" />

	<%-- Links towards edition, display and others --%>

	<security:authorize access="hasRole('MANAGER')">

	<spring:url var="editUrl" value="service/manager/edit.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<jstl:if test="${requestURI == 'service/manager/list.do'}">
		<display:column>
			<a href="${editUrl}"><jstl:out value="${edit}" /></a>
		</display:column>
	</jstl:if>
	
	<spring:url var="deleteUrl" value="service/manager/delete.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>
	
	<jstl:if test="${requestURI == 'service/manager/list.do'}">
		<display:column>
			<a href="${deleteUrl}"><jstl:out value="${delete}" /></a>
		</display:column>
	</jstl:if>
	
</security:authorize>
	
<security:authorize access="hasRole('ADMIN')">

	<spring:url var="cancelUrl"	value="service/administrator/cancel.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column>
		<a href="${cancelUrl}"><jstl:out value="${cancel}" /></a>
	</display:column>
		
</security:authorize>
</display:table>

<security:authorize access="hasRole('MANAGER')">

	<spring:url var="createUrl" value="service/manager/create.do"/>
	<a href="${createUrl}"><jstl:out value="${create}" /></a>
	
</security:authorize>

<br/>
<input type="button" name="return" value="${msgReturn}"
	onclick="javascript: relativeRedir('welcome/index.do');" />

</security:authorize>