<!DOCTYPE c:set PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8,chrome=1">
    <link rel="shortcut icon" href="${ctx}/favicon.ico" type="image/x-icon" />
    <title>九药网微信支付</title>
	<script type="text/javascript" src="https://static.9drug.com/pc/assets/js/jquery-1.10.1.min.js" ></script>
<style>
    /* 初始化CSS */
    body, h1, h2, h3, h4, h5, h6, hr, p, blockquote, dl, dt, dd, ul, ol, li, pre, form, fieldset, legend, button, input, textarea, th, td, img {margin:0;padding:0;}
    body, button, input, select, textarea {font:12px/1.5  arial, Verdana,"宋体",'\5b8b\4f53', sans-serif; color:#666666;}
    address, caption, cite, code, dfn, th, var { font-style:normal; font-weight:normal;}
    h1, h2, h3, h4, h5, h6 {font-size:100%;}
    ol, ul, li, dl, dd, dt {list-style:none;}
    table {border-collapse:collapse;border-spacing:0;}
    table,tr,td{ border:none;}
    fieldset, img，input{border:0;}
    input,label,img,th{vertical-align:middle;}
    button, input, select, textarea {font-size:100%;}
    img{vertical-align:middle}
    input { padding-top:0; padding-left:0; padding-bottom:0}
    a:focus,input{outline:none}
    a{text-decoration: none;color:#666666;}
    a:hover { text-decoration:none; }
    .l{ float: left;}
    .r{ float: right;}
    .c{ clear: both;}
    .clearfix:after { content:"."; display:block; height:0; visibility:hidden; clear:both; }
    .clearfix { zoom:1; }/*ie67*/
    .clearit { clear:both; height:0; font-size:0; overflow:hidden; }/*加div*/
    img{border: none;}
    /*购物车头部-陈爽*/
    .shopping{height: 107px;  background: url("https://img01.9drug.com/images/pay/weixin/shop_02.jpg") repeat-x;}
    .shop_top,.ucenter_head_top{width: 1000px; margin: 0 auto; color: #666;/*min-width: 1200px;*/}
    .shop_logo1{ height: 100px;margin-top: 30px;}
    .shop_logo1,.shop_logo2{ display: block; float: left;}
    /*微信支付*/
    .wechat_info{width: 1200px;height: 100px;margin: 0 auto;font:18px/100px "Microsoft YaHei",SimSun,'\5b8b\4f53',sans-serif;}
    .wechat_info .right{float: right;}
    .wechat_pay{width: 1200px;height: 620px;margin: 0 auto; background-color: #ffffff;margin-bottom: 100px;}
    .wechat_pay .left{width: 558px;height: 490px;border: 7px solid #b1b0b5;float: left;margin: 60px 50px 0 110px;}
    .wechat_pay .top{height: 50px;width: 538px;padding-left:20px;line-height: 50px;font-size: 20px;color: #ffffff;background-color:#394a69; }
    .wechat_pay .cancle{float: right;width: 20px;height: 20px; background: url("https://img01.9drug.com/images/pay/weixin/wechat_pay_cancle.png") no-repeat;margin: 17px 15px;cursor: pointer;}
    .wechat_pay .middle{width: 200px;height: 270px;margin: 0 auto;text-align: center;margin-top: 70px;position: relative;}
    .wechat_pay .text{height: 40px;line-height: 40px;width: 202px; float: left;font-size: 30px;color: #394a69;font-weight: 500;}
    .wechat_pay .qr_code{width: 200px;height: 200px;border: 1px solid #b2b2b2;float: left;}
    .wechat_pay .qr_code img{margin: 10px;width: 180px;height: 180px;}
    .wechat_pay .warning,.wechat_pay .warning_2{float: left; height: 30px;line-height: 30px;width: 202px; font-size: 18px;color: #394a69;}
    .wechat_pay .warning span,.wechat_info .red{color: #f00000;}
    .wechat_pay .warning_2 span{color: #f00000;font-size: 12px;}
    .wechat_pay .buttom{width: 500px;margin: 0 auto;margin-top: 40px; height: 60px;font: 14px/60px "Microsoft YaHei",SimSun,'\5b8b\4f53',sans-serif;color: #666666;text-align: center;border-top: 1px solid #b2b2b2;}
    .wechat_pay .buttom a:hover{color: #1d6ea4;}
    .wechat_pay .right{float: left;margin: 30px 0 0 25px;}
</style>
</head>
<body style="background-color: #ecedf2;">
<!--这里存放头部位置 shop_header.html-->
<div class="shopping" >
    <div class="shop_top" style="width: 1200px;">
        <div class="shop_top_l close_top_l">
            <a class="shop_logo1" target="_blank"><img src="https://img01.9drug.com/images/pay/weixin/logo_shop.png"></a>
            <a class="shop_logo2" target="_blank"><img src="https://img01.9drug.com/images/pay/weixin/wechat.png" ></a>
        </div>
    </div>
</div>

<!--微信支付界面-->
<div class="wechat_info">
    请您及时付款，以便订单尽快处理！订单号：${orderId} <span class="right">应付金额：<span class="red">${paymentFee}</span>&nbsp;元</span>

</div>
<div class="wechat_pay clearfix">
    <div class="left">
        <div class="top">
            支付&nbsp;<span>${paymentFee}</span>&nbsp;元
            
        </div>
        <div class="middle">
            <div class="text">微信扫码支付</div>
            <div class="qr_code">
                <img src="${ctx}/image/getWxImage.html?content=${content}" alt=""/>
            </div>
            
            <!--二维码过期提示语-->
            <!--<div class="warning_2"> <span>二维码已过期，请重新获取二维码</span></div>-->
            <!--二维码过期-->
        </div>
        <div class="buttom">
            <a href="https://www.9drug.com/shop/sucAndPayment.html?orderNo=${orderId}">使用其他支付方式支付&nbsp;></a>
        </div>
    </div>
    <div class="right">
        <img src="https://img01.9drug.com/images/pay/weixin/wechat_pay.png" alt=""/>
    </div>
</div>
<!--这里存放尾部 public_foot.html-->
</body>
<script type="text/javascript">
	window.setInterval(poll, 5000);
	function poll() {
	$.ajax({
			url : '${ctx}/wxpay/poll.html?paymentNo=${paymentNo}',
			type : "post",
			success : function(data) {
				if (data == "ERR") {
				}
				if (data == "SUCCESS") {
					window.location.href = "${returnUrl}?paymentNo=${paymentNo}&total_fee=${paymentFee}&payFlag=success";
				}
			},
			error : function(responseText, error) {
				showError("系统错误，请稍候再试！");
			}
		});
	}
</script>

</html>