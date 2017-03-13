<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN" 
	"http://www.springframework.org/dtd/spring-beans.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>健一网-医卡通-订单支付</title>
<%
response.setHeader("Pragma","No-Cache");
response.setHeader("Cache-Control","No-Cache");
response.setDateHeader("Expires", 0);
%>
</head>
<title>健一网-医卡通-订单支付</title>
</head>
<body>
	<div align="center"></br></br></br></br></br></br></br></br><font size="5">正在跳转，请勿关闭或刷新页面</font><img src="http://img.j1.com/images/waitting.gif" /></div>
	<form id="payment" name="ebaolife" action="${postUrl}" method="post">
            <input type="hidden" name="un" value="${un}"/>
            <input type="hidden" name="pw" value="${pw}"/>
            <input type="hidden" name="storeName" value="${storeName}"/>
            <input type="hidden" name="operator" value="${operator}"/>
            <input type="hidden" name="serialNo" value="${serialNo}"/>
            <c:forEach items="${items}" var="items">
             <input type="hidden" name="mname" value="${items.goodsName}"/>
            <input type="hidden" name="mspec" value="件"/>
            <input type="hidden" name="mtype" value="件"/>
            <input type="hidden" name="munit" value="件"/>
            <input type="hidden" name="mprice" value="${items.goodsType}"/>
            <input type="hidden" name="mnum" value="${items.goodsUnit}"/>
            </c:forEach>
            <input type="hidden" name="costAdjust" value="${costAdjust}"/>
            <input type="hidden" name="callbackURL" value="${callbackURL}"/>
            <input type="hidden" name="redirectURL" value="${redirectURL}"/>
            <input type="hidden" name="identifyCode" value="${identifyCode}"/>
            <input type="hidden" name="t" value="${t}"/>
        </form>     
</body>
<script language="javascript">
document.getElementById('payment').submit();
</script>
</html>