<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>  
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
	<p>Edit Event</p>
	
	<form:form action="/events/${currEvent.id}/edit" method="post" modelAttribute="event">
		<input type="hidden" name="_method" value="put">
	    <p>
	        <form:label path="name">Name:</form:label>
	        <form:errors path="name"/>
	        <form:input value="${currEvent.name}" path="name"/>
	    </p>  
	    <p>
	    	<label>Date:</label>
	    	<input type="date" name="day"/>
	    </p>
	    <p>
	        <form:label path="location">Location:</form:label>
	        <form:errors path="location"/>
	        <form:input value="${currEvent.location}" path="location"/>
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