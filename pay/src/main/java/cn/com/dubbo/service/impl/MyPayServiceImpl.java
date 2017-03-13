package cn.com.dubbo.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jd.open.api.sdk.domain.jzt_kc.DspAdKCAreaService.ArrayList;
import cn.com.jiuyao.pay.common.constant.ResponseCodeConstants;
import com.jiuyao.ec.common.type.OrderBusinessType;

import cn.com.dubbo.mapper.EcPaymentMapper;
import cn.com.dubbo.mapper.OrderPaymentLogMapper;
import cn.com.dubbo.mapper.OrderPaymentMessageMapper;
import cn.com.dubbo.model.AccountInfo;
import cn.com.dubbo.model.AccountLog;
import cn.com.dubbo.model.CardInfo;
import cn.com.dubbo.model.CardLog;
import cn.com.dubbo.model.EcPaymentType;
import cn.com.dubbo.model.EcPaymentTypeParams;
import cn.com.dubbo.model.OrderPaymentLog;
import cn.com.dubbo.model.OrderPaymentMessageLog;
import cn.com.dubbo.model.OrderPaymentTypeList;
import cn.com.dubbo.service.MyPayService;
import cn.com.dubbo.service.payment.factory.Factory;
import cn.com.dubbo.service.payment.platform.Platform;
import cn.com.jiuyao.pay.common.util.DateUtil;
import cn.com.jiuyao.pay.common.util.StringUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyPayServiceImpl extends BaseServiceImpl implements MyPayService {
	
    @Autowired
    public EcPaymentMapper paymentMapper;
    
    @Autowired
    public OrderPaymentLogMapper orderPayment;

    @Autowired
    public OrderPaymentMessageMapper orderPaymentMessage;
    

    @Override
    public EcPaymentType findPaymentInfoByNo(String paymentTypeNo) {
        return paymentMapper.getPaymentInfoByNo(paymentTypeNo);
    }

    @Override
    public EcPaymentType findPaymentInfoById(String paymentTypeId) {
        return paymentMapper.getPaymentInfoById(paymentTypeId);
    }

    //@J1Service(needCache = false)
    public List<EcPaymentType> getPaymentTypeList(EcPaymentType ecPaymentType) {
        return paymentMapper.getEcPaymentTypeList(ecPaymentType);
    }


    @Override
    public Map<String, String> findPaymentTypeListInfo(Integer paymentTypeId) {
        List<EcPaymentTypeParams> list = paymentMapper.getEcPaymentTypeParamsBytypeId(paymentTypeId);
        Map<String, String> result = new HashMap<String, String>();
        for (EcPaymentTypeParams ecPaymentTypeParams : list) {
            result.put(ecPaymentTypeParams.getParamsName(),
                    ecPaymentTypeParams.getParamsValue());
        }
        return result;
    }

    @Override
    public void saveOrderPaymentLog(OrderPaymentLog orderPaymentLog) {
    	orderPayment.saveOrderPaymentLog(orderPaymentLog);
    }

    @Override
    public void updateOrderPaymentLog(OrderPaymentLog orderPaymentLog) {
    	orderPayment.updateOrderPaymentLog(orderPaymentLog);
    }

    @Override
    public OrderPaymentLog findOrderPaymentLog(OrderPaymentLog orderPaymentLog, boolean beforePayment) {
        if (beforePayment) {
            return orderPayment.findOrderPaymentLog0(orderPaymentLog);

        } else {
            return orderPayment.findOrderPaymentLog(orderPaymentLog);
        }
    }

    @Override
    public OrderPaymentLog getSimilarOrderPaymentLog(OrderPaymentLog orderPaymentLog) {
        return orderPayment.getSimilarOrderPaymentLog(orderPaymentLog);
    }

    @Override
    public Integer getCountOrderpayMessgeLog(String paymentLogId) {
        return orderPaymentMessage.countRequestLog(paymentLogId);
    }

    @Override
    public List<OrderPaymentLog> queryRefundOrder() {
        return orderPayment.queryRefundOrder();
    }

    @Override
    public Double getPaidFeeSumByLog(OrderPaymentLog orderPaymentLog) {
        return orderPayment.getPaidFeeSumByLog(orderPaymentLog);
    }

    @Override
    public List<OrderPaymentLog> getPaidOrderInfo(OrderPaymentLog orderPaymentLog) {
        return orderPayment.getPaidOrderInfo(orderPaymentLog);
    }

    @Override
    public OrderPaymentLog findOrderMaxTime(OrderPaymentLog orderPaymentLog) {
        return orderPayment.getMaxPayTime(orderPaymentLog);
    }

    @Override
    public void saveOrderPaymentMessageLog(OrderPaymentMessageLog orderPaymentMessageLog) {
    	orderPaymentMessage.saveOrderPaymentMessageLog(orderPaymentMessageLog);
    }

    @Override
    public List<String> getBlacklistProducId(Long paymentTypeId) {
    	//业务不需要，暂不实现
        return (List<String>) new ArrayList();
    }

    @Override
    public void saveOrderPaymentTypeList(
            OrderPaymentTypeList orderPaymentTypeList) {
    	orderPaymentMessage.saveOrderPaymentTypeList(orderPaymentTypeList);
    }

    @Override
    public List<OrderPaymentTypeList> getOrderPaymentTypeList(
            OrderPaymentTypeList orderPaymentTypeList) {
        return orderPaymentMessage.getPaymentTypeList(orderPaymentTypeList);

    }

    /**
     * 第三方支付或银行原路返回退款
     *
     * @param orderPaymentLog 退款信息
     * @return map 退款状态详情
     */
    @Override
    public Map doRefund(OrderPaymentLog orderPaymentLog) throws Exception {
        Map resultMap = new HashMap();
        String refundTime = DateUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:ss");//退款时间
        //订单有效性校验，订单是否已支付
        OrderPaymentLog oldOrderPaymentLog = new OrderPaymentLog();
        oldOrderPaymentLog.setBusinessId(Long.parseLong(orderPaymentLog.getOldPaymentNo()));//原订单id是 BussinesId
        oldOrderPaymentLog.setBusinessType(OrderBusinessType.ORDER);
        oldOrderPaymentLog.setPaidState("Y"); //已支付
        oldOrderPaymentLog = this.findOrderPaymentLog(oldOrderPaymentLog, false);
        if (StringUtil.isEmpty(oldOrderPaymentLog.getPaidFee().toString())) {
            resultMap.put("responseCode", ResponseCodeConstants.ORDER_NON_PAYMENT);
            return resultMap;
        }
        //如果原来是alipay 支付的方式，则退款时候选择使用apipayApp  此处需要优化
        if(1 == oldOrderPaymentLog.getPaymentTypeId()){
            oldOrderPaymentLog.setPaymentTypeId(2);
        }

        //校验退款总额是否比订单支付金额小（可以部分退款，多次退款总额需小于原订单支付金额）
        orderPaymentLog.setBusinessType(OrderBusinessType.REFUND);//添加查询条件，原来没有此条件，因为这个是查询的退款金额，需要加上
        Double sumRefund = getPaidFeeSumByLog(orderPaymentLog);//该订单已退款总额
        if (null == sumRefund) {
            sumRefund = 0d;
        }
       // BigDecimal sum = new BigDecimal(sumRefund).add(new BigDecimal(orderPaymentLog.getRefundAmt()));
        BigDecimal sum = new BigDecimal(sumRefund).setScale(4,BigDecimal.ROUND_HALF_UP).add(new BigDecimal(orderPaymentLog.getRefundAmt()).setScale(4,BigDecimal.ROUND_HALF_UP));//原来的写法会出现0.020000000005888
        if (sum.compareTo(oldOrderPaymentLog.getPaidFee()) > 0) {//paidFee总金额
            logger.info("可退款金额不足,orderNO:{" + oldOrderPaymentLog.getBusinessId() + "}");
            resultMap.put("responseCode", ResponseCodeConstants.REFUND_OVERFULFIL);
            return resultMap;
        }
        //添加退款记录
        OrderPaymentLog refundLog = new OrderPaymentLog();
        refundLog.setBusinessType(OrderBusinessType.REFUND);
        refundLog.setBusinessId(oldOrderPaymentLog.getBusinessId());
        refundLog.setPaymentTypeId(oldOrderPaymentLog.getPaymentTypeId());
        refundLog.setPaymentNo(oldOrderPaymentLog.getPaymentNo()); //orderPaymentLog 改成 oldOrderPaymentLog
        refundLog.setPaidFee(oldOrderPaymentLog.getPaidFee());//paidFee为实际支付金额  2017-1-18 fanhongtao
        refundLog.setPaymentFee(new BigDecimal(orderPaymentLog.getRefundAmt()));
        refundLog.setPaymentTime(refundTime);
        refundLog.setIsDelete("N");
        refundLog.setMemberId(oldOrderPaymentLog.getMemberId());
        refundLog.setReqTxnTime(orderPaymentLog.getReqTxnTime());
        refundLog.setQueryState("N");
        refundLog.setPaidState("N");
        refundLog.setBackNo(oldOrderPaymentLog.getBackNo()==null?"":oldOrderPaymentLog.getBackNo());//如果是易汇金需要添加原订单流水号
        synchronized (this) {
            OrderPaymentLog refundLog2 = this.findOrderPaymentLog(refundLog, false);
            if (null != refundLog2) {
                if (null != refundLog2.getPaidState()
                        && "Y".equals(refundLog2.getPaidState())) {
                    logger.error("退款流水号重复，无法退款：" + orderPaymentLog.getOldPaymentNo());
                    resultMap.put("responseCode", ResponseCodeConstants.REFUND_REPEAT);
                    return resultMap;
                } else {
                   /* refundLog.setEditTime(refundTime);
                    this.updateOrderPaymentLog(refundLog);*/
                    refundLog.setAddTime(refundTime); //如果是部分退款应该是添加不应该是修改
                    this.saveOrderPaymentLog(refundLog);
                }
            } else {
                refundLog.setAddTime(refundTime);
                this.saveOrderPaymentLog(refundLog);
            }

        }
        refundLog.setOrderPaymentLog(oldOrderPaymentLog);
        //发起退款请求获得结果
        EcPaymentType ecPaymentType = this.findPaymentInfoById(oldOrderPaymentLog.getPaymentTypeId().toString());
        Map<String,String> mapParams=findPaymentTypeListInfo(ecPaymentType.getPaymentTypeId());
        refundLog.setEcPaymentTypeParames(mapParams);
        refundLog.setEcPaymentType(ecPaymentType);
        Platform platform = Factory.createPlatform(ecPaymentType.getPaymentTypeNo());
        Map map = platform.refund(refundLog);
        if (null != map) {
            resultMap.put("responseCode", map.get("result"));
            resultMap.put("refundAmt", map.get("refundAmt"));
        }
        return resultMap;
    }


    /**
     * 预存账户消费
     */
    @Override
    public Map doAccountConsume(AccountLog accountLog, AccountInfo ai) throws Exception {
    	
    	return null;
    }

    /**
     * 预存账户充值
     */
    @Override
    public String doAccountRecharge(AccountLog accountLog) throws Exception {
    	return null;
    }

    /**
     * 预存账户退款
     * 将金额直接充值到预存账户这个支付方式中，支付方式ID：81，交易流水号：0
     *
     * @param accountLog
     */
    public Map doAccountRefund(AccountLog accountLog) throws Exception {
    	return null;
    }

    /**
     * 订单退款至预存账户
     * 根据业务类型进行交易，订单退款金额不可以超过订单原支付总额，邮费退款不校验退款总额
     *
     * @param accountLog
     */
    public Map doAccountOrderRefund(AccountLog accountLog) throws Exception {
    	return null;
    }

    /**
     * 预存账户提现
     * 管理员系统转账后，提供支付方式，金额，进行提现操作，扣除预存账户金额，提交提现记录
     *
     * @param accountLog
     */
    public Map doAccountWithdraw(AccountLog accountLog) throws Exception {
    	return null;
    }

    /**
     * 预存账户消费查询
     */
    @Override
    public String accountQuery(AccountLog accountLog) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 卡券充值到预存账户
     */
    @Override
    public String doAccountRechargeByCard(CardLog cardLog, CardInfo cardInfo) throws Exception {
    	return null;
    }

    /**
     * 根据卡券订单将所有卡充值到预存账户
     */
    @Override
    public String doAccountRechargeByOrder(CardLog cardLog) throws Exception {
    	return null;
    }
    
    @Override
    public Integer getCountOrderPaymentLog(String orderNo){
    	return orderPayment.getCountOrderPaymentLog(orderNo);
    }

    private Long getNowVersion() {
        return new Date().getTime();
    }

    
    
}
