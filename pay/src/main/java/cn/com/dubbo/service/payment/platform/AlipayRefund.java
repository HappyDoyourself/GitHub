package cn.com.dubbo.service.payment.platform;

import cn.com.dubbo.model.EcPaymentType;
import cn.com.dubbo.model.OrderPaymentLog;
import cn.com.dubbo.model.PaymentResult;
import cn.com.jiuyao.pay.common.constant.ResponseCodeConstants;
import cn.com.jiuyao.pay.common.util.DateUtils;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fanhongtao
 * Date 2017-02-21 14:30
 */
public class AlipayRefund extends PlatformPayment{
    public Map refund(OrderPaymentLog orderPaymentLog)
            throws Exception {
        Map<String, Object> orderInfoMap = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject();
        try {
            Map<String, String> parameter = myPayService
                    .findPaymentTypeListInfo(orderPaymentLog.getPaymentTypeId());
            EcPaymentType ecPaymentType = myPayService.
                    findPaymentInfoById(orderPaymentLog.getPaymentTypeId().toString());
            orderPaymentLog.setEcPaymentTypeParames(parameter);
            orderPaymentLog.setEcPaymentType(ecPaymentType);
            PaymentResult paymentResult = (PaymentResult)refundByOrder(orderPaymentLog);
            //退款已申请状态
            if(ResponseCodeConstants.REFUND_APPLIED_STATE.equals(paymentResult.getRefundStatus())){
                String currentTime = DateUtils.longToDateAll(System
                        .currentTimeMillis());
                orderPaymentLog.setBackTime(currentTime);// 返回时间
                orderPaymentLog.setEditTime(currentTime);// 修改时间
                orderPaymentLog.setBackState(paymentResult.getRefundStatus());
                orderPaymentLog.setBackNo(paymentResult.getTradeNo());
                orderPaymentLog.setPaidFee(new BigDecimal(paymentResult.getRefundFee()));
                doSaveResultByLog(orderPaymentLog);
                jsonObject.put("code","0000");
                orderInfoMap.put("result", jsonObject.toString());
                orderInfoMap.put("refundAmt",paymentResult.getRefundFee());
            } else {
                jsonObject.put("code","0004");
                orderInfoMap.put("result", jsonObject.toString());
             /*   orderInfoMap.put("result", paymentResult.getRefundStatus());*/
                orderInfoMap.put("refundAmt","0");
            }
            return orderInfoMap;
        } catch (Exception e) {
            logger.error("=== An exception occurred while refunds,and the businessId is " + orderPaymentLog.getBusinessId());
            e.printStackTrace();
            orderInfoMap.put("result","fail");
            orderInfoMap.put("refundAmt","0");
        }
        jsonObject.put("code","0099");
        orderInfoMap.put("result",jsonObject.toString());
        return orderInfoMap;
    }


    /**
     * 订单退款请求
     * @param orderPaymentLog
     * @return PaymentResult
     */
    @Override
    public Object refundByOrder(OrderPaymentLog orderPaymentLog) throws Exception{

        return null;
    }
}
