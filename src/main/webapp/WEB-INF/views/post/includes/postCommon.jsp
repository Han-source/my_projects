<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<!-- Begin Page Content -->
<div class="form-group">
	<!-- VO, name, Class, id, readonly, HTML태그종류 -->
	<label>아이디</label> <input name="id" class="form-control"
		value="${post.id}" readonly="readonly">
</div>

<div class="form-group">
	<label>제목</label> <input id="title" class="form-control" name="title"
		value="${post.title}" readonly="readonly"></input>
</div>

<!-- id값으로 content를 그대로 사용하는것은 위험 -->
<div class="form-group">
	<label>내용</label>
	<textarea id="txacontent" class="form-control" name="content" rows="3"
		readonly="readonly">${post.content}</textarea>
</div>

2
<div class="form-group">
	<label>이름</label> <input class="form-control"
		value="" readonly="readonly"></input>

</div>

<div class="form-group">
	<label>조회수 </label><input value="${post.readCnt}" readonly="readonly">
	<label>좋아요 </label><input value="${post.likeCnt}" readonly="readonly">
	<label>싫어요 </label><input value="${post.dislikeCnt}"
		readonly="readonly">
</div>

<div class="form-group">
	<label>등록시점 : </label>
	<fmt:formatDate pattern="yyyy-MM-dd" value="${post.registrationDate}" />
	<label>, 수정시점 :</label>
	<fmt:formatDate pattern="yyyy-MM-dd" value="${post.updateDate}" />
</div>

<input type='hidden' name='${_csrf.parameterName}'
	value='${_csrf.token}'>

<script>
	
</script>