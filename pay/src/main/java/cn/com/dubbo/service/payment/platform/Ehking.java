package cn.com.dubbo.service.payment.platform;

import cn.com.dubbo.excption.EhkException;
import cn.com.dubbo.model.EcPaymentType;
import cn.com.dubbo.model.OrderItem;
import cn.com.dubbo.model.OrderPaymentLog;
import cn.com.dubbo.model.OrderPaymentMessageLog;
import cn.com.dubbo.service.payment.constant.Constants;
import cn.com.jiuyao.pay.common.util.DateUtils;
import cn.com.jiuyao.pay.common.util.MathUtil;
import cn.com.jiuyao.util.payments.ehk.EhkHttpUtils;
import cn.com.jiuyao.util.payments.ehk.EhkMd5;
import cn.com.jiuyao.util.payments.ehk.FastJsonUtils;
import cn.com.jiuyao.util.payments.pingan.httpClient.TransType;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jiuyao.ec.common.type.OrderBusinessType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 易汇金支付
 * @author
 *
 */
@Service
public class Ehking extends PlatformPayment implements Platform{
    @Override
    public String requestMessagePackage(HttpServletRequest request, HttpServletResponse response, OrderPaymentLog orderPaymentLog) {
       Map<String,String> map=orderPaymentLog.getEcPaymentTypeParames();
        EcPaymentType ecPaymentType=orderPaymentLog.getEcPaymentType();
        String message="";
        try {
            String merchantId=map.get("merchantId");//商户编号
            String orderCurrency=map.get("ecPaymentType");//订单币种
            BigDecimal orderAmount= MathUtil.changeY2F(orderPaymentLog.getPaymentFee());
            String requestId=String.valueOf(orderPaymentLog.getBusinessId());//订单号
            String callbackUrl;//通知地址
            if(!TransType.CHANNEL.equals(orderPaymentLog.getChannel())){
                callbackUrl = orderPaymentLog.getReturnUrl() + "?orderNo=" + orderPaymentLog.getBusinessId();//通知地址
            }else {
                callbackUrl = ecPaymentType.getReturnUrl();//通知地址
            }
            String notifyUrl=ecPaymentType.getNotifyUrl();//回调地址
            String paymentModeCode=orderPaymentLog.getFieldOne();//通过支付方式编码实现直连支付方式

            //商品信息
            List<OrderItem> orderItemList=orderService.getOrderItemListById(Long.parseLong(requestId));
            if (CollectionUtils.isEmpty(orderItemList)){
                logger.error("produceDetail is null ,orderNo:{" + requestId+"}");
                return  null;
            }

            String paymentNo=orderPaymentLog.getPaymentNo();
            OrderPaymentMessageLog messageLog=new OrderPaymentMessageLog();
            messageLog.setPaymentLogId(orderPaymentLog.getPaymentLogId());
            List<String> listForSign=new ArrayList<String>();
            listForSign.add(merchantId);
            listForSign.add(orderAmount.toString());
            listForSign.add(orderCurrency);
            listForSign.add(paymentNo);
            listForSign.add(notifyUrl);
            listForSign.add(callbackUrl);
            if (StringUtils.isNotEmpty(paymentModeCode)){
                listForSign.add(paymentModeCode);//通过支付方式编码实现直连支付方式
            }

            for (OrderItem orderItem : orderItemList){
                listForSign.add(orderItem.getGoodsName());//商品名称
                listForSign.add(orderItem.getGoodsAmount().toString());//商品数量
                listForSign.add(MathUtil.changeY2F(orderItem.getGoodsPrice()).toString());//商品成交价  注意单位是分
            }
            String ehkKey=map.get("ehkKey");//商户秘钥 获取hmac
            String key=this.hmacList(listForSign, ehkKey);//获取签名算法

            //传参数
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("merchantId",merchantId);
            jsonObject.put("orderAmount",orderAmount.toString());
            jsonObject.put("orderCurrency",orderCurrency);
            jsonObject.put("requestId",paymentNo);
            jsonObject.put("notifyUrl",notifyUrl);
            jsonObject.put("callbackUrl",callbackUrl);
            if (StringUtils.isNotEmpty(paymentModeCode)){
                jsonObject.put("paymentModeCode",paymentModeCode);
            }

            //接口文档里面 商品信息不能为空且为json格式
            JSONArray productDetails=new JSONArray();
            for (OrderItem orderItem : orderItemList){
                Map<String,String> product=new LinkedHashMap<String, String>();
                product.put("name",orderItem.getGoodsName());
                product.put("quantity",orderItem.getGoodsAmount().toString());
                product.put("amount",MathUtil.changeY2F(orderItem.getGoodsPrice()).toString());
                productDetails.add(product);
            }
            jsonObject.put("productDetails",productDetails);
            jsonObject.put("payer",new JSONObject());
            jsonObject.put("hmac",key);

            JSONObject resData=this.order(jsonObject,map.get("paygateway"),orderPaymentLog);
            //保持到页面中
            if (resData!=null && "REDIRECT".equals(resData.get("status"))){
                 //如果是wap或者app,返回json格式数据
                if (!TransType.CHANNEL.equals(orderPaymentLog.getChannel())){
                    //返回给app端
                    net.sf.json.JSONObject jb = new net.sf.json.JSONObject();
                    net.sf.json.JSONObject data = new net.sf.json.JSONObject();
                    jb.element("status", 0);
                    jb.element("msg", "OK");
                    data.element("redirectUrl",resData.getString("redirectUrl")); //wap端用到的跳转页面
                    jb.element("data", data);

                    StringBuilder str = new StringBuilder();
                    String callback = request.getParameter("callback");
                    if (StringUtils.isNotEmpty(callback)) {
                        str.append(callback);
                    }
                    str.append(jb.toString());
                    response.resetBuffer();
                    response.setContentType("text/html;charset=UTF-8");
                    response.getWriter().print(str.toString());
                    response.getWriter().flush();
                    response.getWriter().close();
                    response.flushBuffer();
                }

                request.setAttribute("merchantId",resData.getString("merchantId"));
                request.setAttribute("status",resData.getString("status"));
                request.setAttribute("requestId",resData.getString("requestId"));
                request.setAttribute("redirectUrl",resData.getString("redirectUrl"));
                message=resData.toString();
            }
            else {
                logger.error("EHKing order error,errorMessage:"+resData);
                return "";
            }
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        return message;
    }


    /**
     * 同步通知
     * @param request 请求参数
     * @param response
     * @param paymentTypeNo
     * @return
     * @throws Exception
     */
    @Override
    public String returnMessageHandle(HttpServletRequest request, HttpServletResponse response, String paymentTypeNo) throws Exception {
        EcPaymentType ecPaymentType=myPayService.findPaymentInfoByNo(paymentTypeNo);
        String merchantId=request.getParameter("merchantId");
        String requestId=request.getParameter("requestId");
        logger.info("EHKing Synchronize return parameter,merchantId:{"+merchantId+"},requestId:{"+requestId+"}");
        OrderPaymentLog orderPaymentLog=new OrderPaymentLog();
        orderPaymentLog.setPaymentNo(requestId);
        orderPaymentLog.setPaymentTypeId(ecPaymentType.getPaymentTypeId());
        orderPaymentLog=myPayService.findOrderPaymentLog(orderPaymentLog,false);
        Map<String,String> mapParam=new LinkedHashMap<String,String>();
        mapParam.put("merchantId",merchantId);
        mapParam.put("requestId",requestId);
        String message=getContent(mapParam);
        paymentMessageLog(orderPaymentLog, "response","return", message);

        //检查订单的有效性
        String isCheck=checkPamentBackBeforeSign(request,orderPaymentLog);
        if (!StringUtils.isEmpty(isCheck)) {
            return null;
        }
        doSaveResultByLog(orderPaymentLog);
        //pc端
        request.setAttribute("payFlag", "success");
        request.setAttribute("paymentNo",orderPaymentLog.getPaymentNo());
        request.setAttribute("returnUrl", orderPaymentLog.getReturnUrl());
        request.setAttribute("paidSuccess", "订单支付成功");
        request.setAttribute("orderId", orderPaymentLog.getBusinessId());
        request.setAttribute("total_fee", orderPaymentLog.getPaymentFee());
        request.setAttribute("deliveryReceiver", "");
        request.setAttribute("deliveryAddressFull", "");
        request.getRequestDispatcher(PAY_DONE_JSP_PATH).forward(request, response);
        return super.returnMessageHandle(request, response, paymentTypeNo);
    }

    /**
     * 异步通知
     * @param request
     * @param response
     * @param paymentTypeNo
     *            支付方式编码
     * @return
     * @throws Exception
     */
    @Override
    public String notifyMessageHandle(HttpServletRequest request, HttpServletResponse response, String paymentTypeNo) throws Exception {
        JSONObject responseData= FastJsonUtils.convert(request.getInputStream());
        logger.info("EHKing asynchronous return  parameter,responseData: {"+responseData+"}");
        EcPaymentType ecPaymentType = myPayService.findPaymentInfoByNo(paymentTypeNo);
        Map<String,String> map=myPayService.findPaymentTypeListInfo(ecPaymentType.getPaymentTypeId());
        //修改订单状态,日志记录
        if (responseData.get("status")!=null && "SUCCESS".equals(responseData.get("status").toString())){
            String hmac=responseData.get("hmac").toString();
            OrderPaymentLog orderPaymentLog=new OrderPaymentLog();
            orderPaymentLog.setPaymentNo(responseData.get("requestId").toString());
            orderPaymentLog.setPaymentTypeId(ecPaymentType.getPaymentTypeId());
            orderPaymentLog=myPayService.findOrderPaymentLog(orderPaymentLog,false);
            orderPaymentLog.setBackState(responseData.get("status").toString());//支付状态
            orderPaymentLog.setBackNo(responseData.get("serialNumber").toString());//易汇金系统交易流水号
            orderPaymentLog.setPaidFee(MathUtil.changeF2Y(new BigDecimal(responseData.get("orderAmount").toString())));//订单金额

            String ehkKey=map.get("ehkKey");
            Map<String,String> mapParam=new LinkedHashMap<String,String>();
            mapParam=this.responseData(mapParam, responseData);//获取参数并存到map里面，对返回参数验签
            String message=getContent(mapParam);
            paymentMessageLog(orderPaymentLog, "response","return", message);
            //检查订单的有效性
            String isCheck=checkPamentBackBeforeSign(request,orderPaymentLog);
            if (!StringUtils.isEmpty(isCheck)) {
                return isCheck;
            }

            String md5=this.hmacMap(mapParam,ehkKey);
            if (hmac.equals(md5)){
                doSaveResultByLog(orderPaymentLog);
                return null;
            }else{
                doSaveErrResultByLog(orderPaymentLog);
                return null;
            }
        }
        return  null;
    }


    /**
     * 支付查询
     * @param orderPaymentLog
     * @return
     * @throws Exception
     */
    @Override
    public Map query(OrderPaymentLog orderPaymentLog) throws Exception {
        Map<String,String> queryMap=new HashMap<String, String>();
        //参数封装并获取hmac验签
        Map<String,String> requestMap=new LinkedHashMap<String, String>();
        requestMap.put("merchantId",orderPaymentLog.getEcPaymentTypeParames().get("merchantId"));
        requestMap.put("requestId",orderPaymentLog.getPaymentNo());
        String hmac=this.hmacMap(requestMap, orderPaymentLog.getEcPaymentTypeParames().get("ehkKey"));
         //传参数
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("merchantId",orderPaymentLog.getEcPaymentTypeParames().get("merchantId"));
        jsonObject.put("requestId",orderPaymentLog.getPaymentNo());
        jsonObject.put("hmac",hmac);

        JSONObject responseData=this.order(jsonObject,orderPaymentLog.getEcPaymentTypeParames().get("ehkQuery"),orderPaymentLog);
        Map<String,String> returnMap=new LinkedHashMap<String, String>();
        if (responseData!=null && "SUCCESS".equals(responseData.get("status"))){
            returnMap=this.responseData(returnMap, responseData);
            String responseHmac=responseData.get("hmac").toString();
            String countHmac=this.hmacMap(returnMap, orderPaymentLog.getEcPaymentTypeParames().get("ehkKey"));
            if (!responseHmac.equals(countHmac)){
                logger.error("query ehk error:hmac is not right");
                throw  new  EhkException("query ehk error:hmac is not right"+countHmac+":"+countHmac);
            }
        }
        queryMap.put("result", responseData == null ? "" : responseData.toString());
        return queryMap;
    }

    /**
     * 退款查询
     * @param orderPaymentLog
     * @return
     * @throws Exception
     */
    @Override
    public Map refundQuery(OrderPaymentLog orderPaymentLog) throws Exception {
        Map<String,String> resMap=new HashMap<String, String>();
        String merchantId=orderPaymentLog.getEcPaymentTypeParames().get("merchantId");
        String requestId=orderPaymentLog.getPaymentNo();
        String ehkKey=orderPaymentLog.getEcPaymentTypeParames().get("ehkKey");
        Map<String,String> requestMap=new LinkedHashMap<String, String>();
        requestMap.put("merchantId",merchantId);
        requestMap.put("requestId",requestId);
        String hmac=this.hmacMap(requestMap,ehkKey);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("hmac",hmac);
        jsonObject.put("merchantId",merchantId);
        jsonObject.put("requestId",requestId);
        JSONObject responseData=this.order(jsonObject,orderPaymentLog.getEcPaymentTypeParames().get("ehkRefundQuery"),orderPaymentLog);
        JSONObject jsonRes = new JSONObject();
        Map<String,String> map=new LinkedHashMap<String, String>();
        if (responseData.get("status").equals("SUCCESS")){
            map=this.responseData(map, responseData);
            String resHmac=responseData.get("hmac").toString();
            String countHmac=this.hmacMap(map,ehkKey);
            if (!resHmac.equals(countHmac)){
                logger.error("refundQuery error hmac is not match");
                throw  new EhkException("refundQuery error hmac is  not match");
            }
            jsonRes.put("code","0000");
            resMap.put("result",jsonRes.toString());
        }
        return resMap;
    }

    /**
     * 退款
     * @param orderPaymentLog 退款信息
     * @return
     * @throws Exception
     */
    @Override
    public Map refund(OrderPaymentLog orderPaymentLog) throws Exception {
        Map<String,String> refundMap=new HashMap<String, String>();
        String merchantId=orderPaymentLog.getEcPaymentTypeParames().get("merchantId");
        String requestId=orderPaymentLog.getPaymentNo();
        String ehkKey=orderPaymentLog.getEcPaymentTypeParames().get("ehkKey");
        String amount=MathUtil.changeY2F(orderPaymentLog.getPaymentFee()).toString();
        String orderId=orderPaymentLog.getBackNo();//交易流水号,对应文档里面的serialNumber
        String notifyUrl=orderPaymentLog.getEcPaymentTypeParames().get("refundNotify");
        Map<String,String> requestMap=new LinkedHashMap<String, String>();
        requestMap.put("merchantId",merchantId);
        requestMap.put("requestId",requestId);
        requestMap.put("amount",amount);
        requestMap.put("orderId",orderId);
        requestMap.put("notifyUrl",notifyUrl);
        String hmac=this.hmacMap(requestMap,ehkKey);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("merchantId",merchantId);
        jsonObject.put("requestId",requestId);
        jsonObject.put("amount", amount);
        jsonObject.put("orderId",orderId);
        jsonObject.put("notifyUrl",notifyUrl);
        jsonObject.put("hmac",hmac);
        JSONObject responseData=this.order(jsonObject,orderPaymentLog.getEcPaymentTypeParames().get("ehkRefund"),orderPaymentLog);
        Map<String,String> map=new LinkedHashMap<String, String>();
        if (responseData!=null && "SUCCESS".equals(responseData.get("status"))){
            map=this.responseData(map, responseData);
            String resHmac=responseData.get("hmac").toString();
            String countHmac=this.hmacMap(map,ehkKey);
            if (!resHmac.equals(countHmac)){
                responseData.put("code","0001");
                logger.error("hmac is not match");
                throw  new EhkException("errorMessage:hmac is not match ");
            }
            responseData.put("code","0000");
        }
        //refundMap.put("result",responseData == null ? "1000" :responseData.toString());
        return refundMap;
    }

    /**
     * 退款通知地址
     * @param request
     * @param response
     * @param paymentTypeNo
     *            支付方式编码
     * @return
     * @throws Exception
     */
    @Override
    public Object refundNotify(HttpServletRequest request, HttpServletResponse response, String paymentTypeNo) throws Exception {
        System.out.println("=========退款返回页面=======");
        JSONObject responseData = FastJsonUtils.convert(request.getInputStream());
        //修改订单状态,日志记录
        if (responseData.get("status") != null && "SUCCESS".equals(responseData.get("status").toString())) {
            EcPaymentType ecPaymentType = myPayService.findPaymentInfoByNo(Constants.YHJ);
            Map<String, String> map = myPayService.findPaymentTypeListInfo(ecPaymentType.getPaymentTypeId());
            String hmac = responseData.get("hmac").toString();
            OrderPaymentLog orderPaymentLog = new OrderPaymentLog();
            orderPaymentLog.setPaymentNo(responseData.get("requestId").toString());
            orderPaymentLog.setPaymentTypeId(ecPaymentType.getPaymentTypeId());
            orderPaymentLog.setBusinessType(OrderBusinessType.REFUND);//退款
            orderPaymentLog = myPayService.findOrderPaymentLog(orderPaymentLog, false);
            orderPaymentLog.setBackState(responseData.get("status").toString());//退款状态
            orderPaymentLog.setBackNo(responseData.get("serialNumber").toString());//易汇金系统交易流水号
            orderPaymentLog.setPaidFee(MathUtil.changeF2Y(new BigDecimal(responseData.get("amount").toString())));//订单金额
            orderPaymentLog.setBackTime(DateUtils.getCurrentDateString());
            orderPaymentLog.setRefundState(responseData.get("status").toString());
            String ehkKey = map.get("ehkKey");
            Map<String, String> mapParam = new LinkedHashMap<String,String>();
            mapParam = this.responseData(mapParam, responseData);//获取参数并存到map里面，对返回参数验签
            String message = getContent(mapParam);
            paymentMessageLog(orderPaymentLog, "response", "return", message);
            //检查订单的有效性
            String isCheck = checkPamentBackBeforeSign(request, orderPaymentLog);
            if (!StringUtils.isEmpty(isCheck)) {
                return isCheck;
            }

            String md5 = this.hmacMap(mapParam, ehkKey);
            if (hmac.equals(md5)) {
                doSaveResultByLog(orderPaymentLog);
                request.setAttribute("payFlag", "success");
                request.setAttribute("paymentNo", orderPaymentLog.getPaymentNo());
                request.setAttribute("returnUrl", orderPaymentLog.getReturnUrl());
                request.setAttribute("paidSuccess", "订单退款成功");
                request.setAttribute("orderId", orderPaymentLog.getBusinessId());
                request.setAttribute("total_fee", orderPaymentLog.getPaymentFee());
                request.setAttribute("deliveryReceiver", "");
                request.setAttribute("deliveryAddressFull", "");
                request.getRequestDispatcher(PAY_DONE_JSP_PATH).forward(request, response);
                return null;
            } else {
                doSaveErrResultByLog(orderPaymentLog);
                request.setAttribute("paidSuccess", "抱歉,您的订单支付失败！");
                request.setAttribute("payFlag", "failure");
                request.setAttribute("paymentNo",
                        orderPaymentLog.getPaymentNo());
                request.setAttribute("returnUrl", orderPaymentLog.getReturnUrl());
                request.getRequestDispatcher(PAY_DONE_JSP_PATH).forward(
                        request, response);
                return null;
            }
        }
        return null;
    }




    /**
     * httpClient请求
     * @param requestData
     * @param requestUrl
     * @return
     * @throws Exception
     */
    private JSONObject order(JSONObject requestData,String requestUrl,OrderPaymentLog orderPaymentLog) throws Exception {
        JSONObject responseData;
        String response;
        Map<String,Object> map = this.parseJSON2Map(requestData);
        String message=getContent(map);
        paymentMessageLog(orderPaymentLog, "request","", message);
        try {
            logger.debug("requestData:[" + requestData.toString() + "]" + ",requestUrl:" + requestUrl);
            response = EhkHttpUtils.post(requestUrl, requestData.toString());
            logger.debug("responseStr:[" + response + "]");
            responseData=JSONObject.parseObject(response);
        } catch (Exception exception) {
            logger.error("exception:"+exception);
            throw new Exception(exception);
        }
        return  responseData;
    }


    private Map<String,Object> parseJSON2Map(JSONObject responseData){
        Map<String,Object> map=new ConcurrentHashMap<String, Object>();
        logger.info("parJSON2MapData" + responseData);
        for (Object key:responseData.keySet()){
           Object value=responseData.get(key);
            if (key instanceof  JSONArray){
                String  json= JSON.toJSONString(key);
                JSONArray jsonArray=JSONArray.parseArray(json);
                for(Object object : jsonArray){
                    map=parseJSON2Map((JSONObject)object);
                }
            }else {
                map.put(key.toString(), value);
            }
        }
        return map;
    }

    private Map<String,String> responseData( Map<String,String> returnMap,JSONObject responseData){
        returnMap.put("merchantId",responseData.get("merchantId").toString());
        returnMap.put("requestId",responseData.get("requestId").toString());
        returnMap.put("serialNumber",responseData.get("serialNumber")==null?"":responseData.get("serialNumber").toString());

        returnMap.put("totalRefundCount",responseData.get("totalRefundCount")==null?"":responseData.get("totalRefundCount").toString());
        returnMap.put("totalRefundAmount",responseData.get("totalRefundAmount")==null?"": responseData.get("totalRefundAmount").toString());
        returnMap.put("orderCurrency",responseData.get("orderCurrency")==null?"": responseData.get("orderCurrency").toString());
        returnMap.put("orderAmount",responseData.get("orderAmount")==null?"": responseData.get("orderAmount").toString());

        returnMap.put("orderId",responseData.get("orderId")==null?"":responseData.get("orderId").toString());
        returnMap.put("status",responseData.get("status")==null?"":responseData.get("status").toString());
        returnMap.put("errorCode",responseData.get("errorCode")==null?"":responseData.get("errorCode").toString());
        returnMap.put("errorMessage",responseData.get("errorMessage")==null?"":responseData.get("errorMessage").toString());
        returnMap.put("amount",responseData.get("amount")==null?"":responseData.get("amount").toString());
        returnMap.put("currency",responseData.get("currency")==null?"":responseData.get("currency").toString());
        returnMap.put("completeDateTime", responseData.get("completeDateTime") == null ? "" : responseData.get("completeDateTime").toString());

        return returnMap;
    }


    private String hmacMap(Map<String,String> map,String ehkKey){
        if (map==null || map.size()==0){
            return null;
        }
        StringBuffer stringBuffer=new StringBuffer();
        for (Map.Entry<String,String> entry:map.entrySet()){
            stringBuffer.append(entry.getValue());
        }
        return  EhkMd5.signMd5(stringBuffer.toString(), ehkKey);
    }

    /**
     * 下单时候 商品信息有多个处理
     * @param list
     * @param ehkKey
     * @return
     */
    private String hmacList(List<String> list,String ehkKey){
        if (list==null || list.size()==0){
            return null;
        }
        StringBuffer stringBuffer=new StringBuffer();
        for (String str : list){
            stringBuffer.append(str);
        }
        return  EhkMd5.signMd5(stringBuffer.toString(), ehkKey);
    }

}
