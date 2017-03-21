<%@page import="cn.com.jiuyao.pay.common.util.Md5Encrypt"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");   
String date= format.format(new Date());
	String sig = "businessType=order&channel=pc&commitTime="+date+"&memberId=203&orderId=1171315755438080&paymentFee=0.01&paymentModeCode=BANK_CARD-B2C-CMBCHINA-P2P&paymentTypeNo=ehking&returnUrl=http://118.187.58.130:8086/pay";
	//String sig = "businessType=order&channel=pc&commitTime="+date+"&memberId=369&orderId=1171315755438080&paymentFee=0.01&paymentTypeNo=wxpay&returnUrl=http://118.187.58.130:8086/pay";
	//String sig = "businessType=order&channel=pc&commitTime="+date+"&memberId=62&orderId=1157391436861440&paymentFee=0.01&paymentTypeNo=weixin&returnUrl=http://118.187.58.130:8086/pay&trade_type=JSAPI";
	//String sig = "businessType=order&channel=pc&commitTime="+date+"&memberId=259&orderId=1021756757233664&paymentFee=0.01&paymentTypeNo=wxpay&returnUrl=http://118.187.58.130:8086/pay";
	 // String sig ="businessType=order&channel=ios&commitTime=20170210160210&memberId=482&orderId=1122083245330432&paymentFee=536.00&paymentModeCode=BANK_CARD-B2C-CMBCHINA-P2P&paymentTypeNo=ehking";
		// String sig = "businessType=order&channel=pc&commitTime="+date+"&memberId=168&orderId=1021763592507392&paymentFee=0.01&paymentTypeNo=alipay&returnUrl=http://118.187.58.130:8086/pay";
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
     <form action="${pageContext.request.contextPath}/pay.html" method="post">
     	<table>
     		<tr>
     			<td>orderId</td>
     			<td><input type="text" value="1171315755438080" name="orderId"/></br>
     				<input type="text" value="ehking" name="paymentTypeNo"/></br>
     				<input type="text" value="order" name="businessType"/></br>
     				<input type="text" value="pc" name="channel"/></br>
     				<input type="text" value="http://118.187.58.130:8086/pay" name="returnUrl"/></br>
     				<input type="text" value="<%=date%>" name="commitTime">
			<%--		<input type="text" value="JSAPI" name="trade_type">--%>
				<%--	<input type="text" value="oN9750NoaidaUuTQHo9nlN0OJ-iY" name="openid">--%>
					<input type="text" value="BANK_CARD-B2C-CMBCHINA-P2P" name="paymentModeCode"/></br>
					<%--//ehk参数S
                    <input type="text" value="iphone" name="name"/></br>
                        <input type="text" value="1" name="quantity"/></br>
                        <input type="text" value="100" name="amount"/></br>
                        <input type="text" value="BANK_CARD-B2C-CMBCHINA-P2P" name="paymentModeCode"/></br>--%>

     			</td>
     		</tr>
     		<tr>
     			<td>memberId</td>
     			<td><input type="text" value="203" name="memberId"/></td>
     		</tr>
     		<tr>
     			<td>paymentFee</td>
     			<td><input type="text" value="0.01" name="paymentFee"/></td>
     		</tr>
     		<tr>
     			<td>sign</td>
     			<td><input type="text" value="<%=sign%>" name="sign"/></td>
     		</tr>
     	</table>
     	<input type="submit" value="付款">
     </form>
     <%--<script type="text/javascript">
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
	</script>--%>

	 <p><a href="jsp/myec/ehk_query.jsp">查询</a></p>
	 <p><a href="jsp/myec/ehk_refund.jsp">退款</a></p>
	 <p><a href="jsp/myec/ehk_query_refund.jsp">退款查询</a></p>
	
  </body>
</html>
