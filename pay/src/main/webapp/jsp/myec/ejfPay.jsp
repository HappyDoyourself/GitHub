<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN" 
	"http://www.springframework.org/dtd/spring-beans.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>健一网-易极付-订单支付</title>
<%
response.setHeader("Pragma","No-Cache");
response.setHeader("Cache-Control","No-Cache");
response.setDateHeader("Expires", 0);
%>
</head>
<body>
	<div align="center"></br></br></br></br></br></br></br></br><font size="5">正在跳转，请勿关闭或刷新页面</font><img src="http://img.j1.com/images/waitting.gif" /></div>
	<form id="payment" name="kqPay" action="${paygateway}" method="post">
            <input type="hidden" name="protocol" value="${protocol}"/>
            <input type="hidden" name="orderNo" value="${orderNo}"/>
            <input type="hidden" name="partnerId" value="${partnerId}"/>
            <input type="hidden" name="returnUrl" value="${returnUrl}"/>
            <input type="hidden" name="notifyUrl" value="${notifyUrl}"/>
            <input type="hidden" name="service" value="${service}"/>
            <input type="hidden" name="sign" value="${sign}"/>
            <input type="hidden" name="signType" value="${signType}"/>
            <input type="hidden" name="version" value="${version}"/>
            <textarea rows="50" cols="50" name="prodInfoList" style="display:none">${prodInfoList}</textarea>
     </form>     
</body>
<script language="javascript">
document.getElementById('payment').submit();
</script>
</html>