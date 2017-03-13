package cn.com.jiuyao.pay.common.constant;

public class CardTypeConstants {
	/** 卡类型 **/
	public final static String VIRTUAL_CARD = "2";
	public final static String ENTITY_CARD = "1";
	
	/** 卡状态 **/
	public final static String UNACTIVATED = "0";//未激活
	public final static String ACTIVATED = "1";//已激活
	public final static String FROZEN = "2";//冻结
	public final static String INVALID= "3";//作废
	public final static String REFUND = "4";//退款
	public final static String OUT_OF_DATE= "5";//过期
	public final static String RUN_OUT = "6";//已用完
}
