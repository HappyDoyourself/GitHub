package cn.com.jiuyao.task; 

import cn.com.dubbo.action.JunitSuper;
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
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
* RefundQueryTask Tester. 
* 
* @author <fanhongtao> 
* @since <pre>03/04/2017</pre> 
* @version 1.0 
*/ 
public class RefundQueryTaskTest extends JunitSuper{


   @Autowired
   protected MyPayService myPayService;

   public static Integer[] ALIPAY = {1, 2, 3};  //支付方式是支付宝微信通过二维码支付的
   public static Integer[] WEIXIN = {4, 6, 7};  //支付方式是支付宝微信通过二维码支付的
   public static Integer[] YINLIAN = {10}; //银联 健康卡
   public static Integer[] PINGAN = {200}; //健康卡
   public static String  REFUNDSTATE = "1";

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: refundQueryTask() 
* 
*/ 
@Test
public void testRefundQueryTask() throws Exception { 
//TODO: Test goes here...
   List<OrderPaymentLog> orderPaymentLogList = myPayService.queryRefundOrder();
   for (OrderPaymentLog log : orderPaymentLogList) {
      String paymentId = log.getPaymentNo();
      Integer paymentTypeId = log.getPaymentTypeId();
      if (ArrayUtils.contains(WEIXIN, paymentTypeId)) {
               log.setChannel(Constants.WXPAY);
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

                     }
                  }
               } catch (Exception e) {
                  e.printStackTrace();
               }
      }
   }
} 


/** 
* 
* Method: updateQueryState(OrderPaymentLog log) 
* 
*/ 
@Test
public void testUpdateQueryState() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = RefundQueryTask.getClass().getMethod("updateQueryState", OrderPaymentLog.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
