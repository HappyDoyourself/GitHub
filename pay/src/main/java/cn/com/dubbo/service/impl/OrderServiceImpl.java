package cn.com.dubbo.service.impl;

import com.jiuyao.ec.common.type.OrderBusinessType;

import cn.com.dubbo.mapper.OrderInfoMapper;
import cn.com.dubbo.mapper.PayTypeMapper;
import cn.com.dubbo.model.OrderPaymentLog;
import cn.com.dubbo.service.OrderService;
import cn.com.jiuyao.pay.common.util.DateUtil;
import cn.com.dubbo.model.OrderInfo;
import cn.com.dubbo.model.OrderItem;
import cn.com.dubbo.model.OrderLog;
import cn.com.dubbo.model.PayOrderItemType;
import cn.com.dubbo.model.PayType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl extends BaseServiceImpl implements OrderService {
    @Autowired
    public OrderInfoMapper orderInfoMapper;

    @Autowired
    public PayTypeMapper payTpyeMapper;

    @Override
    public OrderInfo findOrderById(String orderId) {
        return orderInfoMapper.getOrderById(orderId);
//        if (orderInfo != null) {
//           
//        } else {
//            logger.error("j1soa-order服务获取订单异常，返回码：" + result.getStatus() +
//                    "，订单号：" + orderId +",Message:"+ result.getMessage());
//            return null;
//        }
    }
    
    @Override
	public List<OrderItem> getOrderItemListById(Long orderId) {
		// TODO Auto-generated method stub
		return orderInfoMapper.getItemsByOrderId(orderId.toString());
	}

    /**
	 * 获取订单明细中分类数据汇总
	 * @param orderNo 订单号
	 * @param paymentNo 支付方式编号
	 * @return 每个分类对应的总金额列表
	 */
    @Override
    public List<PayType> getOrderItemGroupType(String orderNo,String paymentNo){
		return payTpyeMapper.getOrderItemGroupType(orderNo, paymentNo);
	};
	
	/**
	 * 获取订单明细分类
	 * @param orderNo 订单号
	 * @param paymentNo 支付方式编号
	 * @return 订单明细对应的分类信息
	 */
	@Override
	public List<PayOrderItemType> getOrderItemDetailType(String orderNo,String paymentNo){
		return payTpyeMapper.getOrderItemDetailType(orderNo, paymentNo);
	};


	@Override
    public void doPaySuccess(OrderInfo orderInfo) {
    	orderInfoMapper.update(orderInfo);
    }

    @Override
    public void saveOrderlog(OrderLog orderLog) {
    	orderInfoMapper.saveOrderLog(orderLog);
    }
    

    @Override
	public List<OrderItem> queryOrderItemByOrderId(Long orderId) {
		// TODO Auto-generated method stub
		return orderInfoMapper.getItemsByOrderId(orderId+"");
	}

	/**
     * 支付方式优惠计算，返回优惠后的待支付金额
     * @param orderPaymentLog 支付流水信息
     * @return
     */
    public BigDecimal promoteByPayment(OrderPaymentLog orderPaymentLog){
        //获取订单支付方式优惠后的待支付金额，如果获取失败则直接返回原待支付金额
        /**
         * 暂不实现
    	try {
            PromotePaymentParam param = new PromotePaymentParam();
            param.setMemberId(orderPaymentLog.getMemberId());
            param.setMul(orderPaymentLog.getChannel());
            param.setOrderId(orderPaymentLog.getBusinessId());
            param.setPaymentTypeId(Long.valueOf(orderPaymentLog.getPaymentTypeId()));
            ServiceMessage<PromotePaymentFee> result = promotePaymentTypeService.
                    getPromotePaymentTypeFee(param);
            if (result.getStatus().equals(MsgStatus.NORMAL) && result.getResult()!=null) {
                return result.getResult().getOrderPayFee();
            } else {
                if(!result.getStatus().equals(MsgStatus.NORMAL) && !result.getStatus().equals(MsgStatus.NO_RESULT)){
                    logger.error("j1soa-order服务获取支付方式优惠后金额出错，返回码：" + result.getStatus() +
                            "，OrderId：" + orderPaymentLog.getBusinessId() +",Message:"+ result.getMessage());
                }
                return orderPaymentLog.getPaymentFee();
            }
        } catch (Exception e) {
            logger.error("===获取订单支付方式优惠后支付金额出错! 订单号： " + orderPaymentLog.getBusinessId());
            e.printStackTrace();
            return orderPaymentLog.getPaymentFee();
        } */
    	 return orderPaymentLog.getPaymentFee();
    }

    /**
     *  保存订单支付优惠
     * @param orderPaymentLog
     */
    public void savePaymentPromote(OrderPaymentLog orderPaymentLog) throws Exception{
    	/**
    	 * 暂不实现
    	 */
//        PromotePaymentParam param = new PromotePaymentParam();
//        param.setMemberId(orderPaymentLog.getMemberId());
//        param.setMul(orderPaymentLog.getChannel());
//        param.setOrderId(orderPaymentLog.getBusinessId());
//        param.setPaymentTypeId(Long.valueOf(orderPaymentLog.getPaymentTypeId()));
//        ServiceMessage<String> result = promotePaymentTypeService.
//                savePaymentPromote(param);
//        if(!result.getStatus().equals(MsgStatus.NORMAL) && !result.getStatus().equals(MsgStatus.NO_RESULT)){
//            logger.error("j1soa-order保存订单支付优惠，返回码：" + result.getStatus() +
//                    "，OrderId：" + orderPaymentLog.getBusinessId() +",Message:"+ result.getMessage());
//            throw new Exception("j1soa-order修改订单支付优惠异常！");
//        }
    }
}
