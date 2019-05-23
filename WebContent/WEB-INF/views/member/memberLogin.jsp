<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="../temp/header.jsp" />
</head>
<body>
<c:import url="../temp/bootstrap.jsp" />
	
	<div class="container">
	<h1>Login Page</h1>
	<form action ="../index.do" method="post">		
		<div class="form-group">
    		<label for="id">ID:</label>
    		<input type="text" class="form-control " id="id" placeholder="Enter id" name="id">
    	</div>
    	<div class="form-group">
    		<label for="pw">Password:</label>
    		<input type="password" class="form-control " id="pw" placeholder="Enter password" name="pw">
    	</div>
    	<div class="checkbox">
      <label><input type="checkbox" name="remember"> Remember me</label>
    	</div>
    	<button type="submit" class="btn btn-default">submit</button>
    	</form>	
    </div>
	
</body>
</html>