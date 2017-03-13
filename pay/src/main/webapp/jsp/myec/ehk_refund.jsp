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
<title>九药网-易汇金-订单退款</title>
<body>
<form name="refundForm" action='${pageContext.request.contextPath}/refund.html' method='post'>
	<table>
		<tr>
			<td width=200>接口类型:人民币退款</td>
		</tr>
		<%--<tr>
			<td width=200>商家ID</td>
			<td><input type='text' name='merchantId' value='120140257'></td>
		</tr>--%>
		<tr><td><input   type='hidden' name='paymentNo' id="paymentNo" value='ehking'/></td></tr>
		<tr>
			<td width=200>订单号</td>
			<td><input type='text' name='oldPaymentNo' id="oldPaymentNo" value=''></td>
		</tr>
		<tr>
			<td width=200>退款金额</td>
			<td><input type='text' name='refundAmt' id="refundAmt" value='0.01'></td>
		</tr>
		<%--<tr>
			<td width=200>memberId</td>
			<td><input type='text' name='memeberId' id="memeberId" value=''></td>
		</tr>
		<tr>
			<td width=200>金额</td>
			<td><input type='text' name='amount' value=''></td>
		</tr>
		<tr>
			<td width=200>原订单流水号</td>
			<td><input type='text' name='orderId' value=''></td>
		</tr>
		<tr>
			<td width=200>通知地址</td>
			<td><input type='text' name='notifyUrl' value='http://qa.ehking.com/sdk/onlinepay/refundNotify'></td>
		</tr>
		<tr>
			<td width=200>备注</td>
			<td><input type='text' name='remark' value='备注'></td>
		</tr>--%>
		<tr>
			<td width=200><input type='submit' name='' value='提交'></td>
			<td></td>
		</tr>
	</table>
</form>
</body>

</html>
<Script>
</Script>