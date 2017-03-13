<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN" 
	"http://www.springframework.org/dtd/spring-beans.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>健一网-付费通-订单支付</title>
<%
response.setHeader("Pragma","No-Cache");
response.setHeader("Cache-Control","No-Cache");
response.setDateHeader("Expires", 0);
%>
</head>
<body>
	<div align="center"></br></br></br></br></br></br></br></br><font size="5">正在跳转，请勿关闭或刷新页面</font><img src="http://img.j1.com/images/waitting.gif" /></div>
	<form id="payment" method="post" action="${gateway}">
		<input type=hidden name="version" value="${version}"/>
	 	<input type=hidden name="transCode" value="${transCode}"/>
	 	<input type=hidden name="merchantId" value="${merchantId}"/>
		<input type=hidden name="merOrderNum" value="${merOrderNum}"/>
		<input type=hidden name="intBussId" value="${intBussId}"/>
		<input type=hidden name="tranAmt" value="${tranAmt}"/>
		<input type=hidden name="sysTraceNum" value="${sysTraceNum}"/>
		<input type=hidden name="tranDateTime" value="${tranDateTime}"/>
		<input type=hidden name="currencyType" value="${currencyType}"/>
									 	
		<input type=hidden name="merURL" value="${merURL}"/>
		<input type=hidden name="backURL" value="${backURL}"/>
		<input type=hidden name="orderInfo" value="${orderInfo}"/>
		<input type=hidden name="userId" value="${userId}"/>
		<input type=hidden name="signValue" value="${signValue}"/>
	</form>
</body>
<script language="javascript">
document.getElementById('payment').submit();
</script>
</html>