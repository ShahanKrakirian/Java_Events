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
	<h1>Welcome, <c:out value="${currUser.firstName}"/>!</h1>
	<a href="/logout">Logout</a>
	<br><br>
	<p>Some events in your state:</p>
	<br>
	<table>
		<tr>
			<th>Name</th>
			<th>Date</th>
			<th>Location</th>
			<th>Host</th>
			<th>Action/Status</th>
		</tr>
		<c:forEach var="event" items="${eventsHostedInState}">
		<tr>
			<td><a href="/events/${event.id}">${event.name}</a></td>
			<td>${event.date}</td>
			<td>${event.location}</td>
			<td>${event.host.firstName}</td>
			<td>
				<a href="/events/${event.id}/edit">Edit</a>
				<form action="/events/${event.id}/delete" method="post">
					<input type="hidden" name="_method" value="delete">
					<input type="submit" value="Delete"/>
				</form>
			</td>
		</tr>
		</c:forEach>
		<c:forEach var="event" items="${eventsJoinedInState}">
		<tr>
			<td><a href="/events/${event.id}">${event.name}</a></td>
			<td>${event.date}</td>
			<td>${event.location}</td>
			<td>${event.host.firstName}</td>
			<td>Joined <form action="/events/${event.id}/cancel" method="post"><input type="hidden" name="_method" value="delete"><input type="submit" value="Cancel"/></form></td>
		</tr>
		</c:forEach>
		<c:forEach var="event" items="${eventsNotJoinedInState}">
		<tr>
			<td><a href="/events/${event.id}">${event.name}</a></td>
			<td>${event.date}</td>
			<td>${event.location}</td>
			<td>${event.host.firstName}</td>
			<td><form action="/events/${event.id}/join" method="post"><input type="hidden" name="_method" value="put"><input type="submit" value="Join"/></form></td>
		</tr>
		</c:forEach>
	</table>
	<br>
	<p>Some events in nearby states:</p>
	<table>
		<tr>
			<th>Name</th>
			<th>Date</th>
			<th>Location</th>
			<th>State</th>
			<th>Host</th>
			<th>Action</th>
		</tr>
		<c:forEach var="event" items="${eventsHostedOutOfState}">
		<tr>
			<td><a href="/events/${event.id}">${event.name}</a></td>
			<td>${event.date}</td>
			<td>${event.location}</td>
			<td>${event.state}</td>
			<td>${event.host.firstName}</td>
			<td>
				<a href="/events/${event.id}/edit">Edit</a>
				<form action="/events/${event.id}/delete" method="post">
					<input type="hidden" name="_method" value="delete">
					<input type="submit" value="Delete"/>
				</form>
			</td>
		</tr>
		</c:forEach>
		<c:forEach var="event" items="${eventsJoinedOutOfState}">
		<tr>
			<td><a href="/events/${event.id}">${event.name}</a></td>
			<td>${event.date}</td>
			<td>${event.location}</td>
			<td>${event.state}</td>
			<td>${event.host.firstName}</td>
			<td>Joined <form action="/events/${event.id}/cancel" method="post"><input type="hidden" name="_method" value="delete"><input type="submit" value="Cancel"/></form></td>
		</tr>
		</c:forEach>
		<c:forEach var="event" items="${eventsNotJoinedOutOfState}">
		<tr>
			<td><a href="/events/${event.id}">${event.name}</a></td>
			<td>${event.date}</td>
			<td>${event.location}</td>
			<td>${event.state}</td>
			<td>${event.host.firstName}</td>
			<td><form action="/events/${event.id}/join" method="post"><input type="hidden" name="_method" value="put"><input type="submit" value="Join"/></form></td>
		</tr>
		</c:forEach>
	</table>
	<br><br>
	<c:out value="${error}"/>
	<h3>Create an Event</h3>
	<form:form action="/events/new" method="post" modelAttribute="eventToAdd">
	    <p>
	        <form:label path="name">Name:</form:label>
	        <form:errors path="name"/>
	        <form:input path="name"/>
	    </p>  
	    <p>
	    	<label>Date:</label>
	    	<input type="date" name="day"/>
	    </p>
	    <p>
	        <form:label path="location">Location:</form:label>
	        <form:errors path="location"/>
	        <form:input path="location"/>
	    </p>
	    <p>
	    	<form:label path="state">State:</form:label>
	    	<form:errors path="state"/>
	    	<form:select path="state">
	        	<form:option value="CA"/>
	        	<form:option value="NV"/>
	        	<form:option value="OR"/>
	        	<form:option value="WA"/>
	        </form:select>
	    </p>	    
	    <input type="submit" value="Submit"/>
	</form:form>
</body>
</html>





