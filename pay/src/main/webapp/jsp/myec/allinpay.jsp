<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN" 
	"http://www.springframework.org/dtd/spring-beans.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>健一网-通联支付-订单支付</title>
<%
response.setHeader("Pragma","No-Cache");
response.setHeader("Cache-Control","No-Cache");
response.setDateHeader("Expires", 0);
%>
</head>
<body>
	<div align="center"></br></br></br></br></br></br></br></br><font size="5">正在跳转，请勿关闭或刷新页面</font><img src="http://img.j1.com/images/waitting.gif" /></div>
	<form id="payment" method="post" action="${gataway}">		 
	 	<input type="hidden" name="inputCharset" value="${inputCharset}"/>
        <input type="hidden" name="pickupUrl" value="${pickupUrl}"/>
        <input type="hidden" name="receiveUrl" value="${receiveUrl}" />
        <input type="hidden" name="version" value="${version}"/>
        <input type="hidden" name="language" value="${language}" />
        <input type="hidden" name="signType" value="${signType}"/>
        <input type="hidden" name="merchantId" value="${merchantId}" />
        <input type="hidden" name="payerName" value="${payerName}"/>
        <input type="hidden" name="payerEmail" value="${payerEmail}" />
        <input type="hidden" name="payerTelephone" value="${payerTelephone}" />
        <input type="hidden" name="payerIDCard" value="${payerIDCard}" />
        <input type="hidden" name="pid" value="${pid}"/>
        <input type="hidden" name="orderNo" value="${orderNo}" />
        <input type="hidden" name="orderAmount" value="${orderAmount}"/>
        <input type="hidden" name="orderCurrency" value="${orderCurrency}" />
        <input type="hidden" name="orderDatetime" value="${orderDatetime}" />
        <input type="hidden" name="orderExpireDatetime" value="${orderExpireDatetime}"/>
        <input type="hidden" name="productName" value="${productName}" />
        <input type="hidden" name="productPrice" value="${productPrice}" />
        <input type="hidden" name="productNum" value="${productNum}"/>
        <input type="hidden" name="productId" value="${productId}" />
        <input type="hidden" name="productDesc" value="${productDesc}" />
        <input type="hidden" name="ext1" value="${ext1}" />
        <input type="hidden" name="ext2" value="${ext2}" />
        <input type="hidden" name="payType" value="${payType}" />
        <input type="hidden" name="issuerId" value="${issuerId}" />
        <input type="hidden" name="pan" value="${pan}" />
        <input type="hidden" name="signMsg" value="${strSignMsg}" />
	</form>
</body>
<script language="javascript">
document.getElementById('payment').submit();
</script>
</html>