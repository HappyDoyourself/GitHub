<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN" 
	"http://www.springframework.org/dtd/spring-beans.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>健一网-订单支付-支付完成</title>
<%
response.setHeader("Pragma","No-Cache");
response.setHeader("Cache-Control","No-Cache");
response.setDateHeader("Expires", 0);
%>
</head>
<body>
	<form id="payment" name="payment">
	    <table>	        
	        <tr>
	           <td colspan="2">
	                               支付状态：${payFlag}
	           </td>
	        </tr>
	        <tr>
	           <td colspan="2">
	                                支付结果：${paidSuccess}
	           </td>
	        </tr>
	        <tr>
	           <td colspan="2">
	                                    支付金额：${total_fee}
	           </td>
	        </tr>
	    </table>		
	</form>
</body>
</html>

