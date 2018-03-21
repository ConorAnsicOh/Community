<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<c:url value="/static/css/common.css"/>"/>
<title>Insert title here</title>
<style type="text/css">
	.locationTitle{
		cursor: pointer;
	}
</style>
<script type="text/javascript" src="<c:url value="/static/js/jquery-3.3.1.min.js"/>"></script>
<script type="text/javascript">
	
	$().ready(function() {
		
		$("#toggle").change(function() {
			
			/* 체크 하나로 모두 해결 할 수 있음. */
			var checked = $(this).prop("checked");
			var checkbox = $("input[type=checkbox][name=delete]");			 
				checkbox.prop("checked", checked);
			
			/* 선택 반전 
			checkbox.each(function(index, checkbox) {
				var checked= $(checkbox).prop("checked");HTML 쿼리를 주게되므로 $을 이용해서 다시 지정해야한다. 
				$(checkbox).prop("checked", !checked);
			});
		*/
			
		});
		$("input[type=checkbox][name=delete]").change(function() {
			var checkedLength = $("input[type=checkbox][name=delete]:checked").length;

			var checkboxLength = $("input[type=checkbox][name=delete]").length;
			
			if (checkedLength == checkboxLength ) {
				$("#toggle").prop("checked", true);
			}
			else{
				$("#toggle").prop("checked", false);				
			}
		});
		
		$("#massDeleteBtn").click(function() {
			$("#massDeleteForm").attr({
				"method" : "post",
				"action" : "<c:url value="/mypage/communities/delete"/>"
			}).submit();
		});

	 	$(".locationTitle").click(function() {
			var urrl = $(this).data("id");
      		window.open("<c:url value="/view/"/>" + urrl,"새창", "width=500, height=500, scrollbars=yes, resizable=no");
      	});  
		
		
	});
	
</script>
</head>
<body>

	<div	id="popUpwrapper">
		<h1 id="title">나의 추억들</h1>
		<div>
			<table	class="grid" style="width:100%">
				<colgroup>
					<col style="width: 5%"/>
					<col style="width: 90"/>
					<col style="width: 5%"/>				
				</colgroup>
				<thead>
					<tr>
						<th>ID</th>
						<th>TITLE</th>
						<th>
							<input type="checkbox" id="toggle"/>
						</th>
					</tr>
				</thead>
				<tbody>
						<form id="massDeleteForm">
						<c:forEach items="${myCommunities}" var="community">
							<tr>
								<td>${community.id }</td>
								
								<td class="locationTitle" data-id="${community.id}">${community.title }</td>
								<td>
									<input type="checkbox"	name="delete"	value="${community.id}"/>
								</td>						
							</tr>
						</c:forEach>
					</form>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="3">
							<input type="button" id="massDeleteBtn" value="일괄삭제"/>
						</td>
					</tr>
				</tfoot>
				
			</table>
		</div>
	</div>
	
</body>
</html>