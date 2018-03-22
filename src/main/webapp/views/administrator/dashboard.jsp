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
<spring:message code="administrator.queryb1" var="queryb1" />
<spring:message code="administrator.queryb2" var="queryb2" />
<spring:message code="administrator.queryb3" var="queryb3" />
<spring:message code="administrator.querya1" var="querya1" />
<spring:message code="administrator.querya2" var="querya2" />
<spring:message code="administrator.querya3" var="querya3" />
<spring:message code="administrator.query2c1" var="query2c1" />
<spring:message code="administrator.query2c2" var="query2c2" />
<spring:message code="administrator.query2c3" var="query2c3" />
<spring:message code="administrator.query2b1" var="query2b1" />
<spring:message code="administrator.query2b2" var="query2b2" />
<spring:message code="administrator.query2b3" var="query2b3" />
<spring:message code="administrator.query2b4" var="query2b4" />
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
			<td><jstl:out value="${avgStddevRendezvousPerUser}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${queryc2}" /></td>
			<td><jstl:out value="${ratioRendezvousVsNotRendezvous}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${queryc3}" /></td>
			<td><jstl:out value="${avgStddevUserPerRendezvous}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${queryc4}" /></td>
			<td><jstl:out value="${avgStddevAttendancePerUser}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${queryc5}" /></td>
			<td><jstl:out value="${topTenRendezvous}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${queryb1}" /></td>
			<td><jstl:out value="${avgStddevAnnouncementsPerRendezvous}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${queryb2}" /></td>
			<td><jstl:out value="${announcementsWithAboveAverageRendezvous}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${queryb3}" /></td>
			<td><jstl:out value="${announcementsWithLinksAboveAverageRendezvous}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${querya1}" /></td>
			<td><jstl:out value="${avgStddevQuestionsPerRendezvous}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${querya2}" /></td>
			<td><jstl:out value="${avgStddevAnswer}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${querya3}" /></td>
			<td><jstl:out value="${avgStddevRepliesPerComment}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query2c1}" /></td>
			<td><jstl:out value="${bestSellingServices}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query2c2}" /></td>
			<td><jstl:out value="${managerWithAboveAverageServices}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query2c3}" /></td>
			<td><jstl:out value="${managerWithMoreServiceCancelled}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query2b1}" /></td>
			<td><jstl:out value="${avgCategoriesPerRendezvous}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query2b2}" /></td>
			<td><jstl:out value="${avgRatioServiceByCategory}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query2b3}" /></td>
			<td><jstl:out value="${avgMinMaxStddevRequestsPerRendezvous}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query2b4}" /></td>
			<td><jstl:out value="${topSellingServices}" /></td>
		</tr>
	</table>

	<a href="welcome/index.do"><jstl:out value="${returnMsg}" /></a>

</security:authorize>