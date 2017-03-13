<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>健一网-中信银行-订单支付</title>
</head>
<body>
	<div align="center">
	</br></br></br></br></br></br></br></br>
	<font size="5">正在跳转，请勿关闭或刷新页面</font><img src="http://img.j1.com/images/waitting.gif" />
	</div>
	<form id="pay_form" name="pay_form" action="${postUrl}"  method="post">
     <input type="hidden" id="SIGNREQMSG" name="SIGNREQMSG" value="${signedMessage}" />
  </form>
</body>
<script language="javascript">
	document.getElementById('pay_form').submit();
</script>
</html>