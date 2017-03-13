<%@ page contentType="text/html; charset=gb2312" %>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN" 
    "http://www.springframework.org/dtd/spring-beans.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>健一网-交通银行-订单支付</title>
<%
response.setHeader("Pragma","No-Cache");
response.setHeader("Cache-Control","No-Cache");
response.setDateHeader("Expires", 0);
%>
</head>
<body>
    <div align="center"></br></br></br></br></br></br></br></br><font size="5">正在跳转，请勿关闭或刷新页面</font><img src="http://img.j1.com/images/waitting.gif" /></div>
    <form id="payment" name="kqPay" action="${paygateurl}" method="post">
<input  type="hidden" name="interfaceVersion" value="${interfaceVersion}">
    <input  type="hidden" name="merID"  value="${merID}">
    <input  type="hidden" name="orderid" value="${orderid}">
    <input  type="hidden" name="orderDate" value="${orderDate}">
    <input  type="hidden" name="orderTime" value="${orderTime}">
    <input  type="hidden" name="tranType" value="${tranType}" >
    <input  type="hidden" name="amount" value="${amount}">
    <input  type="hidden" name="curType" value="${curType}">
    <input  type="hidden" name="orderContent" value="${orderContent}">
    <input  type="hidden" name="orderMono" value="${orderMono}">
    <input  type="hidden" name="phdFlag" value="${phdFlag}" >
    <input  type="hidden" name="notifyType" value="${notifyType}">
    <input  type="hidden" name="merURL" value="${merURL}">
    <input  type="hidden" name="goodsURL" value="${goodsURL}">
    <input  type="hidden" name="jumpSeconds" value="${jumpSeconds}">
    <input  type="hidden" name="payBatchNo" value="${payBatchNo}">
    <input  type="hidden" name="proxyMerName" value="${proxyMerName}">
    <input  type="hidden" name="proxyMerType" value="${proxyMerType}">
    <input  type="hidden" name="proxyMerCredentials" value="${proxyMerCredentials}">
    <input  type="hidden" name="netType" value="${netType}" >
    <input  type="hidden" name="merSignMsg" value="${signMsg}">
     <input type ="hidden" name = "issBankNo" value="${issBankNo}">
        </form>     
</body>
<script language="javascript">
document.getElementById('payment').submit();
</script>
</html>