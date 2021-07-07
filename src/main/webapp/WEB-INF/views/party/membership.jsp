<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../includes/header.jsp"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!-- End of Topbar -->

<!-- Begin Page Content -->
<div class="container-fluid">

	<!-- DataTales Example -->
	<div class="card shadow mb-4">
		<div class="card-body">
		
		<form id="frmPost" method="post" action="/party/membership">
			<!-- Begin Page Content -->
<div class="form-group">
	<!-- VO, name, Class, id, readonly, HTML태그종류 -->
	<label>아이디</label>
	 <input id="userId" type="text" class="form-control" name="userId"  placeholder="아이디 입력" >
</div>

<div class="form-group">
	<label>비밀번호</label>
	<input id="userPwd" type="password" class="form-control" name="userPwd" placeholder="비밀번호 입력" >
</div>
<div class="form-group">
	<label>이름</label>
	<input id="name" type="text" class="form-control" name="name" placeholder="이름 입력" >
</div>

<div class="form-group">
	<label>생년월일</label>
	<input id="birthDt" type="date" class="form-control" name="birthDt"  >
</div>

<div class="form-group">
	<label>휴대폰 번호</label>
	<input id="PNum" type="tel" class="form-control" name="PNum"  >
</div>

<div class="form-group">
	<label>성별</label>
	<input id="sex1" type="radio" class="form-control" name="sex"  >
</div>


<div class="form-group">
	<label>이메일 주소</label>
	<input id="Uemail" type="email" class="form-control" name="Uemdail"  >
</div>




<!-- id값으로 content를 그대로 사용하는것은 위험 -->






<input type='hidden' name='${_csrf.parameterName}' value='${_csrf.token}'>


			<button id="btnRegisterPost" type="submit" class="btn-primary">회원 등록</button>
		</form>
		
		
		</div>
	</div>

</div>
<!-- /.container-fluid -->



<!-- End of Main Content -->

<%@ include file="../includes/footer.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	var csrfHN = "${_csrf.headerName}";
	var csrfTV = "${_csrf.token}";
	
});
</script>
