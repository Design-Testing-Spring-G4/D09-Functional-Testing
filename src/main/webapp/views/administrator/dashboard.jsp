<%--
 * dashboard.jsp
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<%-- Stored message variables --%>

<spring:message code="administrator.querycol" var="querycol" />
<spring:message code="administrator.valuecol" var="valuecol" />
<spring:message code="administrator.queryc1" var="queryc1" />
<spring:message code="administrator.queryc2" var="queryc2" />
<spring:message code="administrator.queryc3" var="queryc3" />
<spring:message code="administrator.queryc4" var="queryc4" />
<spring:message code="administrator.queryc5" var="queryc5" />
<spring:message code="administrator.queryc6" var="queryc6" />
<spring:message code="administrator.queryc7" var="queryc7" />
<spring:message code="administrator.queryc8" var="queryc8" />
<spring:message code="administrator.queryc9" var="queryc9" />
<spring:message code="administrator.queryc10" var="queryc10" />
<spring:message code="administrator.queryc11" var="queryc11" />
<spring:message code="administrator.return" var="returnMsg" />

<security:authorize access="hasRole('ADMIN')">

	<%-- Displays the result of all required database queries --%>

	<table style="width: 100%">
		<tr>
			<th><jstl:out value="${querycol}" /></th>
			<th><jstl:out value="${valuecol}" /></th>
		</tr>
		<tr>
			<td><jstl:out value="${queryc1}" /></td>
			<td><jstl:out value="${Nombre query1}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${queryc2}" /></td>
			<td><jstl:out value="${Nombre query2}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${queryc3}" /></td>
			<td><jstl:out value="${Nombre query3}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${queryc4}" /></td>
			<td><jstl:out value="${Nombre query4}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${queryc5}" /></td>
			<td><jstl:out value="${Nombre query5}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${queryc6}" /></td>
			<td><jstl:out value="${Nombre query6}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${queryc7}" /></td>
			<td><jstl:out value="${Nombre query7}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${queryc8}" /></td>
			<td><jstl:out value="${Nombre query8}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${queryc9}" /></td>
			<td><jstl:out value="${Nombre query9}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${queryc10}" /></td>
			<td><jstl:out value="${Nombre query10}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${queryc11}" /></td>
			<td><jstl:out value="${Nombre query11}" /></td>
		</tr>
		<tr>
	</table>

	<a href="welcome/index.do"><jstl:out value="${returnMsg}" /></a>

</security:authorize>