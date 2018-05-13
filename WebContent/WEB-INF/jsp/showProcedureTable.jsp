<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>貸出/返却依頼表一覧画面</title>
</head>
<body>
	<h3>手順書貸出/返却依頼表一覧画面</h3>

	<table border="1">
		<tr>
			<th>No</th>
			<th>マニュアル種類</th>
			<th>手順書名</th>
			<th>対象ページ</th>
			<th>区分</th>
			<th>貸出申請者</th>
			<th>貸出申請者連絡先</th>
			<th>貸出依頼日</th>
			<th>返却予定日</th>
			<th>リリース予定日</th>
			<th>返却申請者</th>
			<th>返却申請者連絡先</th>
			<th>返却日</th>
			<th>リリース日</th>
		</tr>
		<c:forEach items="${sessionScope.procedureFileBeanList }"
			var="procedure">
			<tr>
				<th><c:out value="${pageScope.procedure.sequenceNumber }" /></th>
				<th><c:out value="${pageScope.procedure.manualCategory }" /></th>
				<th><c:out value="${pageScope.procedure.manualFileName }" /></th>
				<th><c:out value="${pageScope.procedure.targetPage }" /></th>
				<th><c:out value="${pageScope.procedure.operationCategory }" /></th>
				<th><c:out value="${pageScope.procedure.applicantForRental }" /></th>
				<th><c:out value="${pageScope.procedure.applicantForReturn }" /></th>
				<th><c:out value="${pageScope.procedure.telForRental }" /></th>
				<th><c:out value="${pageScope.procedure.telForReturn }" /></th>
				<th><c:out value="${pageScope.procedure.rentalRequestDate }" /></th>
				<th><c:out value="${pageScope.procedure.returnPlanDate }" /></th>
				<th><c:out value="${pageScope.procedure.returnDate }" /></th>
				<th><c:out value="${pageScope.procedure.releasePlunDate }" /></th>
				<th><c:out value="${pageScope.procedure.releaseDate }" /></th>
			</tr>
		</c:forEach>
	</table>



</body>
</html>