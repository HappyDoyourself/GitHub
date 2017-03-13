package cn.com.jiuyao.pay.common.constant;

public class ResponseCodeConstants
{
	/**
	 * 未明原因的失败
	 */
	public final static String FAIL = "fail";
	
	/**
	 * 成功
	 */
	public final static String SUCCESS = "success";
	
	/**
	 * 参数格式有误
	 */
	public final static String DATAFORMAT_FAIL = "dataformat_fail";
	
	/**
	 * 签名无效
	 */
	public final static String SIGN_FAIL = "sign_fail";
	
	/**
	 * 未查询到卡
	 */
	public final static String NO_FOUND_CARD = "no_found_card_fail";
	
	/**
	 * 卡片信息错误
	 */
	public final static String CARD_INFO_INCORRECT = "card_info_incorrect_fail";
	
	/**
	 * 卡未激活
	 */
	public final static String CARD_INACTIVE = "card_inactive_fail";

	/**
	 * 卡已冻结
	 */
	public final static String CARD_FROZEN = "card_frozen_fail";

	/**
	 * 卡已作废
	 */
	public final static String CARD_INVALID = "card_invalid_fail";


	/**
	 * 卡已退款
	 */
	public final static String CARD_REFUND = "card_refund_fail";


	/**
	 * 卡已过有效期
	 */
	public final static String CARD_EXPIRED = "card_expired_fail";

	/**
	 * 卡已用完
	 */
	public final static String CARD_FINISHED = "card_finished_fail";


	/**
	 * 交易密码错误
	 */
	public final static String PASSWORD_INCORRECT = "password_incorrect_fail";
	
	/**
	 * 账户余额不足
	 */
	public final static String BALANCE_INSUFFICIENT = "balance_insufficient_fail";
	
	/**
	 * 充值过程中失败
	 */
	public final static String CONSUME_FAIL = "consume_fail";
	
	/**
	 * 存在重复卡片
	 */
	public final static String DUPLICATE_CARD_FAIL = "duplicate_card_fail";
	
	/**
	 * 卡余额与卡面额不等(虚拟卡)
	 */
	public final static String BALANCE_NOEQL_FEE_FAIL = "balance_noeql_fee_fail";
	
	/**
	 * 卡余额与卡面额不等(虚拟卡)
	 */
	public final static String CHANGE_NOEQL_FEE_FAIL = "change_noeql_fee_fail";
	
	/**
	 * 购买会员与冲入会员不符(虚拟卡)
	 */
	public final static String PURCHASER_NOEQL_CONSUMER_FAIL = "purchaser_noeql_consumer_fail";
	
	/**
	 * 订单状态有误
	 */
	public final static String ORDER_STATE_FAIL = "order_state_fail";
	
	/**
	 * 获取用户id失败
	 */
	public final static String MEMBERID_FAIL = "memberId_fail";

	/**
	 * 订单已支付
	 */
	public final static String ORDER_IS_PAID = "ORDER_IS_PAID_fail";

	/**
	 * 退款金额大于原订单金额
	 */
	public final static String REFUND_OVERFULFIL = "REFUND_OVERFULFIL_fail";
	/**
	 * 提现金额大于账户余额
	 */
	public final static String WITHDRAW_OVERFULFIL = "WITHDRAW_OVERFULFIL_fail";
	/**
	 * 提现流水号重复
	 */
	public final static String WITHDRAW_REPEAT= "WITHDRAW_REPEAT_fail";

	/**
	 * 订单用户与当前支付用户不匹配
	 */
	public final static String ORDER_MEMBER_UNMATCH = "ORDER_MEMBER_UNMATCH_fail";
	/**
	 * 订单已取消
	 */
	public final static String ORDER_IS_CANCELED = "ORDER_IS_CANCELED_fail";

	/**
	 * 订单未支付
	 */
	public final static String ORDER_NON_PAYMENT = "ORDER_NON_PAYMENT";

	/**
	 * 退款流水号重复
	 */
	public final static String REFUND_REPEAT = "REFUND_REPEAT_fail";

	//////////////////////////////////////////////////////////////////
	/** 退款已申请状态 */
	public static final String REFUND_APPLIED_STATE = "REFUND_APPLIED";
}
