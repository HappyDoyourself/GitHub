<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN"
"http://www.springframework.org/dtd/spring-beans.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>九药网微信支付</title>
	<%
		response.setHeader("Pragma","No-Cache");
		response.setHeader("Cache-Control","No-Cache");
		response.setDateHeader("Expires", 0);
	%>

</head>
<body>
<form id="payment" method="post" action="${urlPrefix.j1Web}/wxpay.html">
	<input type=hidden name="content" value="${content}"/>
	<input type=hidden name="paymentNo" value="${paymentNo}"/>
	<input type=hidden name="returnUrl" value="${returnUrl}"/>
	<input type=hidden name="paymentFee" value="${paymentFee}"/>
	<input type=hidden name="orderId" value="${orderId}"/>
</body>
<script language="javascript">
	//document.getElementById('payment').submit();
</script>
</html>