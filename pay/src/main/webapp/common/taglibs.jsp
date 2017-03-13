<%@ page trimDirectiveWhitespaces="true" %> 
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
response.setHeader("Pragma","No-Cache");
response.setHeader("Cache-Control","No-Cache");
response.setDateHeader("Expires", 0);
%>
<%@include file="/jsp/common/common_ctx.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-cn" lang="zh-cn"> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<meta http-equiv="Content-Script-Type" content="text/javascript" /> 
<meta http-equiv="Content-Style-Type" content="text/css" />
<jsp:include page="/jsp/seo/seo${param.seoPageSufix }.jsp" >
	<jsp:param value="${param.seoTitle }" name="seoTitle"/>
	<jsp:param value="${param.seoKeyword }" name="seoKeyword"/>
	<jsp:param value="${param.seoDesc }" name="seoDesc"/>
</jsp:include>
<meta property="qc:admins" content="16502756576216375" />
<link rel="icon" href="${ctx }/favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="${ctxcj}/css/asyncbox/asyncbox.css"/>
<link type="text/css" rel="stylesheet" href="${ctxcj}/css/collection.css?20140903" />
<script type="text/javascript" src="${ctxcj}/js/lib/jquery/jquery-1.5.2.min.js?v=${codeVersion}" ></script>
<script type="text/javascript" src="${ctxcj}/js/lib/jquery/jquery.cookie.js?v=${codeVersion}" ></script>
<script type="text/javascript" src="${ctxcj }/js/common/common.js?v=${codeVersion}" ></script>
<script type="text/javascript">
function getAppPath(){
	return "${ctx}" ;
}
var appPath =  getAppPath();
</script>
