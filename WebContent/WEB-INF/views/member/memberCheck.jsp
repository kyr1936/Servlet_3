<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="../temp/header.jsp"></c:import>

</head>
<body>
<c:import url="../temp/bootstrap.jsp"></c:import>

	<div class="container" >
		<h1>약관 동의 페이지</h1>
			<div class="checkbox">
		      <label><input type="checkbox" id="checkAll"> 전체동의</label>
		    </div>
		    <div class="checkbox">
		      <label><input type="checkbox" class="check"> 동의 A</label>
		    </div>
		    <div class="checkbox">
		      <label><input type="checkbox" class="check"> 동의 B</label>
		    </div>
		    <div class="checkbox">
		      <label><input type="checkbox" class="check"> 동의 C</label>
		    </div>
		    
		<input type="button"  class="btn btn-default" id="join" value="join">
	</div>	
<script type="text/javascript">

$(function() {
	$('#checkAll').click(function() {
		var c = $(this).prop('checked');
		$('.check').prop('checked', c);
	});
	
	$('.check').click(function() {
		var c=true;
		$('.check').each(function() {
			if(!$(this).prop('checked')) {
				c=false;
			}
		});
		$('#checkAll').prop('checked', c);
	});
	$('#join').click(function() {
		var c = $('#checkAll').prop('checked');
		
		if (c) {
			location.href="./memberJoin";
		} else {
			alert("약관에 동의해주세요");
		}
		
	});
});

</script>
	
	
	
	
	
	
</body>
</html>