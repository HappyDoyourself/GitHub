package cn.com.dubbo.service;

import cn.com.dubbo.model.OrderPaymentLog;
import cn.com.dubbo.model.OrderInfo;
import cn.com.dubbo.model.OrderItem;
import cn.com.dubbo.model.OrderLog;
import cn.com.dubbo.model.PayOrderItemType;
import cn.com.dubbo.model.PayType;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService extends BaseService {
    /**
     * 根据订单号获取订单明细商品详情
     *
     * @param orderId
     * @return
     */
    List<OrderItem> queryOrderItemByOrderId(Long orderId);

    OrderInfo findOrderById(String orderId);

    List<OrderItem> getOrderItemListById(Long orderId);

    void doPaySuccess(OrderInfo updateOrderInfo);

    /**
     * 保存日志
     */
    void saveOrderlog(OrderLog orderLog);
    
    /**
	 * 获取订单明细中分类数据汇总
	 * @param orderNo 订单号
	 * @param paymentNo 支付方式编号
	 * @return 每个分类对应的总金额列表
	 */
	List<PayType> getOrderItemGroupType(String orderNo,String paymentNo);
	
	/**
	 * 获取订单明细分类
	 * @param orderNo 订单号
	 * @param paymentNo 支付方式编号
	 * @return 订单明细对应的分类信息
	 */
	List<PayOrderItemType> getOrderItemDetailType(String orderNo,String paymentNo);
//
//
//    /**
//     * 获取订单明细及对应产品信息
//     */
//    List<OrderItem> getOrderItemAndProductByOrderId(Long orderId);
//
//    /**
//     * 定时更新mysql中的订单支付状态
//     * @return
//     */
//    void updateMysqlOrder();
//
//    /**
//     * 支付方式优惠计算，返回优惠后的待支付金额
//     * @param orderPaymentLog
//     * @return
//     */
//    BigDecimal promoteByPayment(OrderPaymentLog orderPaymentLog);
//
//    /**
//     *  保存订单支付优惠
//     * @param orderPaymentLog
//     */
//    void savePaymentPromote(OrderPaymentLog orderPaymentLog) throws Exception;
}
