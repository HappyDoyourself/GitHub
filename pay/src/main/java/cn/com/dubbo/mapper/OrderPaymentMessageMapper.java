package cn.com.dubbo.mapper;

import java.util.List;

import cn.com.dubbo.model.OrderPaymentMessageLog;
import cn.com.dubbo.model.OrderPaymentTypeList;


public interface OrderPaymentMessageMapper {

	void saveOrderPaymentMessageLog(OrderPaymentMessageLog orderPaymentMessageLog);
	
	void saveOrderPaymentTypeList(OrderPaymentTypeList orderPaymentTypeList);
	
	List<OrderPaymentTypeList> getPaymentTypeList(OrderPaymentTypeList orderPaymentTypeList);

	Integer countRequestLog(String paymentLogId);
}
