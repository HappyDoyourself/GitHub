package cn.com.dubbo.service.payment.promote;


import java.math.BigDecimal;

import cn.com.dubbo.model.OrderPaymentLog;

/**
 * 类 <code>Strategy</code>策略类，定义所有支持算法的公共接口
 * 
 * @author suqun
 * @version 2014-3-20
 */
public interface  Strategy {
	public BigDecimal getDiscountByLog(OrderPaymentLog orderpaymentLog);
}
