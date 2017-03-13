package cn.com.dubbo.service;

import cn.com.dubbo.service.BaseService;
import cn.com.dubbo.model.AccountInfo;
import cn.com.dubbo.model.AccountLog;
import cn.com.dubbo.model.CardInfo;
import cn.com.dubbo.model.CardLog;
import cn.com.dubbo.model.EcPaymentType;
import cn.com.dubbo.model.OrderInfo;
import cn.com.dubbo.model.OrderPaymentLog;
import cn.com.dubbo.model.OrderPaymentMessageLog;
import cn.com.dubbo.model.OrderPaymentTypeList;

import java.util.List;
import java.util.Map;

public interface MyPayService extends BaseService {

    public EcPaymentType findPaymentInfoByNo(String paymentTypeNo);

    public EcPaymentType findPaymentInfoById(String paymentTypeNo);

    public List<EcPaymentType> getPaymentTypeList(EcPaymentType ecPaymentType);

//    public List<EcPromoteRule> getOrderPromoteRuleList(OrderInfo orderInfo);
//
//    /**
//     * 根据支付方式的id查找对应的优惠规则
//     *
//     * @param rule
//     * @return
//     */
//    public List<EcPromoteRule> getOrderPromoteRuleByDTO(EcPromoteRule rule);

    public Map<String, String> findPaymentTypeListInfo(Integer paymentTypeId);

    /**
     * 保存支付日志
     *
     * @param orderPaymentLog
     */
    public void saveOrderPaymentLog(OrderPaymentLog orderPaymentLog);

    /**
     * 更新支付日志
     *
     * @param orderPaymentLog
     */
    public void updateOrderPaymentLog(OrderPaymentLog orderPaymentLog);

    /**
     * 根据支付的流水号获得支付日志
     *
     * @param orderPaymentLog
     */
    public OrderPaymentLog findOrderPaymentLog(OrderPaymentLog orderPaymentLog,
                                               boolean beforePayment);


    /**
     * 获取订单已退款总额
     *
     * @param orderPaymentLog
     * @return
     */
    public Double getPaidFeeSumByLog(OrderPaymentLog orderPaymentLog);

    /**
     * 查找支付最近一条信息
     *
     * @return
     */
    public OrderPaymentLog findOrderMaxTime(OrderPaymentLog orderPaymentLog);


    /**
     * 保存订单支付信息日志
     */
    void saveOrderPaymentMessageLog(
            OrderPaymentMessageLog orderPaymentMessageLog);

    /**
     * 获取支付产品黑名单表中的productId
     */
    public List<String> getBlacklistProducId(Long paymentTypeId);

    /**
     * 保存订单支付方式列表
     */
    public void saveOrderPaymentTypeList(
            OrderPaymentTypeList orderPaymentTypeList);

    /**
     * 查询订单支付方式列表
     */
    public List<OrderPaymentTypeList> getOrderPaymentTypeList(
            OrderPaymentTypeList orderPaymentTypeList);

    /**
     * 第三方支付或银行原来返回退款
     *
     * @param orderPaymentLog 退款信息
     * @return map 退款状态详情
     */
    public Map doRefund(OrderPaymentLog orderPaymentLog) throws Exception;

    /**
     * 预存账户消费
     *
     * @param al
     * @param ai
     */
    public Map doAccountConsume(AccountLog al, AccountInfo ai) throws Exception;

    /**
     * 预存账户退款
     *
     * @param accountLog
     */
    public Map doAccountRefund(AccountLog accountLog) throws Exception;

    /**
     * 预存账户提现
     *
     * @param accountLog
     */
    public Map doAccountWithdraw(AccountLog accountLog) throws Exception;

    /**
     * 订单退款至预存账户
     *
     * @param accountLog
     */
    public Map doAccountOrderRefund(AccountLog accountLog) throws Exception;

    /**
     * 预存账户充值
     *
     * @param accountLog
     */
    public String doAccountRecharge(AccountLog accountLog) throws Exception;

    /**
     * 根据卡向预存账户卡充值
     *
     * @param cardLog
     * @param cardInfo
     */
    public String doAccountRechargeByCard(CardLog cardLog, CardInfo cardInfo) throws Exception;

    /**
     * 根据订单向预存账户充值
     *
     * @param cardLog
     * @return
     */
    public String doAccountRechargeByOrder(CardLog cardLog) throws Exception;

    /**
     * 预存账户消费日志查询
     *
     * @param accountLog
     */
    public String accountQuery(AccountLog accountLog);

    /**
     * 根据条件获取已支付订单总金额等信息
     *
     * @param orderPaymentLog
     * @return
     */
    public List<OrderPaymentLog> getPaidOrderInfo(OrderPaymentLog orderPaymentLog);

    /**
     * 根据条件获取订单支付日志条数
     *
     * @param
     * @return
     */
    public Integer getCountOrderPaymentLog(String orderNo);
    
    public OrderPaymentLog getSimilarOrderPaymentLog(OrderPaymentLog orderPaymentLog);


    /**
     * 根据日志表主键统计总请求次数
     * @param paymentLogId
     * @return
     */
    public Integer getCountOrderpayMessgeLog(String paymentLogId);


    public List<OrderPaymentLog> queryRefundOrder();
}
