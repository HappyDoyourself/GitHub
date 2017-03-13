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
<title>九药网-易汇金-显示结果</title>
</head>
<body>
返回结果：<%=request.getAttribute("result")%>
</body>

</html>
