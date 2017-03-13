<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN" 
	"http://www.springframework.org/dtd/spring-beans.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>九药网-支付宝-订单支付</title>
<%
response.setHeader("Pragma","No-Cache");
response.setHeader("Cache-Control","No-Cache");
response.setDateHeader("Expires", 0);
//把请求参数打包成数组
StringBuffer sbHtml = new StringBuffer();
	sbHtml.append("<form id=\"payment\" method=\"get\" action=\""+request.getAttribute("paygateway")+"\">");
	sbHtml.append("<input type=hidden name=\"_input_charset\" value=\""+request.getAttribute("input_charset")+"\"/>");
	sbHtml.append("<input type=hidden name=\"body\" value=\""+request.getAttribute("body")+"\"/>");	
	sbHtml.append("<input type=hidden name=\"notify_url\" value=\""+request.getAttribute("notify_url")+"\"/>");	
	sbHtml.append("<input type=hidden name=\"payment_type\" value=\""+request.getAttribute("payment_type")+"\"/> ");	
	sbHtml.append("<input type=hidden name=\"seller_email\" value=\""+request.getAttribute("seller_email")+"\"/>");	
	sbHtml.append("<input type=hidden name=\"subject\" value=\""+request.getAttribute("subject")+"\"/>");	
	sbHtml.append("<input type=hidden name=\"paymethod\" value=\""+request.getAttribute("paymethod")+"\"/>");	
	sbHtml.append("<input type=hidden name=\"defaultbank\" value=\""+request.getAttribute("defaultbank")+"\"/>");	
	sbHtml.append("<input type=hidden name=\"show_url\" value=\""+request.getAttribute("show_url")+"\"/>");	
	sbHtml.append("<input type=hidden name=\"out_trade_no\" value=\""+request.getAttribute("out_trade_no")+"\"/>");
	sbHtml.append("<input type=hidden name=\"partner\" value=\""+request.getAttribute("partner")+"\"/>");
	sbHtml.append("<input type=hidden name=\"service\" value=\""+request.getAttribute("service")+"\"/>");
	sbHtml.append("<input type=hidden name=\"sign_type\" value=\""+request.getAttribute("sign_type")+"\"/>");
	sbHtml.append("<input type=hidden name=\"total_fee\" value=\""+request.getAttribute("total_fee")+"\"/>");
	sbHtml.append("<input type=hidden name=\"return_url\" value=\""+request.getAttribute("return_url")+"\"/>");
	sbHtml.append("<input type=hidden name=\"it_b_pay\" value=\""+request.getAttribute("it_b_pay")+"\"/>");
	sbHtml.append("<input type=hidden name=\"sign\" value=\""+request.getAttribute("sign")+"\"/>");
	sbHtml.append("</form>");
	sbHtml.append("<script language=\"javascript\">document.getElementById('payment').submit();</script>");
	out.println(sbHtml.toString());
%>
</head>
<body>
	<div align="center"></br></br></br></br></br></br></br></br><font size="5">正在跳转到支付宝，请勿关闭或刷新页面</font><img src="http://img.j1.com/images/waitting.gif" /></div>
</body>

</html>