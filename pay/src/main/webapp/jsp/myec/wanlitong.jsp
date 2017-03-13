<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN" 
	"http://www.springframework.org/dtd/spring-beans.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>健一网-平安万里通-订单支付</title>
<%
response.setHeader("Pragma","No-Cache");
response.setHeader("Cache-Control","No-Cache");
response.setDateHeader("Expires", 0);
%>
</head>
<body>
	<div align="center"></br></br></br></br></br></br></br></br><font size="5">正在跳转，请勿关闭或刷新页面</font><img src="http://img.j1.com/images/waitting.gif" /></div>
	<form id="payment" method="get" action="${payment}">	
		<input type=hidden name="id" value="${id}"/>	 
	 	<input type=hidden name="reqId" value="${reqId}"/>
	 	<input type=hidden name="merId" value="${merId}"/>
		<input type=hidden name="orderId" value="${orderId}"/> 
		<input type=hidden name="orderAmt" value="${orderAmt}"/>
		<input type=hidden name="sign" value="${sign}"/>
	</form>
</body>
<script language="javascript">
document.getElementById('payment').submit();
</script>
</html>