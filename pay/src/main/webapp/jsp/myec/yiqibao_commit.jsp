<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>健一网-益企保-订单支付</title>
</head>
<body>
	<div align="center">
	</br></br></br></br></br></br></br></br>
	<font size="5">正在跳转，请勿关闭或刷新页面</font><img src="http://img.j1.com/images/waitting.gif" />
	</div>
	
	<form id="payment" method="post" action="${postUrl}">
		<input type="hidden" name="inputCharset" value="${inputCharset}">
		<input type="hidden" name="signType" value="${signType}">
		<input type="hidden" name="productId" value="${productId}">
		<input type="hidden" name="versionId" value="${versionId}">
		<input type="hidden" name="merId" value="${merId}">
		<input type="hidden" name="merOrdDate" value="${merOrdDate}">
		<input type="hidden" name="merOrdId" value="${merOrdId}">
		<input type="hidden" name="ordExprTime" value="${ordExprTime}">
		<input type="hidden" name="ordAmt" value="${ordAmt}">
		<input type="hidden" name="ordDetail" value="${ordDetail}">
		<input type="hidden" name="merFrwdUrl" value="${merFrwdUrl}">
		<input type="hidden" name="merNotfUrl" value="${merNotfUrl}">
		<input type="hidden" name="gateId" value="${gateId}">
		<input type="hidden" name="merDepId" value="${merDepId}">
		<input type="hidden" name="merTermId" value="${merTermId}">
		<input type="hidden" name="addiInfo" value="${addiInfo}">
		<input type="hidden" name="sign" value="${sign}">
</form>
</body>
<script language="javascript">
	document.getElementById('payment').submit();
</script>
</html>