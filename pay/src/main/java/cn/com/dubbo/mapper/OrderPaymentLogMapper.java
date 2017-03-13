package cn.com.dubbo.mapper;

import java.util.List;

import cn.com.dubbo.model.OrderPaymentLog;

/**
 * 订单支付日志
 * @author Administrator
 *
 */
public interface OrderPaymentLogMapper {

	void saveOrderPaymentLog(OrderPaymentLog orderPaymentLog);
	
	void updateOrderPaymentLog(OrderPaymentLog orderPaymentLog);
	
	OrderPaymentLog findOrderPaymentLog0(OrderPaymentLog orderPaymentLog);
	
	OrderPaymentLog findOrderPaymentLog(OrderPaymentLog orderPaymentLog);
	
	OrderPaymentLog getSimilarOrderPaymentLog(OrderPaymentLog orderPaymentLog);
	
	Double getPaidFeeSumByLog(OrderPaymentLog orderPaymentLog);
	
	List<OrderPaymentLog> getPaidOrderInfo(OrderPaymentLog orderPaymentLog);
	
	/**
	 * 获取最新支付日志
	 * @param orderPaymentLog
	 * @return
	 */
	OrderPaymentLog getMaxPayTime(OrderPaymentLog orderPaymentLog);
	
	Integer getCountOrderPaymentLog(String orderNo);

	List<OrderPaymentLog> queryRefundOrder();
}
