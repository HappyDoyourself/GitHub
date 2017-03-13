package cn.com.dubbo.service.payment.promote;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.com.dubbo.model.OrderPaymentLog;

/**
 * 充值时满赠 （具体的策略类，封装了具体的算法或行为，继承于Strategy）
 * 500+50,1200+100...
 * 返回需要赠送的金额
 * @author qun.su
 * @version 2015-2-4
 */
public class ManZeng implements Strategy {

	@Override
	public BigDecimal getDiscountByLog(OrderPaymentLog orderPaymentLog) {
		BigDecimal orderPayFee = orderPaymentLog.getPaidFee();
		return orderPayFee;
	}


}
