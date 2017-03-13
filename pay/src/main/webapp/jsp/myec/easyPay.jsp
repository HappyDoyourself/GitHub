<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN" 
	"http://www.springframework.org/dtd/spring-beans.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>健一网-快钱-订单支付</title>
<%
response.setHeader("Pragma","No-Cache");
response.setHeader("Cache-Control","No-Cache");
response.setDateHeader("Expires", 0);
%>
</head>
<body>
	<div align="center"></br></br></br></br></br></br></br></br><font size="5">正在跳转，请勿关闭或刷新页面</font><img src="http://img.j1.com/images/waitting.gif" /></div>
	<form id="payment" name="kqPay" action="${paygateurl}" method="post">
            <input type="text" name="protocol" value="${protocol}"/>
            <input type="text" name="orderNo" value="${orderNo}"/>
            <input type="text" name="partnerId" value="${partnerId}"/>
            <input type="text" name="returnUrl" value="${returnUrl}"/>
            <input type="text" name="notifyUrl" value="${notifyUrl}"/>
            <input type="text" name="service" value="${service}"/>
            <input type="text" name="sign" value="${sign}"/>
            <input type="text" name="signType" value="${signType}"/>
            <input type="text" name="version" value="${version}"/>
            <input type="text" name="prodInfoList" value=${prodInfoList}>
            <input type="text" name="buyerUserId" value="${buyerUserId}"/>  
            <input type="submit" name="提交"/>  
        </form>     
</body>
</html>