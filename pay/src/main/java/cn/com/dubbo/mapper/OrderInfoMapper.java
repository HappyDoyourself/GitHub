package cn.com.dubbo.mapper;

import java.util.List;
import java.util.Map;


import cn.com.dubbo.model.OrderInfo;
import cn.com.dubbo.model.OrderItem;
import cn.com.dubbo.model.OrderLog;

public interface OrderInfoMapper {

	int countValidateOrder(Map<String, Long> params);
	
	OrderInfo getOrderById(String orderId);
	
	void update(OrderInfo orderInfo);
	
	void saveOrderLog(OrderLog orderLog);
	
	List<OrderItem> getItemsByOrderId(String orderNo);
	
	List<OrderItem> getItemsByPay(Map map);
}
