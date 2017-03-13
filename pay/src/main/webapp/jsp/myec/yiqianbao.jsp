<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>九药网-平安健康卡支付</title>
	<%
		response.setHeader("Pragma","No-Cache");
		response.setHeader("Cache-Control","No-Cache");
		response.setDateHeader("Expires", 0);
	%>
</head>
<body>
	<div align="center">
	</br></br></br></br></br></br></br></br>
	<font size="5">正在跳转，请勿关闭或刷新页面</font><img src="http://img.j1.com/images/waitting.gif" />
	</div>
	<!-- -->
	<form id="payment" method="post" action="${postUrl}">
		<input type="hidden" name="version" value="${version}">
		<input type="hidden" name="charset" value="${charset}">
		<input type="hidden" name="signMethod" value="${signMethod}">
		<input type="hidden" name="transType" value="${transType}">
		<input type="hidden" name="transCode" value="${transCode}">
		<input type="hidden" name="merchantId" value="${merchantId}">
		<input type="hidden" name="backEndUrl" value="${backEndUrl}">
		<input type="hidden" name="frontEndUrl" value="${frontEndUrl}">
		<input type="hidden" name="mercOrderNo" value="${mercOrderNo}">
		<input type="hidden" name="orderAmount" value="${orderAmount}">
		<input type="hidden" name="orderCurrency" value="${orderCurrency}">
		<input type="hidden" name="merReserved" value="${merReserved}">
		<input type="hidden" name="merReserved2" value="${merReserved2}">
		<input type="hidden" name="businessScene" value="${businessScene}">
		<input type="hidden" name="signature" value="${signature}">
		
	</form>
</body>
<script language="javascript">
	document.getElementById('payment').submit();
</script> 
</html>