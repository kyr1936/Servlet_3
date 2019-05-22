<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="../temp/bootstrap.jsp" />
</head>
<body>
<c:import url="../temp/header.jsp"></c:import>

	<div class="container">
		<h1>NoticeUpdate Page</h1>
		<form action="./noticeUpdate" method="post" enctype="multipart/form-data" >
		
			<input type="hidden" name="num" value="${noticeDTO.num }">
		    <div class="form-group">
		      <label for="title">Title:</label>
		      <input type="text" class="form-control" value="${noticeDTO.title }" id="title" name="Title">
		    </div>
		    <div class="form-group">
		      <label for="writer">Writer:</label>
		      <input type="text" class="form-control" value= "${noticeDTO.writer }"   id="writer" name="writer" readonly="readonly">
		    </div>
		    <div class="form-group">
		      <label for="contents">Contents:</label>
		       <textarea class="form-control" rows="5" value="${noticeDTO.contents }" id="contents" name="contents"></textarea>
		    </div>
    		<div class="form-group">
		      <label for="file">File:</label>
		      <input type="file" class="form-control" value="${f1 }" id="f1" name="f1">
		    </div>
    	<button type="submit" class="btn btn-danger">Update</button>
  </form>	
</div>
	
	
	
	
	
	
</body>
</html>