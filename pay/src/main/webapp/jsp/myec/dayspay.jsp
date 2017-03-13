<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>健一网-得仕通-订单支付</title>
</head>
<body>
	<div align="center">
	</br></br></br></br></br></br></br></br>
	<font size="5">正在跳转，请勿关闭或刷新页面</font><img src="http://img.j1.com/images/waitting.gif" />
	</div>
  <form method='post' name='SendOrderForm' action='<%=request.getAttribute("host_pay_url")%>' id="SendOrderForm">
	<input type='hidden' name='Version' value='<%=request.getAttribute("version")%>'> 
	<input type='hidden' name='TermUrl' value='<%=request.getAttribute("termUrl")%>'>
	<input type='hidden' name='MPIReq' value='<%=request.getAttribute("mpiReq")%>'> 
	</form>

</body>
<script language="javascript">
	document.getElementById('SendOrderForm').submit();
</script>
</html>