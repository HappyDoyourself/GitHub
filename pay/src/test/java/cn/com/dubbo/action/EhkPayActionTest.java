package cn.com.dubbo.action;

import cn.com.dubbo.model.EcPaymentType;
import cn.com.dubbo.model.OrderInfo;
import cn.com.dubbo.model.OrderPaymentLog;
import cn.com.dubbo.service.MyPayService;
import cn.com.dubbo.service.OrderService;
import cn.com.dubbo.service.payment.factory.Factory;
import cn.com.dubbo.service.payment.platform.Platform;
import cn.com.jiuyao.pay.common.util.HttpClientTools;
import cn.com.jiuyao.util.payments.alipay.config.AlipayConfig;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jiuyao.ec.common.type.OrderBusinessType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/** 
* EhkPayAction Tester. 
* 
* @author <fanhongtao> 
* @since <pre>01/18/2017</pre> 
* @version 1.0 
*/ 
public class EhkPayActionTest extends JunitSuper{

    @Autowired
    public OrderService orderService;
    @Autowired
    protected MyPayService myPayService;

    @Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: ehkQuery(HttpServletRequest request) 
* 
*/ 
@Test
public void testEhkQuery() throws Exception { 
//TODO: Test goes here...
    String requestId="1020484255125504";
    OrderInfo orderInfo=orderService.findOrderById(requestId);
    OrderPaymentLog orderPaymentLog=new OrderPaymentLog();
    EcPaymentType ecPaymentType=myPayService.findPaymentInfoById(orderInfo.getPaymentTypeId().toString());
    Map<String,String> map=myPayService.findPaymentTypeListInfo(ecPaymentType.getPaymentTypeId());
    orderPaymentLog.setBusinessId(Long.parseLong(requestId));
    orderPaymentLog.setBusinessType(OrderBusinessType.ORDER);
    orderPaymentLog=myPayService.findOrderPaymentLog(orderPaymentLog,false);
    orderPaymentLog.setEcPaymentTypeParames(map);
    orderPaymentLog.setBusinessId(Long.parseLong(requestId));
    Platform platform = Factory.createPlatform(ecPaymentType.getPaymentTypeNo());
    Map<String,String> queryMap= null;
    try {
        queryMap = platform.query(orderPaymentLog);
    } catch (Exception e) {
        e.printStackTrace();
    }

    System.out.println("queryMap:"+queryMap);
} 

/** 
* 
* Method: ehkQueryRefund(HttpServletRequest request) 
* 
*/ 
@Test
public void testEhkQueryRefund() throws Exception {
//TODO: Test goes here...
    String requestId="1147573704839168";
    OrderInfo orderInfo=orderService.findOrderById(requestId);
    OrderPaymentLog orderPaymentLog=new OrderPaymentLog();
    EcPaymentType ecPaymentType=myPayService.findPaymentInfoById(orderInfo.getPaymentTypeId().toString());
    Map<String,String> map=myPayService.findPaymentTypeListInfo(ecPaymentType.getPaymentTypeId());
    orderPaymentLog.setBusinessId(Long.parseLong(requestId));
    orderPaymentLog.setBusinessType(OrderBusinessType.REFUND);
    orderPaymentLog=myPayService.findOrderPaymentLog(orderPaymentLog,false);
    orderPaymentLog.setEcPaymentTypeParames(map);
    orderPaymentLog.setBusinessId(Long.parseLong(requestId));
    Platform platform = Factory.createPlatform(ecPaymentType.getPaymentTypeNo());
    Map<String,String> queryMap= null;
    try {
        queryMap = platform.refundQuery(orderPaymentLog);
    } catch (Exception e) {
        e.printStackTrace();
    }

    System.out.println(queryMap.get("result").toString());

    if (queryMap != null && queryMap.size() > 0) {
        JSONObject jsonObject = JSONArray.parseObject(queryMap.get("result").toString());
        if ("0000".equals(jsonObject.get("code"))) {
            orderPaymentLog.setRefundState("1");
            myPayService.updateOrderPaymentLog(orderPaymentLog);
            Map mapRequest = new HashMap();
            mapRequest.put("orderNo", orderPaymentLog.getPaymentNo());
            mapRequest.put("applyStatus", AlipayConfig.REFUNDSTATE);
        }
    }
} 

/** 
* 
* Method: veryfy(String requestId) 
* 
*/ 
@Test
public void testVeryfy() throws Exception { 
//TODO: Test goes here... 
} 


} 
