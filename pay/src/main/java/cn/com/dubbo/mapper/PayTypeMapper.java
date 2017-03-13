package cn.com.dubbo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.dubbo.model.PayOrderItemType;
import cn.com.dubbo.model.PayType;

public interface PayTypeMapper {
	
	/**
	 * 获取订单明细中分类数据汇总
	 * @param orderNo 订单号
	 * @param paymentNo 支付方式编号
	 * @return 每个分类对应的总金额列表
	 */
	List<PayType> getOrderItemGroupType(@Param("orderNo") String orderNo,@Param("paymentNo") String paymentNo);
	
	/**
	 * 获取订单明细分类
	 * @param orderNo 订单号
	 * @param paymentNo 支付方式编号
	 * @return 订单明细对应的分类信息
	 */
	List<PayOrderItemType> getOrderItemDetailType(@Param("orderNo") String orderNo,@Param("paymentNo") String paymentNo);
	
}
