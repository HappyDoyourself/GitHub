<%@ page language="java" contentType="text/html; charset=GBK"
		 pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<title>健一网-普康保-订单支付</title>
</head>
<body>
<div align="center">
	</br></br></br></br></br></br></br></br>
	<font size="5">正在跳转，请勿关闭或刷新页面</font><img src="http://img.j1.com/images/waitting.gif" />
</div>

<form id="payment" method="post" action="${postUrl}">
	<input type="hidden" name="txnId" value="${txnId}">
	<input type="hidden" name="merCode" value="${merCode}">
	<input type="hidden" name="billNo" value="${billNo}">
	<input type="hidden" name="amount" value="${amount}">
	<input type="hidden" name="merDate" value="${merDate}">
	<input type="hidden" name="merUrl" value="${merUrl}">
	<input type="hidden" name="backStageUrl" value="${backStageUrl}">
	<input type="hidden" name="rc" value="${rc}">
	<input type="hidden" name="signMD5" value="${signMD5}">
	<input type="hidden" name="hbInfo" value="${hbInfo}">
	<input type="hidden" name="bakMsg" value="${bakMsg}">
</form>
</body>
<script language="javascript">
	document.getElementById('payment').submit();
</script>
</html>