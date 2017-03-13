package cn.com.dubbo.service.payment.promote;


import java.math.BigDecimal;
import java.util.List;

import cn.com.dubbo.model.OrderPaymentLog;

/**
 * 立减  （具体的策略类，封装了具体的算法或行为，继承于Strategy）
 * 100-20,200-40... 
 * 
 * @author qun.su
 * @version 2014-3-20
 */
public class Reduce implements Strategy {


	@Override
	public BigDecimal getDiscountByLog(OrderPaymentLog orderPaymentLog) {
		return new BigDecimal(0);
	}

	

}
