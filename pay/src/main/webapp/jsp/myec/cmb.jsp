<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>健一网-招商银行-订单支付</title>
</head>
<body>
	<div align="center">
	</br></br></br></br></br></br></br></br>
	<font size="5">正在跳转，请勿关闭或刷新页面</font><img src="http://img.j1.com/images/waitting.gif" />
	</div>
	<form id="payment" method="post" action="${gateway}">
		<input type="hidden" name="BranchID" value="${branchID}">
		<input type="hidden" name="CoNo" value="${coNo}">
		<input type="hidden" name="BillNo" value="${billNo}">
		<input type="hidden" name="Amount" value="${amount}">
		<input type="hidden" name="Date" value="${date}">
		<input type="hidden" name="MerchantUrl" value="${merchantUrl}">
		<input type="hidden" name="MerchantPara" value="${merchantPara}">
		<input type="hidden" name="MerchantCode" value="${merchantCode}">
		<input type="hidden" name="ExpireTimeSpan" value="${ExpireTimeSpan}">
</form>
</body>
<script language="javascript">
	document.getElementById('payment').submit();
</script>
</html>