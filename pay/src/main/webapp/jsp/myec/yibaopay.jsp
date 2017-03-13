<%@ page contentType="text/html; charset=GBK" %>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN" 
	"http://www.springframework.org/dtd/spring-beans.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>健一网-易宝支付-订单支付</title>
<%
response.setHeader("Pragma","No-Cache");
response.setHeader("Cache-Control","No-Cache");
response.setDateHeader("Expires", 0);
%>
</head>
<body>
	<div align="center"></br></br></br></br></br></br></br></br><font size="5">正在跳转，请勿关闭或刷新页面</font><img src="http://img.j1.com/images/waitting.gif" /></div>
	<form id="payment" name="yibaoPay" action="https://www.yeepay.com/app-merchant-proxy/node" method="post">
            <input type="hidden" name="p0_Cmd" value="${p0_Cmd}"/>
            <input type="hidden" name="p1_MerId" value="${p1_MerId}"/>
            <input type="hidden" name="p2_Order" value="${p2_Order}"/>
            <input type="hidden" name="p3_Amt" value="${p3_Amt}"/>
            <input type="hidden" name="p4_Cur" value="${p4_Cur}"/>
            <input type="hidden" name="p5_Pid" value="${p5_Pid}"/>
            <input type="hidden" name="p6_Pcat" value="${p6_Pcat}"/>
            <input type="hidden" name="p7_Pdesc" value="${p7_Pdesc}"/>
            <input type="hidden" name="p8_Url" value="${p8_Url}"/>
            <input type="hidden" name="p9_SAF" value="${p9_SAF}"/>
            <input type="hidden" name="pa_MP" value="${pa_MP}"/>
            <input type="hidden" name="pd_FrpId" value="${pd_FrpId}"/>
            <input type="hidden" name="pr_NeedResponse" value="${pr_NeedResponse}"/>
            <input type="hidden" name="hmac" value="${hmac}"/>
        </form>     
</body>
<script language="javascript">
document.getElementById('payment').submit();
</script>
</html>