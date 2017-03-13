<%@ page contentType="text/html; charset=GBK" %>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN" 
	"http://www.springframework.org/dtd/spring-beans.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>健一网-手机支付-订单支付</title>
<%
response.setHeader("Pragma","No-Cache");
response.setHeader("Cache-Control","No-Cache");
response.setDateHeader("Expires", 0);
%>
</head>
<body>
	<div align="center"></br></br></br></br></br></br></br></br><font size="5">正在跳转，请勿关闭或刷新页面</font><img src="http://img.j1.com/images/waitting.gif" /></div>
	<form id="payment" name="mobilePay" action="${submit_url}" method="post">
        </form>     
</body>
<script language="javascript">
document.getElementById('payment').submit();
</script>
</html>