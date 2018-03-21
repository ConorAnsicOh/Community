<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%><!--spring validation check 스프링  %@디렉티브-->
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- static 폴더명 아님 이후부터 폴더명 -->
<script type="text/javascript"
	src="<c:url value="/static/js/jquery-3.3.1.min.js"/>">
	
</script>
<script type="text/javascript">
	$().ready(function() {
		/* 수정하기로 왔고, 파일네임이 들어있다면 아래를 수행해라. */
		<c:if test="${mode == 'modify' && not empty displayFilename}">
			$("#file").closest("div").hide();
		</c:if>
		
		$("#displayFilename").change(function() {
			var isChecked = $(this).prop("checked");
			if ( isChecked ) {
				$("#file").closest("div").show();
				$("label[for=displayFilename]").css({
					"text-decoration-line" : "line-through",
					"text-decoration-style" : "double",
					"text-decoration-color" : "#FF0000"
				});
			}
			else {
				$("label[for=displayFilename]").css({
					"text-decoration" : "none"
				});
				$("#file").closest("div").hide();
			}
		
		});

		$("#writeBtn").click(function() {

			var mode = "${mode}";
			if (mode == "modify") {
				var url = "<c:url value="/modify/${communityVO.id}"/>";
			} else {
				var url = "<c:url value="/write"/>";
			}

			var writeForm = $("#writeFrom"); //모든 폼 객체 들어감
			writeForm.attr({
				"method" : "post",
				"action" : url
			//클릭했을때 포스트 방식으로 write 주소로 전송
			});
			writeForm.submit();
		});

	});
</script>

<style>
#body {
	width: 500px;
	height: 300px;
}

form {
	display: inline-block;
}
</style>


</head>
<body>
	<div>
		<jsp:include page="/WEB-INF/view/template/menu.jsp" />
		<form:form modelAttribute="writeFrom" enctype="multipart/form-data">
			<p>
				제목 : <input type="text" id="title" name="title"
					placeholder="제목을 입력하시오" value="${communityVO.title }" />
			</p>
			<div>
				<form:errors path="title" />
				<!--체크하고자하는 엘리먼트의 name을 적어준다.  -->
			</div>

			<p>
				내용 :
				<textarea id="body" name="body" placeholder="내용을 입력하세요"">${communityVO.body}</textarea>
			</p>
			<div>
				<form:errors path="body" />
				<!--체크하고자하는 엘리먼트의 name을 적어준다.  -->
			</div>
			<c:if
				test="${mode == 'modify' && not empty communityVO.displayFilename}">
				<div>
					<input type="checkbox" id="displayFilename" name="displayFilename"
						value="${communityVO.displayFilename}" />
					<!-- for는 영향을 주고 싶은 id를 가져다 적어준다. -->
					<label for="displayFilename">
						${communityVO.displayFilename} </label>
				</div>

			</c:if>


			<p>
				<input type="hidden" id="userId" name="userId"
					value="${sessionScope.__USER__.id} " placeholder="ID" />
			</p>

			<div>
				<form:errors path="writeDate" />
				<!--체크하고자하는 엘리먼트의 name을 적어준다.  -->
			</div>

			<div>
				<input type="file" id="file" name="file" />
			</div>

			<p>
				<input type="button" id="writeBtn" value="등록" />
			</p>
		</form:form>

	</div>

	<a href="<c:url value="/logout"/>">로그아웃</a>

</body>
</html>