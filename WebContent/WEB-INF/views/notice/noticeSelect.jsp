<%@page import="com.yr.notice.NoticeDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<jsp:include page="../temp/bootstrap.jsp" />

</head>
<body>
<jsp:include page="../temp/header.jsp" />
	
	<div class= "container">
		<h1>NoticeSelect Page</h1>
		<h1>Title : ${requestScope.dto.title} </h1>
		<h1>Contents : ${requestScope.dto.contents }</h1>
		<h1>Writer : ${dto.writer }</h1>
		<h1>Param : ${param.num }</h1>
		<h1>Writer : ${dto.writer ne 'test' }</h1>
		<h1>Upload : <a href="../upload/${upload.fname }">${upload.oname }</a></h1>
		
	</div>
	<a href="./noticeUpdate?num=${dto.num }">Go Update</a>
	<a href="./noticeDelete?num=${dto.num }">Go Delete</a>
	
	
	
</body>
</html>