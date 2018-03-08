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

<spring:message code="comment.list" var="list" />
<spring:message code="comment.moment" var="moment" />
<spring:message code="comment.createComment" var="createComment" />
<spring:message code="comment.text" var="text" />
<spring:message code="comment.picture" var="picture" />
<spring:message code="comment.return" var="msgReturn" />
<spring:message code="comment.delete" var="msgDelete" />
<spring:message code="comment.dateint" var="formatDate" />


<%-- Listing grid --%>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="comments" requestURI="${requestURI}" id="row">

	<%-- Attributes --%>

	<display:column title="${moment}" sortable="true">
		<fmt:formatDate value="${row.moment}" pattern="${formatDate}" />
	</display:column>

	<display:column property="text" title="${text}" sortable="true" />

	<display:column property="picture" title="${picture}" sortable="false" />

	<security:authorize access="hasRole('ADMIN')">
		<spring:url var="removeUrl" value="comment/administrator/delete.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column>
			<a href="${removeUrl}"><jstl:out value="${msgDelete}" /></a>
		</display:column>
	</security:authorize>

</display:table>

<security:authorize access="hasRole('USER')">
	<spring:url var="createCommentUrl" value="comment/user/create.do" />
	<a href="${createCommentUrl}"><jstl:out value="${createComment}" /></a>
	<br />
</security:authorize>
<input type="button" name="return" value="${ msgReturn}"
	onclick="javascript: relativeRedir('welcome/index.do');" />

