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

	<h1>Register!</h1>
	
	<p><form:errors path="user.*"/></p>
        
    <form:form method="POST" action="/register" modelAttribute="user">
    	<p>
    		<form:label path="firstName">First Name:</form:label>
    		<form:input path="firstName"/>
    	</p>
    	<p>
    		<form:label path="lastName">Last Name:</form:label>
    		<form:input path="lastName"/>
    	</p>
        <p>
            <form:label path="email">Email:</form:label>
            <form:input type="email" path="email"/>
        </p>
        <p>
    		<form:label path="location">Location:</form:label>
    		<form:input path="location"/>
    	</p>
    	<p>
    		<form:label path="state">State:</form:label>
    		<form:select path="state">
    			<form:option value="CA"/>
    			<form:option value="NV"/>
    			<form:option value="OR"/>
    			<form:option value="WA"/>
    		</form:select>
    	</p>
        <p>
            <form:label path="password">Password:</form:label>
            <form:password path="password"/>
        </p>
        <p>
            <form:label path="passwordConfirmation">Password Confirmation:</form:label>
            <form:password path="passwordConfirmation"/>
        </p>
        <input type="submit" value="Register!"/>
    </form:form>
    
    <br><br>
    <h1>Login!</h1>
    
    <p><c:out value="${error}"/></p>
    <form method="post" action="/login">
        <p>
            <label for="email">Email</label>
            <input type="email" id="email" name="email"/>
        </p>
        <p>
            <label for="password">Password</label>
            <input type="password" id="password" name="password"/>
        </p>
        <input type="submit" value="Login!"/>
    </form>
    
</body>
</html>