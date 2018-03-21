<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/static/css/button.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/static/css/input.css"/>"/>
<script type="text/javascript" src="<c:url value="/static/js/jquery-3.3.1.min.js"/>"></script>
<script type="text/javascript">
	$().ready(function() {
		
		
		/* validate */
		$("#email").keyup(function() {
			var value = $(this).val();
			if ( value != "" ) {
				$(this).removeClass("invalid");
				$(this).addClass("valid");
			}
			else {
				$(this).removeClass("valid");
				$(this).addClass("invalid");				
			}
		});
		$("#nickname").keyup(function() {
			var value = $(this).val();
			if ( value != "" ) {
				$(this).removeClass("invalid");
				$(this).addClass("valid");
			}
			else {
				$(this).removeClass("valid");
				$(this).addClass("invalid");				
			}
		});
		$("#password").keyup(function() {
			var value = $(this).val();
			if ( value != "" ) {
				$(this).removeClass("invalid");
				$(this).addClass("valid");
			}
			else {
				$(this).removeClass("valid");
				$(this).addClass("invalid");				
			}
			
			var password = $("#passwordConfirm").val();
			
			if ( value != password ) {
				$(this).removeClass("valid");
				$(this).addClass("invalid");
				$("#passwordConfirm").removeClass("valid");
				$("#passwordConfirm").addClass("invalid");
			}
			else {
				$(this).removeClass("invalid");
				$(this).addClass("valid");
				$("#passwordConfirm").removeClass("invalid");
				$("#passwordConfirm").addClass("valid");				
			}
			
		});
		$("#passwordConfirm").keyup(function() {
			var value = $(this).val();
			var password = $("#password").val();
			
			if ( value != password ) {
				$(this).removeClass("valid");
				$(this).addClass("invalid");
				$("#password").removeClass("valid");
				$("#password").addClass("invalid");
			}
			else {
				$(this).removeClass("invalid");
				$(this).addClass("valid");
				$("#password").removeClass("invalid");
				$("#password").addClass("valid");				
			}
		});
		
		/* 클릭 이벤트 */
		$("#registBtn").click(function() {
			
			if ($("#email").val() == "") {
				alert("이메일을 입력해라.");
				$("#email").focus();
				/* 클래스명을 셰도어식으로 준다. */
				$("#email").addClass("invalid");
				return false;
			}
			
			if ($("#nickname").val() == "") {
				alert("닉네임을 입력해라.");
				$("#nickname").focus();
				/* 클래스명을 셰도어식으로 준다. */
				$("#nickname").addClass("invalid");
				return false;
			}
			
			if ($("#password").val() == "") {
				alert("비밀번호를 입력해라.");
				$("#password").focus();
				/* 클래스명을 셰도어식으로 준다. */
				$("#password").addClass("invalid");
				return false;
			}
			
			$("#registForm").attr({
								"method":	"post",
								"action":	"<c:url value="/regist"/>"
							})
							.submit();
			
		});
	});

</script>


</head>
<body>

	<div id="wrapper">
		<jsp:include page="/WEB-INF/view/template/menu.jsp"/>
		<form:form modelAttribute="registForm">
			
			<div>
				<!-- TODO Email 중복 검사하기 (ajax) -->
				<input type="email" id="email" name="email" placeholder="Email" value="${registForm.email}"/>
			</div>
			<div>
				<%-- TODO Nickname 중복검사 (ajax) --%>
				<input	type="text"	id="nickname" name="nickname" placeholder="Nickname" value="${registForm.nickname}"/>			
				<div>
					<form:errors></form:errors>
				</div>
			</div>
			<div>
				<input	type="password" id="password" name="password" placeholder="Password" value="${registForm.password}"/>			
			</div>
			<div>
				<input	type="password" id="passwordConfirm" placeholder="Password-Confirm"/>			
			</div>
			<div	style="text-align: center;">
				<div	id="registBtn" class="button">회원가입</div>
			</div>
			
		</form:form>
	
	</div>

</body>
</html>