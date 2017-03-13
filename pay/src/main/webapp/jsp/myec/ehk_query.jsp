<%@page import="cn.com.jiuyao.pay.common.util.Md5Encrypt"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
	String date= format.format(new Date());
	String sig = "businessType=order&channel=app&commitTime="+date+"&memberId=400&orderId=1062329338105856&paymentFee=0.01&paymentTypeNo=ehking&returnUrl=http://118.187.58.130:8086/pay";
	String sign = Md5Encrypt.md5(sig+"4N8x32");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">

	<title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
</head>
<body>

<form name="queryForm" action="${pageContext.request.contextPath}/ehkQuery.html" method='post'>
	<table>
		<tr>
			<td width=200>接口类型:人民币订单查询</td>
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
<script type="text/javascript">
	var _uid = "${sessionScope.memberId}";
	var _oid = "${orderId}";
	var unionKey = "${unionKey}";
	(function() { //(function(){})是一个闭包的用法，闭包必定会被调用。
		var ga = document.createElement('script');
		ga.type = 'text/javascript';
		ga.charset = 'UTF-8';
		ga.async = true;//ga.async = true 异步调用外部js文件，即不阻塞浏览器的解析
		ga.src = 'http://tracker.9yao.wang/js/ana.js?v=1';
		var s = document.getElementsByTagName('script')[0]; //取得第一个tag名为script的元素
		s.parentNode.insertBefore(ga, s); //在s前添加元素ga
	})();
</script>



</body>
</html>
