<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>��һ��-�»�һ�ǿ�-����֧��</title>
</head>
<body>
	<div align="center">
	</br></br></br></br></br></br></br></br>
	<font size="5">������ת������رջ�ˢ��ҳ��</font><img src="http://img.j1.com/images/waitting.gif" />
	</div>
	<form id="payment" action="${postUrl}" method="post">
		<input name="merchantNo" type="hidden" value="${merchantNo}"/>
		<input name="orderId" type="hidden" value="${orderId}"/>
		<input name="serialNo" type="hidden" value="${serialNo}"/>
		<input name="amount" type="hidden" value="${amount}"/>
		<input name="callBackUrl" type="hidden" value="${callBackUrl}"/>
		<input name="isFull" type="hidden" value="${isFull}"/>
		<input name="terminalNo" type="hidden" value="${terminalNo}"/>
		<input name="merchantDecodedData" type="hidden" value="${merchantDecodedData}"/>
	</form>
</body>
<script language="javascript">
	document.getElementById('payment').submit();
</script>
</html>