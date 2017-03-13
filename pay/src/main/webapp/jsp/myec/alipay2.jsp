<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN" 
	"http://www.springframework.org/dtd/spring-beans.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>九药网-支付宝-订单支付</title>
<%
response.setHeader("Pragma","No-Cache");
response.setHeader("Cache-Control","No-Cache");
response.setDateHeader("Expires", 0);

%>
</head>
<body>
	<form id="payment" method="post" action="${paygateway}">	
		<input type=hidden name="_input_charset" value="${input_charset}"/>	 
	 	<input type=hidden name="body" value="${body}"/>
	 	<input type=hidden name="notify_url" value="${notify_url}"/>
		<input type=hidden name="payment_type" value="${payment_type}"/> 
		<input type=hidden name="seller_email" value="${seller_email}"/>
		<input type=hidden name="subject" value="${subject}"/>
		<input type=hidden name="paymethod" value="${paymethod}"/>
		<input type=hidden name="defaultbank" value="${defaultbank}"/>
		<input type=hidden name="show_url" value="${show_url}"/>
									 	
		<input type=hidden name="out_trade_no" value="${out_trade_no}"/>
		<input type=hidden name="partner" value="${partner}"/>
		<input type=hidden name="service" value="${service}"/>
		<input type=hidden name="sign_type" value="${sign_type}"/>
		<input type=hidden name="total_fee" value="${total_fee}"/>
		<input type=hidden name="return_url" value="${return_url}"/>
		<input type=hidden name="it_b_pay" value="${it_b_pay}"/>
		<input type=hidden name="sign" value="${sign}"/>
	</form>
	<script language="javascript">
		document.getElementById('payment').submit();
	</script>
	<div align="center"></br></br></br></br></br></br></br></br><font size="5">正在跳转，请勿关闭或刷新页面</font><img src="http://img.j1.com/images/waitting.gif" /></div>
</body>

</html>