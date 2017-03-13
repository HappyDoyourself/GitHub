package cn.com.dubbo.action; 

import cn.com.dubbo.model.OrderPaymentLog;
import cn.com.dubbo.service.MyPayService;
import cn.com.jiuyao.pay.common.util.HttpClientTools;
import cn.com.jiuyao.util.IPUtil;
import cn.com.jiuyao.util.payments.alipay.config.AlipayConfig;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/** 
* PayRefundAction Tester. 
* 
* @author <fanhongtao> 
* @since <pre>01/18/2017</pre> 
* @version 1.0 
*/ 
public class PayRefundActionTest extends JunitSuper{

   @Autowired
   private MyPayService myPayService;

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: refund(HttpServletRequest request, HttpServletResponse response) 
* 
*/ 
@Test
public void testRefund() throws Exception { 
//TODO: Test goes here...
    // String oldPaymentNo = "1021756757233664";//订单号



    String oldPaymentNo = "1023212227186688"; //订单号
   //String paymentNo ="pingan";//类型
   String refundAmt ="0.01";//退款金额
   OrderPaymentLog orderPaymentLog = new OrderPaymentLog();
   orderPaymentLog.setOldPaymentNo(oldPaymentNo);
   orderPaymentLog.setBusinessId(Long.parseLong(oldPaymentNo));
  // orderPaymentLog.setPaymentNo(oldPaymentNo);
   orderPaymentLog.setRefundAmt(refundAmt);
   //orderPaymentLog.setReqTxnTime(IPUtil.getIpAddr(request));
   Map map = myPayService.doRefund(orderPaymentLog);
   System.out.println("resData:"+map.get("responseCode")+"==================");


    JSONObject jsonObject =new JSONObject();
    jsonObject.put("code","0000");
    map.put("responseCode",jsonObject.toString());

    if(map != null && map.size() > 0){
        JSONObject jsonObjectRes = JSONArray.parseObject(map.get("responseCode").toString());
        if ("0000".equals(jsonObjectRes.get("code"))){
            Map mapRequest = new HashMap();
            mapRequest.put("orderNo","1145789911650304");
            mapRequest.put("applyStatus", AlipayConfig.REFUNDSTATE);
            HttpClientTools.doGet("http://192.168.100.233:8080/mall-api-web/order/refund.html", mapRequest);
        }
    }
} 

/** 
* 
* Method: refundNotify(HttpServletRequest request, HttpServletResponse response, @PathVariable String paymentTypeNo) 
* 
*/ 
@Test
public void testRefundNotify() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: verifyRefundData(HttpServletRequest request, HttpServletResponse response) 
* 
*/ 
@Test
public void testVerifyRefundData() throws Exception { 
//TODO: Test goes here... 
} 


/** 
* 
* Method: verifyParam(String oldPaymentNo, String paymentNo, String refundAmt) 
* 
*/ 
@Test
public void testVerifyParam() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = PayRefundAction.getClass().getMethod("verifyParam", String.class, String.class, String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: errorRetrun(HttpServletRequest request) 
* 
*/ 
@Test
public void testErrorRetrun() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = PayRefundAction.getClass().getMethod("errorRetrun", HttpServletRequest.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
