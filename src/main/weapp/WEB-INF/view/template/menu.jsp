<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/static/css/link.css"/>"/>
<!-- 부품이 될 페이지기 때문에, HEAD BODY HTML 이런게 필요없다. -->
<style type="text/css">
	#nav > ul {
		padidng: 0px;
		margin: 0px;
	}
	#nav li {
		display: inline-block;
		margin-left: 15px;
	}
	#nav li:FIRST-CHILD {
		margin-left: 0px;
	}
</style>
<div id="nav">
	<!-- 메뉴를 구성할때 많이 쓰는 <ul></ul> -->
	<!-- row만 증가되는 ul을 colum형으로 증가되게 바꿔보자. -->
	<ul style="display: inline-block; margin: 0px;">
		<c:if test="${empty sessionScope.__USER__}">
			<li>
				<a href="<c:url value="/login"/>">Regist/Login</a>
			</li>
		</c:if>
		
		<c:if test="${not empty sessionScope.__USER__}">
			<li>
				(${sessionScope.__USER__.nickname}님) 
				<a href="<c:url value="/logout"/>">Logout</a>
			</li>
			<li>
				<a href="<c:url value="/delete/process1"/>">탈퇴</a>
			</li>
		</c:if>
		<li>Community</li>
	</ul>
</div>