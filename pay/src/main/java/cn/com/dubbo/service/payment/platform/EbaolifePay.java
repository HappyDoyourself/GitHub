package cn.com.dubbo.service.payment.platform;

import cn.com.dubbo.model.EcPaymentType;
import cn.com.dubbo.model.OrderPaymentLog;
import cn.com.dubbo.model.OrderInfo;
import cn.com.dubbo.model.OrderItem;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 类 <code>EbaolifePay</code>医卡通
 * 
 * @author qun.su
 * @version 2014-4-16
 */
@Service
public class EbaolifePay extends PlatformPayment implements Platform { 

	@Override
	public String requestMessagePackage(HttpServletRequest request,
			HttpServletResponse response,  OrderPaymentLog orderPaymentLog) {
		Map<String, String> ecPaymentTypeParames = orderPaymentLog.getEcPaymentTypeParames();
		EcPaymentType ecPaymentType = orderPaymentLog.getEcPaymentType();
		String message = "";
		try {
			//取得支付参数 
			String un = ecPaymentTypeParames.get("un");// 用户名
			String pass = ecPaymentTypeParames.get("pass");// 密码
			String operator = ecPaymentTypeParames.get("operator");// 操作员
			String pw = getMd5((un + pass + operator), "UTF-8");// MD5加密 用户名+密码+操作员
			String storeName = ecPaymentTypeParames.get("storeName");
			String identifyCode = ecPaymentTypeParames.get("identifyCode");// 门店序列号
			String serialNo = orderPaymentLog.getPaymentNo();
			Long orderId = Long.parseLong(request.getParameter("orderId"));
			//wap:手机支付，提交时，添加参数t=mobile
			String channel = request.getParameter("channel");
			
			List<OrderItem> items=orderService.queryOrderItemByOrderId(orderId);
			//添加黑名单商品检查
			if(ebaolifeBlacklist(orderId,items)){
				request.setAttribute("paidSuccess", "非常抱歉，此订单中包含卡券商品（医卡通不能支付），请您选择其他支付方式或重新下单，谢谢！");
				request.setAttribute("payFlag", "failure");
				request.setAttribute("orderId", request.getParameter("orderId"));
				request.setAttribute("returnUrl", request.getParameter("returnUrl"));
				request.getRequestDispatcher("/jsp/myec/pay_done.jsp").forward(
						request, response);
				return message;
			}
			
			//根据paytypeNo查询商品支付方式黑名单表，去除黑名单的商品
//			items = removeBlacklist(items,ecPaymentType.getPaymentTypeId().toString());
			
			//组装剩余的商品信息上送
			if(items!=null&&items.size()>0){
				for (int j = 0; j < items.size(); j++) {
					String goodsName=items.get(j).getGoodsName().replace("'", "");
					items.get(j).setGoodsName(goodsName);
					DecimalFormat df = new DecimalFormat("###0.0000");
					BigDecimal testBigDecimal=new BigDecimal("0");
					String oldPrice=df.format(testBigDecimal.add(new BigDecimal(items.get(j).getGoodsPrice()+"")));
					String oldNum=df.format(testBigDecimal.add(new BigDecimal(items.get(j).getGoodsAmount()+"")));
					items.get(j).setGoodsType(oldPrice);
					items.get(j).setGoodsUnit(oldNum);
				}
			}
			OrderInfo order_info=orderService.findOrderById(orderId+"");
			BigDecimal discount_fee=order_info.getDiscountFee();//优惠金额(审核调价后)
			Integer order_points=order_info.getOrderPoints();//使用积分 
			BigDecimal order_vouchers=null==order_info.getOrderVouchers()?BigDecimal.ZERO:order_info.getOrderVouchers();//使用代金券
			BigDecimal other_discounts=order_info.getOtherDiscounts();//其它优惠(如:整单满减)
			BigDecimal delivery_fee=order_info.getDeliveryFee();//运费 
			BigDecimal testBigDecimal=new BigDecimal(order_points);
			BigDecimal costAdjustNum=testBigDecimal.add(discount_fee).add(order_vouchers).add(other_discounts);
			BigDecimal testBigDecimal1=new BigDecimal(delivery_fee+"");
			DecimalFormat df = new DecimalFormat("###0.00");
			String costAdjust=df.format(testBigDecimal1.subtract(costAdjustNum));
			String redirectURL=ecPaymentType.getReturnUrl();//前台
			String callbackURL=ecPaymentType.getNotifyUrl();//后台
			
			request.setAttribute("un", un);
			request.setAttribute("pw",pw);
			request.setAttribute("identifyCode",identifyCode);
			request.setAttribute("storeName",storeName);
			request.setAttribute("operator",operator);
			request.setAttribute("serialNo",serialNo);
			request.setAttribute("items", items);
			request.setAttribute("costAdjust",costAdjust);
			request.setAttribute("callbackURL",callbackURL);
			request.setAttribute("redirectURL",redirectURL); 
			//添加手机客户端支持
			if(channel.equals("wap")){
				request.setAttribute("t","mobile");
			}
			
			
			/**拼接请求信息*/
			Map map = new HashMap();
			map.put("un", un);
			map.put("pw", pw);
			map.put("identifyCode", identifyCode);
			map.put("storeName", storeName);
			map.put("operator", operator);
			map.put("serialNo", serialNo);
			map.put("costAdjust", costAdjust);
			map.put("callbackURL", callbackURL);
			map.put("redirectURL",redirectURL);
			if(channel.equals("wap")){
				map.put("t","mobile");
			}
			message = getContent(map);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("###########ebaolife-requestMessagePackage:"+e.getMessage());
			e.printStackTrace();
		}
		
		return message;
	}

	@Override
	public String returnMessageHandle(HttpServletRequest request,
			HttpServletResponse response, String paymentTypeNo)
			throws Exception {
		try {
			// 取得支付参数
			EcPaymentType ecPaymentType = myPayService
					.findPaymentInfoByNo(paymentTypeNo);
			Map<String, String> ecPaymentTypeParames = myPayService
					.findPaymentTypeListInfo(ecPaymentType.getPaymentTypeId());
			// 接收Server返回的支付信息
			String id=request.getParameter("id")!=null?request.getParameter("id"):"";//医卡通消费流水号
			String serialNo=request.getParameter("serialNo")!=null?request.getParameter("serialNo"):"";//健一网流水号
			String code=request.getParameter("code")!=null?request.getParameter("code"):"";//支付状态
			String cardNo=request.getParameter("cardNo")!=null?request.getParameter("cardNo"):"";//卡号
			String charge=request.getParameter("charge")!=null?request.getParameter("charge"):"";//返回金额
			String chargeTime=request.getParameter("chargeTime")!=null?request.getParameter("chargeTime"):"";//消费时间
			String verifyCode=request.getParameter("verifyCode")!=null?request.getParameter("verifyCode"):"";//医卡通签名
			
			// 获取订单日志信息和订单信息
			OrderPaymentLog orderPaymentLog = new OrderPaymentLog(); 
			orderPaymentLog.setPaymentTypeId(ecPaymentType.getPaymentTypeId());
			orderPaymentLog.setPaymentNo(serialNo);
			orderPaymentLog = myPayService.findOrderPaymentLog(orderPaymentLog,false);
//			OrderInfo orderInfo = orderService.findOrderById(orderPaymentLog
//					.getBusinessId());
//
//			// 拼接记录order_payment_message_log
//			String isPaid = "N";
//			if(null != orderInfo){
//				isPaid = orderInfo.getIsPaid();
//			}
//			String message = getContentByParameterMap(request.getParameterMap());
//			paymentMessageLog(orderPaymentLog.getPaymentLogId(), "response",
//					"return", message, orderPaymentLog.getMemberId(),isPaid);
//
//			// 订单有效性校验
//			String be = checkPamentBackBeforeSign(request, orderPaymentLog
//					.getBusinessId().toString(), new BigDecimal(charge),
//					orderPaymentLog, orderInfo);
			// 拼接记录order_payment_message_log
			String message = getContentByParameterMap(request.getParameterMap());
			paymentMessageLog(orderPaymentLog, "response","return", message);
			// 订单有效性校验
			String be = checkPamentBackBeforeSign(request, orderPaymentLog);
			if (!be.equals("")) {
				request.setAttribute("returnUrl", orderPaymentLog.getReturnUrl());
				request.getRequestDispatcher(be).forward(request, response);
				return null;
			}
			
			//验签
			String un = ecPaymentTypeParames.get("un");// 用户名
			String pass = ecPaymentTypeParames.get("pass");// 密码
			String operator = ecPaymentTypeParames.get("operator");// 操作员
			String pw = getMd5((un + pass + operator), "UTF-8");// MD5加密 用户名+密码+操作员
			String oldVerifyCode=getMd5(serialNo+chargeTime+un+pw,"UTF-8");//健一网签名
			orderPaymentLog.setCardNo(cardNo);
			orderPaymentLog.setBackState(code);
			orderPaymentLog.setBackNo(id);
			orderPaymentLog.setPaidFee(new BigDecimal(charge));
			if (verifyCode.equals(oldVerifyCode)&&code.trim().equals("0")) {
				String success = doSaveResultByLog(orderPaymentLog);
				if (success.equals("success")) {
//					String url = checkPamentBackAfterSign(request,
//							orderPaymentLog.getPaymentNo(), new BigDecimal(charge),
//							orderPaymentLog);
					request.setAttribute("payFlag", "success");
					request.setAttribute("paymentNo",
							orderPaymentLog.getPaymentNo()); 
					request.setAttribute("returnUrl", orderPaymentLog.getReturnUrl());
					request.getRequestDispatcher(PAY_DONE_JSP_PATH).forward(request, response);
					return null;
				}
				
			} else {
				doSaveErrResultByLog(orderPaymentLog);
				request.setAttribute("paidSuccess", "抱歉,您的订单支付失败！");
				request.setAttribute("payFlag", "failure");
				request.setAttribute("paymentNo",
						orderPaymentLog.getPaymentNo());
				request.setAttribute("returnUrl", orderPaymentLog.getReturnUrl());
				request.getRequestDispatcher("/jsp/myec/pay_done.jsp").forward(
						request, response);
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("###########ebaolife-returnMessageHandle:"+e.getMessage());
			e.printStackTrace();
		}
 
		return null;

	}
	
	@Override
	public String notifyMessageHandle(HttpServletRequest request,
			HttpServletResponse response, String paymentTypeNo)
			throws Exception {
		try {
			response.setContentType("text/html;charset=UTF-8");
			// 取得支付参数
			EcPaymentType ecPaymentType = myPayService
					.findPaymentInfoByNo(paymentTypeNo);
			Map<String, String> ecPaymentTypeParames = myPayService
					.findPaymentTypeListInfo(ecPaymentType.getPaymentTypeId());
			// 接收Server返回的支付信息
			String id=request.getParameter("id")!=null?request.getParameter("id"):"";//医卡通消费流水号
			String serialNo=request.getParameter("serialNo")!=null?request.getParameter("serialNo"):"";//健一网流水号
			String code=request.getParameter("code")!=null?request.getParameter("code"):"";//支付状态
			String cardNo=request.getParameter("cardNo")!=null?request.getParameter("cardNo"):"";//卡号
			String charge=request.getParameter("charge")!=null?request.getParameter("charge"):"";//返回金额
			String chargeTime=request.getParameter("chargeTime")!=null?request.getParameter("chargeTime"):"";//消费时间
			String verifyCode=request.getParameter("verifyCode")!=null?request.getParameter("verifyCode"):"";//医卡通签名
			
			// 获取订单日志信息和订单信息
			OrderPaymentLog orderPaymentLog = new OrderPaymentLog(); 
			orderPaymentLog.setPaymentTypeId(ecPaymentType.getPaymentTypeId());
			orderPaymentLog.setPaymentNo(serialNo);
			orderPaymentLog = myPayService.findOrderPaymentLog(orderPaymentLog,false);
			OrderInfo orderInfo = orderService.findOrderById(orderPaymentLog
					.getBusinessId()+"");

//			// 拼接记录order_payment_message_log
//			String isPaid = "N";
//			if(null != orderInfo){
//				isPaid = orderInfo.getIsPaid();
//			}
//			String message = getContentByParameterMap(request.getParameterMap());
//			paymentMessageLog(orderPaymentLog.getPaymentLogId(), "response",
//					"notify", message, orderPaymentLog.getMemberId(),isPaid);
//
//			// 订单有效性校验
//			logger.debug("============================Ebaolife-checkPamentBackBeforeSign");
//			String be = checkPamentBackBeforeSign(request, orderPaymentLog
//					.getBusinessId().toString(), new BigDecimal(charge),
//					orderPaymentLog, orderInfo);
			// 拼接记录order_payment_message_log
			String message = getContentByParameterMap(request.getParameterMap());
			paymentMessageLog(orderPaymentLog, "response","notify", message);
			// 订单有效性校验
			String be = checkPamentBackBeforeSign(request, orderPaymentLog);
			if (!be.equals("")) {
				//组装收货人信息返回给医卡通
				response.getWriter().print(getMsgResponse(orderInfo));
				return null;
			}
			
			//验签
			String un = ecPaymentTypeParames.get("un");// 用户名
			String pass = ecPaymentTypeParames.get("pass");// 密码
			String operator = ecPaymentTypeParames.get("operator");// 操作员
			String pw = getMd5((un + pass + operator), "UTF-8");// MD5加密 用户名+密码+操作员
			String oldVerifyCode=getMd5(serialNo+chargeTime+un+pw,"UTF-8");//健一网签名
			orderPaymentLog.setCardNo(cardNo);
			orderPaymentLog.setBackState(code);
			orderPaymentLog.setBackNo(id);
			orderPaymentLog.setPaidFee(new BigDecimal(charge));
			if (verifyCode.equals(oldVerifyCode) && code.trim().equals("0")) {
				doSaveResultByLog(orderPaymentLog);
			} else {
				doSaveErrResultByLog(orderPaymentLog);
			}
			//组装收货人信息返回给医卡通
			response.getWriter().print(getMsgResponse(orderInfo));
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("###########ebaolife-returnMessageHandle:"+e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Map query(OrderPaymentLog orderPaymentLog) {
		Map<String, Object> orderInfoMap = new HashMap<String, Object>();
		try {
			Map<String, String> parameter = myPayService
					.findPaymentTypeListInfo(orderPaymentLog.getPaymentTypeId());

			//取得支付参数
			String un = parameter.get("un");// 用户名
			String pass = parameter.get("pass");// 密码
			String operator = parameter.get("operator");// 操作员
			String pw = getMd5((un + pass + operator), "UTF-8");// MD5加密 用户名+密码+操作员
			String storeName = parameter.get("storeName");
			String identifyCode = parameter.get("identifyCode");// 门店序列号
			String serialNo = orderPaymentLog.getPaymentNo();

			JSONObject obj = new JSONObject();
			obj.element("un", un);
			obj.element("pw", pw);
			obj.element("storeName", storeName);
			obj.element("operator", operator);
			obj.element("serialNo", serialNo);
			obj.element("identifyCode", identifyCode);
			obj.element("remark", "OK");
//			String postData = "{un:"+un+", pw:"+pw+", " +
//					"storeName:"+storeName+", identifyCode:"+identifyCode+", " +
//					"operator:"+operator+",serialNo:"+serialNo+", remark:0 }";
//			System.out.println(obj.toString());
			// 发送下单请求
			String url = "https://www.ebaolife.net/interface/detailBySerial";
			String resultStr = post(url, obj.toString());
//			System.out.println(resultStr);
//			resultStr = new String(Base64.decode(resultStr),"utf-8");
//			JSONObject jsonObject = new JSONObject(resultStr);
//			String charge = jsonObject.getString("charge");//如果有异常,会在errmsg 和 errcode 描述出来，如果成功 errcode 就是0
//			String chargeTime = jsonObject.getString("chargeTime");
//			String id = jsonObject.getString("id");
//			String code = jsonObject.getString("code");
//			String cardNo = jsonObject.getString("cardNo");
//			BigDecimal amt=new BigDecimal(orderAmt1).divide(new BigDecimal(100));
			//获取结果，验签
/*			if (!j1sign.equals(signValue1)) {
				throw new Exception("平安万里通交易查询返回签名无效，商户流水号："+serialNo);
			}
*/
//			orderPaymentLog.setBackState(code);
//			orderPaymentLog.setBackNo(id);
//			orderPaymentLog.setPaidFee(new BigDecimal(charge));
//			orderPaymentLog.setCardNo(cardNo);
//			String result = doSaveResultByLog(orderPaymentLog);
			String result = "";
			//将获取的信息放入map中返回
			orderInfoMap.put("result", result);
		} catch (Exception e) {
			e.printStackTrace();
			orderInfoMap.put("result","fail");
		}

		return orderInfoMap;
	}
	
	/**
	 * 医卡通MD5加密规则
	 * @param str 需要加密的字符串
	 * @param cod 编码(主要中文有区别)
	 * @return 返回加密后的
	 */
	public static String getMd5(String str,String cod) {
        //确定计算方法
		String newstr="";
	    try {
            MessageDigest md = MessageDigest.getInstance("MD5");            
            byte b[] = md.digest(str.getBytes(cod));
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                    i = b[offset];
                    if (i < 0)
                    i += 256;
                    if (i < 16)
                    buf.append("0");
                    buf.append(Integer.toHexString(i));
            }
            newstr= buf.toString();
   
    } catch (Exception e) {
             e.printStackTrace();
    }
        return newstr;
    }
	
	/**
	 * 医保卡黑名单商品检查
	 * 
	 * @param orderId
	 * @param items
	 * @return
	 * @throws IOException
	 */
	public boolean ebaolifeBlacklist(Long orderId,List<OrderItem> items) throws IOException {
		boolean flag = false;
		List<String> productIdList = myPayService.getBlacklistProducId(101L);
		
		if (null != productIdList && productIdList.size() > 0) {
			// 获取黑名单表中该支付方式对应的productId集合b
			Set<String> bSet = new HashSet<String>();
			for (String productId : productIdList) {
				bSet.add(productId);
			}
			// 获取订单明细中productId集合a
			Set<String> aSet = new HashSet<String>();
			Map gpMap = new HashMap();
			for (OrderItem item : items) {
				aSet.add(item.getProductId().toString());
			}
			// 集合a与集合b交集 c
			Set<String> cSet = new HashSet<String>();
			cSet.clear();
			cSet.addAll(aSet);
			cSet.retainAll(bSet);
			
			//判断交集是否为空，空则表示items中不含黑名单商品
			if(cSet.size() > 0){
				flag = true;
			}  
		}
		
		return flag;
	}
	
	/**
	 * 组装收货人信息返回
	 * @param orderInfo
	 */
	private String getMsgResponse(OrderInfo orderInfo){
		StringBuffer sb = new StringBuffer();
//		String province = orderInfo.getProduceId()+"";
//		String city =  extService.getAreaNameById(orderInfo.getCityId().toString());
//		String area =  extService.getAreaNameById(orderInfo.getAreaId().toString());
//		String district =  province + "," + city + "," + area;
		String district = "";
		sb.append("{\"code\":0");
		sb.append(",\"buyer\":\"" + orderInfo.getReceiveUser() + "\"");
		sb.append(",\"district\":\"" + district + "\"");
		sb.append(",\"address\":\"" + orderInfo.getReceiveAddress() + "\"");
		sb.append(",\"contact\":\"" + orderInfo.getReceiveMobile() + "\"}");
		return sb.toString();
	}

	/**
	 * http 发送方法
	 * @param strUrl 请求URL
	 * @param content 请求内容
	 * @return 响应内容
	 * @throws org.apache.commons.httpclient.HttpException
	 * @throws IOException
	 */
	private String post(String strUrl,String content)
			throws IOException {
		URL url = new URL(strUrl);
		HttpURLConnection connection = (HttpURLConnection) url
				.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestProperty("Content-Type",
				"text/plain;charset=UTF-8");
		connection.connect();
		// POST请求
		DataOutputStream out = new DataOutputStream(
				connection.getOutputStream());
		out.write(content.getBytes("utf-8"));
		out.flush();
		out.close();
		// 读取响应
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), "UTF-8"));
		String lines;
		StringBuffer sb = new StringBuffer("");
		while ((lines = reader.readLine()) != null) {
			lines = new String(lines.getBytes(), "utf-8");
			sb.append(lines);
		}
//		System.out.println(sb);
		reader.close();
		// 断开连接
		connection.disconnect();

		return sb.toString();
	}
	
	public static void main(String[] args) {
		 
	}
}
