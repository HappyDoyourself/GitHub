<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN" 
	"http://www.springframework.org/dtd/spring-beans.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>九药网-订单支付-支付完成</title>
<%
response.setHeader("Pragma","No-Cache");
response.setHeader("Cache-Control","No-Cache");
response.setDateHeader("Expires", 0);
%>

</head>
<body>
	<form id="payment" method="post" action="${returnUrl}">
		<%--返回地址：${returnUrl}--%>
	 	<input type=hidden name="payFlag" value="${payFlag}"/> 
	 	<input type=hidden name="paidSuccess" value="${paidSuccess}"/> 
	 	<input type=hidden name="orderId" value="${orderId}"/> 
	 	<input type=hidden name="paymentNo" value="${paymentNo}"/> 
	 	<input type=hidden name="total_fee" value="${total_fee}"/> 
	 	<input type=hidden name="deliveryReceiver" value="${deliveryReceiver}"/> 
	 	<input type=hidden name="deliveryAddressFull" value="${deliveryAddressFull}"/> 
	</form>
</body>
<script language="javascript">
if(document.getElementById("payment").action != ''){
	document.getElementById('payment').submit();
}

</script>
</html>