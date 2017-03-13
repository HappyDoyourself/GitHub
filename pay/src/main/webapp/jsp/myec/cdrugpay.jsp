<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN" 
	"http://www.springframework.org/dtd/spring-beans.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>健一网-药联-订单支付</title>
<%
response.setHeader("Pragma","No-Cache");
response.setHeader("Cache-Control","No-Cache");
response.setDateHeader("Expires", 0);
%>
</head>
<body>
	<div align="center"></br></br></br></br></br></br></br></br><font size="5">正在跳转，请勿关闭或刷新页面</font><img src="http://img.j1.com/images/waitting.gif" /></div>
	<form id="payment" name="drugPay" action="${postUrl}" method="post">
            <input type="hidden" name="txnId" value="${txnId}"/>
            <input type="hidden" name="merCode" value="${merCode}"/>
            <input type="hidden" name="billNo" value="${billNo}"/>
            <input type="hidden" name="amount" value="${amount}"/>
            <input type="hidden" name="merDate" value="${merDate}"/>
            <input type="hidden" name="pan" value="${pan}"/>
            <input type="hidden" name="merUrl" value="${merUrl}"/>
            <input type="hidden" name="backStageUrl" value="${backStageUrl}"/>
            <input type="hidden" name="bakMsg" value="${bakMsg}"/>
            <input type="hidden" name="hbInfo" value="${hbInfo}"/>
            <input type="hidden" name="bal_amt" value="${bal_amt}"/>
            <input type="hidden" name="rc" value="${rc}"/>
            <input type="hidden" name="rcDetai" value="${rcDetai}"/>
            <input type="hidden" name="rrn" value="${rrn}"/>
            <input type="hidden" name="signMD5" value="${signMD5}"/>
        </form>     
</body>
<script language="javascript">
document.getElementById('payment').submit();
</script>
</html>