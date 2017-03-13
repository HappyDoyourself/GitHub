
package cn.com.dubbo.service.payment.constant;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * 类 <code>ClassNameTarget</code>
 * 通过key获得对应的value值
 * key包含两个内容：支付方式paymentTypeNO和优惠方案discountPlan
 * value对应为：platform包中类的bean Id和promote包中的类名
 * 
 * @author jinjin
 * @version 2014-3-7
 */
public class ClassNameTarget implements Serializable {
	private static final long serialVersionUID = 5216772885759354717L;
	  
	private static Map<String, String> map; 
	static{
		map = new HashMap<String, String>(); 
		/**支付平台*/
		map.put(Constants.ALIPAY, "alipay");//支付宝
		map.put(Constants.ALIPAYWAP, "alipayWap");//支付宝
		map.put(Constants.ALIPAYAPP, "alipayApp");//支付宝
		map.put(Constants.UNIONPAY, "unionpay"); //银联
		map.put(Constants.WZT, "wzt"); //万证通
		map.put(Constants.PINGAN, "pingAn"); //平安健康卡
		map.put(Constants.YIQIANBAO, "yiQianBao"); //平安壹钱包
		map.put(Constants.CDRUGPAY, "cdrugPay"); //药联卡
		map.put(Constants.EBAOLIFE, "ebaolifePay");//医卡通
		map.put(Constants.SPDB, "spdb");//浦发银行
		map.put(Constants.ABC, "abcPay");//农业银行
		map.put(Constants.ABCCREDIT, "abcCreditPay");//农业银行
		map.put(Constants.BCOM, "bcomPay");//交通银行
		map.put(Constants.CMB, "cmbPay");//招商银行
		map.put(Constants.ECITIC, "eciticPay");//中信银行
		map.put(Constants.ALLINPAY, "allinpay");//通联支付
		map.put(Constants.BILLPAY, "billpay");//快钱支付
		map.put(Constants.TENPAY, "tenpay");//财付通
		map.put(Constants.YIBAOPAY, "yibaopay");//易宝支付
		map.put(Constants.CMPAY, "cmpay");//移动支付 和包
		map.put(Constants.UNIONPAYMOBILE, "unionpayMobile");//银联手机支付
		map.put(Constants.DAYSPAY, "dayspay");//得仕通
		map.put(Constants.TOWNPAY, "townpay");//一城卡
		map.put(Constants.CE9, "ce9");//神州运通
		map.put(Constants.PEIFUBAO, "peifubao");//赔付宝
		map.put(Constants.WEIXIN, "weixin");//微信APP
		map.put(Constants.WXPAY, "wxpay");//微信主站
		map.put(Constants.WEIXINJS, "weixinJs");//微信主站
		map.put(Constants.WANLITONG, "wanlitong");//平安万里通
		map.put(Constants.SHFFT, "shfft");//上海付费通
		map.put(Constants.BALANCE, "balance");//健一账户
		map.put(Constants.SXPAY, "sxpay");//闪信支付
		map.put(Constants.JIANBAOKA, "jianbaoka");//健保卡
		map.put(Constants.PAYECO, "payeco");//易联支付
		map.put(Constants.IPAYFOR, "ipayfor");//益企保
		map.put(Constants.CHC, "chc");//商保通
		map.put(Constants.PUKANGBAO, "pukangbaoPay");//普康宝
		map.put(Constants.YJF, "ejfPay");//易极付
		map.put(Constants.YHJ, "ehking");//易汇金
        map.put(Constants.WEIXINMINI,"weixinMini"); //微信小程序
		/**优惠算法*/
		map.put(Constants.DISCOUNT, "Discount"); //打折
		map.put(Constants.REDUCE, "Reduce"); //立减
		map.put(Constants.POSTAGE_FREE, "PostageFree"); //免邮
		map.put(Constants.POINT_RETURN, "PointReturn"); //返分
		map.put(Constants.MAN_ZENG, "ManZeng"); //满赠
	}

	public static Map<String, String> getTarget() {

		return map;
	}

	

}
