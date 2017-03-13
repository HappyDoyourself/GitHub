<%@ page contentType="text/html; charset=gb2312" %>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN" 
    "http://www.springframework.org/dtd/spring-beans.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>健一网-健一卡-订单支付</title>
<%
response.setHeader("Pragma","No-Cache");
response.setHeader("Cache-Control","No-Cache");
response.setDateHeader("Expires", 0);
%>
</head>
<body>
    <div align="center"></br></br></br></br></br></br></br></br><font size="5">正在跳转，请勿关闭或刷新页面</font><img src="http://img.j1.com/images/waitting.gif" /></div>
    <form id="jyCardForm" name="jyCardForm" action="${postUrl}" method="post">
		<input type="hidden" name="MerCode" id="MerCode" value="${merCode}">
		<input type="hidden" name="BillNo" id="BillNo" value="${billNo}">
		<input type="hidden" name="Amount" id="Amount" value="${amount}">
		<input type="hidden" name="MerDate" id="MerDate" value="${merDate}">
		<input type="hidden" name="PayType" id="PayType" value="${payType}">
		<input type="hidden" name="MerUrl" id="MerUrl" value="${merUrl}">
		<input type="hidden" name="backStageUrl" id="backStageUrl" value="${backStageUrl}">
		<input type="hidden" name="SignMD5" id="SignMD5" value="${signMD5}">
		<input type="hidden" name="bakMsg" id="bakMsg" value="${bakMsg}">
	</form>
</body>
<script language="javascript">
	document.getElementById('jyCardForm').submit();
</script>
</html>