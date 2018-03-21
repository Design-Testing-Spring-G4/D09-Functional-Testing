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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- Stored message variables --%>

<spring:message code="rendezvous.list" var="list" />
<spring:message code="rendezvous.name" var="name" />
<spring:message code="rendezvous.description" var="description" />
<spring:message code="rendezvous.moment" var="moment" />
<spring:message code="rendezvous.address" var="postalAddress" />
<spring:message code="rendezvous.phone" var="phoneNumber" />
<spring:message code="rendezvous.email" var="emailAddress" />
<spring:message code="rendezvous.details" var="details" />
<spring:message code="rendezvous.edit" var="msgEdit" />
<spring:message code="rendezvous.announcements" var="announcements" />
<spring:message code="rendezvous.similar" var="similarRendezvouses" />
<spring:message code="rendezvous.createAnnouncement" var="createAnnouncement" />
<spring:message code="rendezvous.create" var="createRendezvous" />
<spring:message code="rendezvous.questions" var="questions" />
<spring:message code="rendezvous.comments" var="comments" />
<spring:message code="rendezvous.users" var="users" />
<spring:message code="rendezvous.return" var="msgReturn" />
<spring:message code="rendezvous.RSVP" var="msgRSVP" />
<spring:message code="rendezvous.delete" var="msgDelete" />
<spring:message code="rendezvous.cancel" var="msgCancel" />
<spring:message code="rendezvous.deleted" var="deleted" />
<spring:message code="rendezvous.dateint" var="formatDate" />
<spring:message code="rendezvous.passed" var="passed" />
<spring:message code="rendezvous.link" var="link" />
<spring:message code="rendezvous.children" var="children" />
<spring:message code="rendezvous.parent" var="parent" />
<spring:message code="rendezvous.negative" var="negative" />
<spring:message code="rendezvous.allCategory" var="allCategory" />

<jsp:useBean id="now" class="java.util.Date"/>

<security:authorize access="permitAll()">

<%-- Conditional to display category list in the appropriate view --%>

<jstl:if test="${requestURI == 'rendezvous/listCategory.do'}" >
	<jstl:out value="${category.name}" />.&nbsp;
	<jstl:out value="${parent}" />:&nbsp;
	<jstl:choose>
		<jstl:when test="${category.parent != null}">
			<spring:url var="parentUrl" value="rendezvous/listCategory.do">
				<spring:param name="varId" value="${category.parent.id}" />
			</spring:url>
			<a href="${parentUrl}"><jstl:out value="${category.parent.name}" /></a>&nbsp;
		</jstl:when>
		<jstl:otherwise>
			<jstl:out value="${negative}"/>.&nbsp;
		</jstl:otherwise>
	</jstl:choose>
	<jstl:out value="${children}" />:&nbsp;
	<jstl:choose>
		<jstl:when test="${fn:length(category.children) != 0}">
			<jstl:forEach var="child" items="${childrenCategories}">
				<spring:url var="childUrl" value="rendezvous/listCategory.do">
					<spring:param name="varId" value="${child.id}" />
				</spring:url>
				<a href="${childUrl}"><jstl:out value="${child.name}" /></a>&nbsp;
			</jstl:forEach>
		</jstl:when>
		<jstl:otherwise>
			<jstl:out value="${negative}"/>.&nbsp;
		</jstl:otherwise>
	</jstl:choose>
	<br/>
	<jstl:out value="${allCategory}" />:&nbsp;
	<jstl:forEach var="cat" items="${categories}">
		<spring:url var="catUrl" value="rendezvous/listCategory.do">
			<spring:param name="varId" value="${cat.id}" />
		</spring:url>
		<a href="${catUrl}"><jstl:out value="${cat.name}" /></a>&nbsp;
	</jstl:forEach>
</jstl:if>
<br/>

<%-- Listing grid --%>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="rendezvouses" requestURI="${requestURI}" id="row">

	<%-- Attributes --%>

	<display:column property="name" title="${name}" sortable="true" />

	<display:column property="description" title="${description}"
		sortable="true" />

	<display:column title="${moment}" sortable="true">
		<jstl:if test="${row.moment > now}">
			<fmt:formatDate value="${row.moment}" pattern="${formatDate}" />
		</jstl:if>
		
		<jstl:if test="${row.moment <= now}">
			<jstl:out value="${passed}" />
		</jstl:if>
	</display:column>

	<display:column property="deleted" title="${deleted}" sortable="true" />
	
	<%-- Links towards edition, display and others --%>

	<spring:url var="displayUrl" value="rendezvous/display.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column>
		<a href="${displayUrl}"><jstl:out value="${details}" /></a>
	</display:column>

	<spring:url var="userUrl" value="user/listAttendants.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column>
		<a href="${userUrl}"><jstl:out value="${users}" /></a>
	</display:column>
	
	<spring:url var="announcementsUrl" value="announcement/list.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column>
		<a href="${announcementsUrl}"><jstl:out value="${announcements}" /></a>
	</display:column>

	<security:authorize access="hasRole('USER')">

	<spring:url var="editUrl" value="rendezvous/user/edit.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<jstl:if test="${requestURI == 'rendezvous/user/list.do'}">
		<display:column>
			<jstl:if test="${row.finalMode == false && row.deleted == false && row.creator.id == userId}">
				<a href="${editUrl}"><jstl:out value="${msgEdit}" /></a>
			</jstl:if>
		</display:column>
	</jstl:if>
	
	<spring:url var="questionUrl" value="question/user/list.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>
	
	<jstl:if test="${requestURI == 'rendezvous/user/list.do'}">
		<display:column>
			<jstl:if test="${row.finalMode == false && row.deleted == false && row.creator.id == userId}">
				<a href="${questionUrl}"><jstl:out value="${questions}" /></a>
			</jstl:if>
		</display:column>
	</jstl:if>
	
	<spring:url var="linkUrl" value="rendezvous/user/link.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>
	
	<display:column>
			<a href="${linkUrl}"><jstl:out value="${link}" /></a>
	</display:column>
	
	<spring:url var="rsvpUrl" value="rendezvous/user/rsvp.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column>
		<a href="${rsvpUrl}"><jstl:out value="${msgRSVP}" /></a>
	</display:column>

	<spring:url var="cancelUrl" value="rendezvous/user/cancel.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column>
		<a href="${cancelUrl}"><jstl:out value="${msgCancel}" /></a>
	</display:column>

	<spring:url var="commentsUrl" value="comment/user/list.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column>
		<a href="${commentsUrl}"><jstl:out value="${comments}" /></a>
	</display:column>

	<spring:url var="similarRendezvousesUrl" value="rendezvous/listRelated.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column>
		<a href="${similarRendezvousesUrl}"><jstl:out value="${similarRendezvouses}" /></a>
	</display:column>

	<spring:url var="deleteUrl"	value="rendezvous/user/delete.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<jstl:if test="${requestURI == 'rendezvous/user/list.do'}">
	<display:column>
		<jstl:if test="${row.finalMode == false && row.deleted == false && row.creator.id == userId}">
			<a href="${deleteUrl}"><jstl:out value="${msgDelete}" /></a>
		</jstl:if>
	</display:column>
	</jstl:if>
	
</security:authorize>
	
<security:authorize access="hasRole('ADMIN')">

	<spring:url var="removeUrl"	value="rendezvous/administrator/delete.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column>
		<a href="${removeUrl}"><jstl:out value="${msgDelete}" /></a>
	</display:column>

	<spring:url var="commentsUrl" value="comment/administrator/list.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column>
		<a href="${commentsUrl}"><jstl:out value="${comments}" /></a>
	</display:column>
		
</security:authorize>
</display:table>

<security:authorize access="hasRole('USER')">

	<spring:url var="createRendezvousUrl" value="rendezvous/user/create.do"/>
	<a href="${createRendezvousUrl}"><jstl:out value="${createRendezvous}" /></a>
	
</security:authorize>

<br/>
<input type="button" name="return" value="${msgReturn}"
	onclick="javascript: relativeRedir('welcome/index.do');" />

</security:authorize>