package cn.com.dubbo.service.payment.constant;

/**
 * 
* 类 <code>Constant</code>常量类，支付方式和优惠方式
*
* @author suqun
* @version 2014-3-10
 */
public class Constants {
	/** 支付宝 **/
	public static final String ALIPAY = "alipay";//值为ec_payment_type表中的paymentTypeNo
	/** 手机网页支付宝 **/
	public static final String ALIPAYWAP = "alipayWap";
	/** 手机APP支付宝 **/
	public static final String ALIPAYAPP = "alipayApp";
	/** 银联电子 **/
	public static final String UNIONPAY = "unionpay";
	/** 万证通 **/
	public static final String WZT = "wzt";
	/** 平安健康卡 **/
	public static final String PINGAN = "pingan";
	/** 平安壹钱包 **/
	public static final String YIQIANBAO = "1qianbao";
	/** 药联卡 **/
	public static final String CDRUGPAY = "cdrugpay";
	/** 医卡通 **/
	public static final String EBAOLIFE = "ebaolife";
	/** 浦发银行 **/
	public static final String SPDB = "spdb";
	/** 农业银行 **/
	public static final String ABC = "abc";
	/** 农业银行信用卡支付 **/
	public static final String ABCCREDIT = "abcCredit";
	/** 交通银行 **/
	public static final String BCOM = "bcom";
	/** 招商银行 **/
	public static final String CMB = "cmb";
	/** 中信银行 **/
	public static final String ECITIC = "ecitic";
	/** 通联支付 **/
	public static final String ALLINPAY = "allinpay";
	/** 快钱支付 **/
	public static final String BILLPAY = "99bill";
	/** 财付通 **/
	public static final String TENPAY = "tenpay";
	/** 易宝支付 **/
	public static final String YIBAOPAY = "yibaoPay";
	/** 移动支付 和包 **/
	public static final String CMPAY = "cmpay";
	/** 银联手机支付 **/
	public static final String UNIONPAYMOBILE = "unionpayMobile";
	/** 预付卡 得仕通 **/
	public static final String DAYSPAY = "dayspay";
	/** 预付卡 一城卡 **/
	public static final String TOWNPAY = "townpay";
	/** 预付卡 神州运通 **/
	public static final String CE9 = "ce9";
	/** 预付卡 健一卡 **/
	public static final String MEMBERCARD = "membercard";
	/** 赔付宝 **/
	public static final String PEIFUBAO = "peifubao";
	/** APP微信支付 **/
	public static final String WEIXIN = "weixin";
	/** Wap微信支付 **/
	public static final String WEIXINWAP = "weixinWap";
	/** 主站微信支付 **/
	public static final String WXPAY = "wxpay";
	/** 微信公众号支付 **/
	public static final String WEIXINJS = "weixinJs";
	/** 平安万里通 **/
	public static final String WANLITONG = "wanlitong";
	/** 上海付费通 **/
	public static final String SHFFT = "shfft";
	/** 健一账户余额 **/
	public static final String BALANCE = "balance";
	/** 闪信支付 **/
	public static final String SXPAY = "sxpay";
	/** 健保卡 **/
	public static final String JIANBAOKA = "jianbaoka";
	/** 易联支付 **/
	public static final String PAYECO = "payeco";
	/** 益企保 **/
	public static final String IPAYFOR = "ipayfor";
	/** 商保通 **/
	public static final String CHC = "chc";
 
	/**	普康保 */
	public static final String PUKANGBAO = "pukangbao";
 
	/** 易极付 **/
	public static final String YJF = "YJF";
	
	/** 易汇金 **/
	public static final String YHJ = "ehking";

	/** 微信小程序 **/
	public static final String WEIXINMINI = "weixinMini";
	
 
	///////////////////////////////////////////////////////////////////////////////////
	/** 打折 **/
	public static final String DISCOUNT = "DZ";
	/** 立减 **/
	public static final String REDUCE = "LJ";
	/** 免邮 **/
	public static final String POSTAGE_FREE = "MY";
	/** 返分 **/
	public static final String POINT_RETURN = "FF";
	/** 满赠 **/
	public static final String MAN_ZENG = "MZ";
	
	///////////////////////////////////////////////////////////////////////////////////
	/** 订单号|orderId|n1...16 */
	public static final String ORDERID_REGEX = "^[0-9]{1,20}$";
	/** 支付方式编码|paymentTypeNo|an1...40 */
	public static final String PAYMENTTYPENO_REGEX = "^[A-Za-z0-9]{1,40}$";
	/** 会员号|memberId|n1...10 */
	public static final String MEMBERID_REGEX = "^[0-9]{1,10}$";
	/** 提交时间|commitTime|YYYYMMDDhhmmss */
	public static final String COMMITTIME_REGEX = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229))([0-1][0-9]|2[0-3])([0-5][0-9])([0-5][0-9])$";
	/** 业务类型(order,weg,mobile,member_card)|businessType|an1...20 */
	public static final String BUSINESSTYPE_REGEX = "^[A-Za-z0-9]{1,20}$";
	/** 应付金额 |paymentFee|number(8,2) */
	public static final String PAYMENTFEE_REGEX = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$";
	/** 支付方式ID|paymentTypeId|n1...16 */
	public static final String PAYMENTTYPEID_REGEX = "^[0-9]{1,16}$";
	/** 支付流水|paymentNo|n1...32 */
	public static final String PAYMENTNO_REGEX = "^[0-9]{1,32}$";
	//////////////////////////////////////////////////////////
	/** 卡号|cardNo|n16 */
	public static final String CARDNO_REGEX = "^[0-9]{16}$";
	/** 卡密|cardPass|n8 */
	public static final String CARDPASS_REGEX = "^[0-9]{8}$";
	/** 特殊字符 */
	public static final String CHARACTERS_REGEX = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";


	///////////////////////////////////////////////////////////////////////////////////


}
