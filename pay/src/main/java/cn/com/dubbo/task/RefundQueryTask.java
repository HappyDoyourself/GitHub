package cn.com.dubbo.task;

import cn.com.dubbo.constant.PinganConfig;
import cn.com.dubbo.model.OrderPaymentLog;
import cn.com.dubbo.service.MyPayService;
import cn.com.dubbo.service.payment.constant.Constants;
import cn.com.dubbo.service.payment.factory.Factory;
import cn.com.dubbo.service.payment.platform.Platform;
import cn.com.jiuyao.pay.common.util.DateUtil;
import cn.com.jiuyao.pay.common.util.HttpClientTools;
import cn.com.jiuyao.util.payments.alipay.config.AlipayConfig;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fanhongtao
 * Date 2017-03-04 12:34
 */
@Component
public class RefundQueryTask {

    public static Integer[] ALIPAY = {1, 2, 3};  //支付方式是支付宝通过二维码支付的
    public static Integer[] WEIXIN = {4, 6, 7,8};  //支付方式是微信通过二维码支付的
    public static Integer[] YINLIAN = {10}; //银联 健康卡
    public static Integer[] PINGAN = {200}; //健康卡
    public static String  REFUNDSTATE = "1";
    private Logger logger = LoggerFactory.getLogger(RefundQueryTask.class);

    @Autowired
    protected MyPayService myPayService;
    @Resource(name = "pinganConfig")
    private PinganConfig pinganConfig;

   /*@Scheduled(cron = "* *//*2 * * * ?")*/
   @Scheduled(cron = "0 0 */2 * * ?")
    public void refundQueryTask() {
        System.out.println("=====================退款查询任务开始执行====================");
        List<OrderPaymentLog> orderPaymentLogList = myPayService.queryRefundOrder();
        for (OrderPaymentLog log : orderPaymentLogList) {
            Integer paymentTypeId = log.getPaymentTypeId();
            if (ArrayUtils.contains(ALIPAY, paymentTypeId)) {
                log.setChannel(Constants.ALIPAYAPP);
                updateQueryState(log);
            }  else if(ArrayUtils.contains(WEIXIN, paymentTypeId)){
                log.setChannel(Constants.WXPAY);
                updateQueryState(log);
            }  else if (ArrayUtils.contains(YINLIAN, paymentTypeId)) {
                //3天后执行
                if (DateUtil.StrToDate(log.getAddTime(), "yyyy-MM-dd HH:mm:ss").before(DateUtil.addSencond(new Date(),-3*24*60*60)) ){
                   log.setChannel(Constants.YHJ);
                    updateQueryState(log);
                }

            }else if (ArrayUtils.contains(PINGAN, paymentTypeId)) {
                //pingAn支付因为没有退款接口 直接调用
                try {
                    log.setRefundState(REFUNDSTATE);
                    myPayService.updateOrderPaymentLog(log);
                    Map mapRequest = new HashMap();
                    mapRequest.put("orderNo", log.getPaymentNo());
                    mapRequest.put("applyStatus", AlipayConfig.REFUNDSTATE);
                    HttpClientTools.doGet(pinganConfig.getRefundUrl(), mapRequest);
                    logger.info("updateQueryState success, orderNo:" + log.getBusinessId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 更新退款状态 调用退款接口
     * @param log
     */
    private void updateQueryState(OrderPaymentLog log) {
        Platform platform = Factory.createPlatform(log.getChannel());
        Map<String, String> queryMap = null;
        try {
            queryMap = platform.refundQuery(log);
            //如果退款成功调用退款接口 修改状态
            if (queryMap != null && queryMap.size() > 0) {
                JSONObject jsonObject = JSONArray.parseObject(queryMap.get("result").toString());
                if ("0000".equals(jsonObject.get("code"))) {
                    log.setRefundState(REFUNDSTATE);
                    myPayService.updateOrderPaymentLog(log);
                    Map mapRequest = new HashMap();
                    mapRequest.put("orderNo", log.getPaymentNo());
                    mapRequest.put("applyStatus", AlipayConfig.REFUNDSTATE);
                    HttpClientTools.doGet(pinganConfig.getRefundUrl(), mapRequest);
                    logger.info("updateQueryState success, orderNo:" + log.getBusinessId());
                }
            }
        } catch (Exception e) {
            logger.error("queryOrderTask exception:" + e);
            e.printStackTrace();
        }
    }
}
