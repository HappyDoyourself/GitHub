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
<title>九药网-易汇金-订单支付</title>
<%

response.setHeader("Pragma","No-Cache");

response.setHeader("Cache-Control","No-Cache");
response.sendRedirect(request.getAttribute("redirectUrl").toString());
//response.setContentType("application/vnd.ehking-v1.0+json");
response.setDateHeader("Expires", 0);
/*//把请求参数打包成数组
StringBuffer sbHtml = new StringBuffer();
	sbHtml.append("<form id=\"payment\" method=\"post\" action=\""+request.getAttribute("paygateway")+"\">");
	sbHtml.append("<input type=text name=\"merchantId\" value=\""+request.getAttribute("merchantId")+"\"/>");
	sbHtml.append("<input type=text name=\"orderAmount\" value=\""+request.getAttribute("orderAmount")+"\"/>");
	sbHtml.append("<input type=text name=\"orderCurrency\" value=\""+request.getAttribute("orderCurrency")+"\"/>");
	sbHtml.append("<input type=text name=\"requestId\" value=\""+request.getAttribute("requestId")+"\"/>");
	sbHtml.append("<input type=text name=\"notifyUrl\" value=\""+request.getAttribute("notifyUrl")+"\"/> ");
	sbHtml.append("<input type=text name=\"callbackUrl\" value=\""+request.getAttribute("callbackUrl")+"\"/>");
	sbHtml.append("<input type=text name=\"productDetails\" value=\""+request.getAttribute("productDetails")+"\"/>");
	sbHtml.append("<input type=text name=\"hmac\" value=\""+request.getAttribute("hmac")+"\"/>");
	sbHtml.append("</form>");
	sbHtml.append("<script language=\"javascript\">document.getElementById('payment').submit();</script>");
	out.println(sbHtml.toString());*/
%>
</head>
<body>
	<div align="center"></br></br></br></br></br></br></br></br><font size="5">正在跳转到易汇金，请勿关闭或刷新页面</font><img src="http://img.j1.com/images/waitting.gif" /></div>
</body>

</html>
<Script>
</Script>