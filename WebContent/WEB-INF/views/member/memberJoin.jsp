<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="../temp/header.jsp"/>
</head>
<body>
<c:import url="../temp/bootstrap.jsp"/>
	<div class="container">	
		<h2>회원가입</h2>
		
		  <form action="./memberJoin" id="frm" method="post" enctype="multipart/form-data">
		    <div class="form-group">
		    	<label for="id">ID:</label>
		    	<input type="text" class="form-control" id="id" placeholder="Enter id" name="id">
				<input type="hidden" id="idConfirm" value="0">
				<input type="button" value="중복확인" id="idCheck">
				<div id="idd"></div>
		    </div>
		    <div class="form-group">
		    	<label for="pwd">Password:</label>
		    	<input type="password" class="form-control " id="pw1" placeholder="Enter password" >
		    	<div id="pwd1"></div>
		    </div>
		    <div class="form-group">
		    	<label for="pwd">Password confirm:</label>
		    	<input type="password" class="form-control" id="pw2" placeholder="Enter password" name="pw">
		    	<div id="pwd2"></div>
		    </div>
		    
		    <div class="form-group">
		    	<label for="name">name:</label>
		    	<input type="text" class="form-control" id="name" placeholder="Enter name" name="name">
		    </div>
		    
		    	<div class="form-group">
		    	<label for="phone">Phone:</label>
		    	<input type="tel" class="form-control" id="phone" placeholder="Enter phone" name="phone">
		    </div>
		    
		    <div class="form-group">
		    	<label for="email">Email:</label>
		    	<input type="email" class="form-control" id="email" placeholder="Enter email" name="email">
		    	<div id="emaild"></div>
		    </div>
		    
			<div class="form-group">
		    	<label for="age">age:</label>
		    	<input type="number" class="form-control" id="age" placeholder="Enter age" name="age">
		    	<div id="aged"></div>
		    </div>
		    <div class="form-group">
		      <label for="file">File:</label>
		      <input type="file" class="form-control" id="f1" name="f1">
		    </div>
		    <input type="submit"  class="btn btn-default" value="join" id="join">
		  </form>
	</div>

<script type="text/javascript">


	$(function() {
		$('#pw2').keyup(function() {
			if($('#pw1').val()!=$('#pw2').val()){
				$('#pwd2').text("비밀번호가 일치하지 않습니다");
			} else if($('#pw1').val()==$('#pw2').val()){
					$('#pwd2').text("");
			}
		});
		

		$('#email').keyup(function() {
			var n = null;
			n = $('#email').val().indexOf("@");
			if(n<0) {
				$('#emaild').text("이메일 형식으로 입력하세요");				
			}else $('#emaild').text("");
		});

		$('#pw1').keyup(function() {
			if($('#pw1').val().length<6) {
				$('#pwd1').text("6자 이상으로 입력하세요");
			} else $('#pwd1').text("");
		});
		
		
		$('#join').click(function() {
			var r = true;
			$(".form-control").each(function() {
				if($(this).val()==''){
					r=false;
				}
			});
			
			if(r) {
				$('#frm').submit();	
				
			} else alert("모두 작성하세요");
		});
		
	});
				



</script>
	






</body>
</html>