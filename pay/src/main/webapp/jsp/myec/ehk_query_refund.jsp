<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN" 
	"http://www.springframework.org/dtd/spring-beans.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
<%--<meta http-equiv="Content-Type" content="application/vnd.ehking-v1.0+json">--%>
<%--	<meta http-equiv="Content-Type" content="text/html; charset=GBK">--%>
<title>九药网-易汇金-订单退款查询</title>

<body>
<form name="refundQueryForm" action='${pageContext.request.contextPath}/ehkQueryRefund.html' method='post'>
	<table>
		<tr>
			<td width=200>接口类型:订单退款查询</td>
		</tr>
		<tr>
			<td width=200>商家ID</td>
			<td><input type='text' name='merchantId' value='120140257'></td>
		</tr>
		<tr>
			<td width=200>订单号</td>
			<td><input type='text' name='requestId' value=''></td>
		</tr>
		<tr>
			<td width=200><input type='submit' name='' value='提交'></td>
			<td></td>
		</tr>
	</table>
</form>
</body>

</html>
