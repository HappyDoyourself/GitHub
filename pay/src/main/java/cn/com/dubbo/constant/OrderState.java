package cn.com.dubbo.constant;

/**
 * 订单状态
 */
public enum OrderState {
	
	STATE_1(1,"待审核"),
	STATE_2(2,"已审核"),
	STATE_3(3,"审核拒绝"),
	STATE_4(4,"已分仓"),
	STATE_5(5,"待发货"),
	STATE_6(6,"已发货"),
	STATE_7(7,"已取消"),
	STATE_8(8,"已对账"),
	STATE_9(9,"部分发货"),
	STATE_10(10,"退货中"),
	STATE_11(11,"已退款"),
	STATE_12(12,"异常订单"),
	STATE_13(13,"缺货");

	// 成员变量
	private int code;
    private String name;
    
    //构造方法
    private OrderState(int code, String name) {
    	this.code = code;
        this.name = name;
    }
    
    //普通方法
    public static String getName(int code) {
        for (OrderState c : OrderState.values()) {
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