package cn.com.dubbo.service.payment.promote;


import java.math.BigDecimal;

import cn.com.dubbo.model.OrderPaymentLog;

/**
 * 类 <code>Context</code>策略上下文，用ContextInterface维护对策略Strategy的引用
 * 
 * @author suqun
 * @version 2014-3-20
 */
public class Context {
	private Strategy strategy;

	public Context(Strategy strategy) {
		this.strategy = strategy;
	}


	public BigDecimal ContextInterface(OrderPaymentLog orderPaymentLog) {
		return strategy.getDiscountByLog(orderPaymentLog);
	}
}
