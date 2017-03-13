<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN" 
	"http://www.springframework.org/dtd/spring-beans.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>健一网-闪白条-订单支付</title>
<%
response.setHeader("Pragma","No-Cache");
response.setHeader("Cache-Control","No-Cache");
response.setDateHeader("Expires", 0);
%>
</head>
<body>
	<div align="center"></br></br></br></br></br></br></br></br><font size="5">正在跳转，请勿关闭或刷新页面</font><img src="http://img.j1.com/images/waitting.gif" /></div>
	<form id="payment" method="post" action="${paygateway}">	
		<input type="hidden" name="charset" value="${charset}" />
		<input type="hidden" name="secretKey" value="${secretKey}" />
		<input type="hidden" name="mercId" value="${mercId}" />
		<input type="hidden" name="interfaceName" value="${interfaceName}" />
		<input type="hidden" name="version" value="${version}"/>
		<input type="hidden" name="signType" value="${signType}" />
		<input type="hidden" name="mercOrderNo" value="${mercOrderNo}" />
		<input type="hidden" name="amount" value="${amount}" />
		<%--<input type="hidden" name="allotParm" value="${charset}" />--%>

		<input type="hidden" name="validTime" value="${validTime}" />
		<input type="hidden" name="description" value="${description}" />

		<input type="hidden" name="userName" value="${userName}" />
		<input type="hidden" name="userAddr" value="${userAddr}" />
		<input type="hidden" name="userMbl" value="${userMbl}" />

		<input type="hidden" name="clientIp" value="${clientIp}" />
		<input type="hidden" name="pageUrl" value="${pageUrl}" />
		<input type="hidden" name="notifyUrl" value="${notifyUrl}" />
		<input type="hidden" name="selfSupport" value="${selfSupport}" />
		<input type="hidden" name="sellerInfo" value="${sellerInfo}" />
		<input type="hidden" name="hmac" value="${hmac}" />
	</form>
</body>
<script language="javascript">
document.getElementById('payment').submit();
</script>
</html>