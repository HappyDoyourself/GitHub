<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>健一网-平安健康卡-订单支付</title>
</head>
<body>
	<div align="center">
	</br></br></br></br></br></br></br></br>
	<font size="5">正在跳转，请勿关闭或刷新页面</font><img src="http://img.j1.com/images/waitting.gif" />
	</div>
	
	<form id="payment" method="post" action="${postUrl}">
		<input type="hidden" name="char_set" value="${char_set}">
		<input type="hidden" name="page_return_url" value="${page_return_url}">
		<input type="hidden" name="offline_notify_url" value="${offline_notify_url}">
		<input type="hidden" name="biz_type" value="${biz_type}">
		<input type="hidden" name="version_no" value="${version_no}">
		<input type="hidden" name="sign_type" value="${sign_type}">
		<input type="hidden" name="client_ip" value="${client_ip}">
		<input type="hidden" name="order_date" value="${order_date}">
		<input type="hidden" name="bank_abbr" value="${bank_abbr}">
		<input type="hidden" name="card_type" value="${card_type}">
		<input type="hidden" name="partner_id" value="${partner_id}">
		<input type="hidden" name="partner_name" value="${partner_name}">
		<input type="hidden" name="partner_ac_date" value="${partner_ac_date}">
		<input type="hidden" name="request_id" value="${request_id}">
		<input type="hidden" name="order_id" value="${order_id}">
		<input type="hidden" name="total_amount" value="${total_amount}">
		<input type="hidden" name="show_url" value="${show_url}">
		<input type="hidden" name="purchaser_id" value="${purchaser_id}">
		<input type="hidden" name="product_name" value="${product_name}">
		<input type="hidden" name="product_desc" value="${product_desc}">
		<input type="hidden" name="valid_unit" value="${valid_unit}">
		<input type="hidden" name="valid_num" value="${valid_num}">
		<input type="hidden" name="merchant_cert" value="${merchant_cert}">
		<input type="hidden" name="mac" value="${mac}">
</form>
</body>
<script language="javascript">
	document.getElementById('payment').submit();
</script>
</html>