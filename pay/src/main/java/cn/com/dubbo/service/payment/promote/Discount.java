package cn.com.dubbo.service.payment.promote;


import java.math.BigDecimal;

import cn.com.dubbo.model.OrderPaymentLog;

/**
 * 类 <code>Discount</code>打折（具体的策略类，封装了具体的算法或行为，继承于Strategy）
 * 
 * @author qun.su
 * @version 2014-3-20
 */
public class Discount implements Strategy {

	@Override
	public BigDecimal getDiscountByLog(OrderPaymentLog orderPaymentLog) {

		return BigDecimal.ZERO;
	}

}
