<%--
 * manage.jsp
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
<spring:message code="category.parent" var="parent" />
<spring:message code="category.setParent" var="setParent" />
<spring:message code="category.setChild" var="setChild" />
<spring:message code="category.clear" var="clear" />
<spring:message code="category.return" var="returnMsg" />

<security:authorize access="hasRole('ADMIN')">

<%-- Listing grid --%>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="categories" requestURI="${requestURI}" id="row">
	
	<%-- Attributes --%>

	<display:column property="name" title="${name}" sortable="true" />

	<display:column property="parent.name" title="${parent}" sortable="true" />

	<%-- Link to set as parent or child --%>

	<spring:eval var="contains" expression="category.children.contains(row.id)" />
	
	<spring:url var="setParentUrl" value="category/administrator/setParent.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>
	
	<spring:url var="setChildUrl" value="category/administrator/setChild.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column>
		<jstl:if test="${contains == false && row.id != category.parent.id}">
			<a href="${setParentUrl}"><jstl:out value="${setParent}" /></a>
			
			<a href="${setChildUrl}"><jstl:out value="${setChild}" /></a>
		</jstl:if>
	</display:column>
	
</display:table>

<spring:url var="clearUrl" value="category/administrator/clear.do" />
<a href="${clearUrl}"><jstl:out value="${clear}" /></a>

<br/>
<input type="button" name="return" value="${returnMsg}"
	onclick="javascript: relativeRedir('category/administrator/list.do');" />
	
</security:authorize>