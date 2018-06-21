<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Events</title>
</head>
<body>
	<h2><c:out value="${currEvent.name}"/></h2>
	<table>
		<tr>
			<td>Host:</td>
			<td><c:out value="${currEvent.host.firstName}"/> <c:out value="${currEvent.host.lastName}"/></td>
		</tr>
		<tr>
			<td>Date:</td>
			<td><c:out value="${currEvent.date}"/></td>
		</tr>
		<tr>
			<td>Location:</td>
			<td><c:out value="${currEvent.location}"/></td>
		</tr>
		<tr>
			<td>Number of people attending this event:</td>
			<td><c:out value="${currEvent.attendees.size()}"/></td>
		</tr>
	</table>
	<br>
	<table>
		<tr>
			<th>Name</th>
			<th>Location</th>
		</tr>
		<c:forEach var="user" items="${usersAttending}">
		<c:if test="${currUser != user}">
		<tr>
			<td>${user.firstName} ${user.lastName}</td>
			<td>${user.location}</td>
		</tr>
		</c:if>
		</c:forEach>
	</table>
	<br>
	<h2>Message Wall</h2>
	
	<c:forEach var="message" items="${currEvent.messages}">
		<p>${message.user.firstName} says: ${message.content}</p>
	</c:forEach>
	
	<form:form method="POST" action="/message/new/${currEvent.id}" modelAttribute="messageToAdd">
    	<p>
    		<form:label path="content">Content:</form:label>
    		<form:textarea rows="5" cols="25" path="content"/>
    	</p>
        <input type="submit" value="Add Message"/>
    </form:form>
</body>
</html>