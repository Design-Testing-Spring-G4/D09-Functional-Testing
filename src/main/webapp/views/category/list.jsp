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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%-- Stored message variables --%>

<spring:message code="category.name" var="name" />
<spring:message code="category.description" var="description" />
<spring:message code="category.parent" var="parent" />
<spring:message code="category.children" var="children" />
<spring:message code="category.services" var="services" />
<spring:message code="category.rendezvous" var="rendezvous" />

<spring:message code="category.create" var="create" />
<spring:message code="category.delete" var="delete" />
<spring:message code="category.edit" var="edit" />
<spring:message code="category.manage" var="manage" />
<spring:message code="category.confirm.delete" var="confirm" />
<spring:message code="category.return" var="msgReturn" />

<%-- Listing grid --%>

<security:authorize access="hasRole('ADMIN')">

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="categories" requestURI="${requestURI}" id="row">

	<%-- Attributes --%>

	<display:column property="name" title="${name}" sortable="true" />
	
	<display:column property="description" title="${description}" sortable="true" />

	<display:column property="parent.name" title="${parent}" sortable="true" />

	<%-- Links towards display, apply, edit and cancel views --%>

	<spring:url var="rendezvousUrl" value="rendezvous/listCategory.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column>
		<a href="${rendezvousUrl}"><jstl:out value="${rendezvous}" /></a>
	</display:column>
	
	<spring:url var="childrenListUrl" value="category/administrator/childrenList.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>
	
	<display:column>
		<a href="${childrenListUrl}"><jstl:out value="${children}" /></a>
	</display:column>
	
	<spring:url var="editUrl" value="category/administrator/edit.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>
	
	<display:column>
		<a href="${editUrl}"><jstl:out value="${edit}" /></a>
	</display:column>
	
	<spring:url var="manageUrl" value="category/administrator/manage.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>
	
	<display:column>
		<a href="${manageUrl}"><jstl:out value="${manage}" /></a>
	</display:column>

</display:table>

<spring:url var="createUrl" value="category/administrator/create.do"/>
	<a href="${createUrl}"><jstl:out value="${create}"/></a>

<jstl:if test="${requestURI == 'category/administrator/childrenList.do'}">
	<br/>
	<spring:url var="returnUrl" value="category/administrator/list.do" />
		<a href="${returnUrl}"><jstl:out value="${msgReturn}" /></a>
</jstl:if>

</security:authorize>