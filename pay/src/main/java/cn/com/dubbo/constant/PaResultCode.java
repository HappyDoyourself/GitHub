package cn.com.dubbo.constant;

/**
 * 平安返回的错误码code
 */
public enum PaResultCode {
	
	EXCEPTION(3,"异常抛出"), 
	QUERY_ORDER_FAIL(8,"查询订单失败"),
	NO_TRACKING_NUMBER(20,"物流单号为空"),
	BIZ_ORDER_ID_NOT_EXIST(30,"订单不存在"),
	QUERY_CARRIER_INFO_FAIL(309,"查询物流公司失败"),
	QUERY_SELLER_INFO_FAIL(311,"查询卖家信息失败"),
	PAGE_SIZE_OUT_OF_MAX_VALUE(325,"pageSize 超出最大值"),
	CARRIER_IS_UNKNOWN(327,"未知物流公司"),
	ORDER_STATUS_NOT_MATCH(18993010,"订单状态不匹配"),
	ORDER_OTHER_1(333,"订单不支持更新物流公司"),
	NO_PERMISSION_TO_OPT(18998002,"没有权限操作");
	
	// 成员变量
	private int code;
    private String name;
    
    //构造方法
    private PaResultCode(int code, String name) {
    	this.code = code;
        this.name = name;
    }
    
    //普通方法
    public static String getName(int code) {
        for (PaResultCode c : PaResultCode.values()) {
            if (c.getCode() == code) {
                return c.name;
            }
        }
        return null;
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
