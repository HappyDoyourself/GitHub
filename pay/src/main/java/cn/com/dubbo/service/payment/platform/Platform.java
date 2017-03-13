package cn.com.dubbo.service.payment.platform;

import cn.com.dubbo.model.OrderPaymentLog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 类 <code>Platform</code>支付平台接口
 * 
 * @author qun.su
 * @version 2014-3-20
 */
public interface Platform {
	/**
	 * 获取参数组装银行或第三方机构需要的报文信息
	 * 
	 * @param request
	 * @param response
	 * @return "" 代表初始化失败
	 */
	public String requestMessagePackage(HttpServletRequest request,
			HttpServletResponse response,OrderPaymentLog orderPaymentLog);

	/**
	 * 接收银行或第三方支付机构同步返回信息 1、获取参数 记录order_payment_message_log 2、订单有效性校验 3、验签
	 * 4、对验签结果进行处理，更新订单状态 5、跳转到前台页面通知支付结果
	 * 
	 * @param request
	 * @param response
	 * @param paymentTypeNo
	 *            支付方式编码
	 * @return
	 */
	public String returnMessageHandle(HttpServletRequest request,
			HttpServletResponse response, String paymentTypeNo) throws Exception;

	/**
	 * 接收银行或第三方支付机构异步信息通知 1、获取参数 记录order_payment_message_log 2、订单有效性校验 3、验签
	 * 4、对验签结果进行处理，更新订单状态
	 * 
	 * @param request
	 * @param response
	 * @param paymentTypeNo
	 *            支付方式编码
	 * @return
	 */
	public String notifyMessageHandle(HttpServletRequest request,
			HttpServletResponse response, String paymentTypeNo) throws Exception;
	
	/**
	 * 查询订单信息
	 * @param orderPaymentLog
	 * @return
	 * @throws Exception
	 */
	public Map query(OrderPaymentLog orderPaymentLog) throws Exception;

	/**
	 * 其他备用接口
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public Object extra(Object object) throws Exception;

	/**
	 * 订单退款
	 * @param orderPaymentLog 退款信息
	 * @return
	 * @throws Exception
	 */
	public Map refund(OrderPaymentLog orderPaymentLog) throws Exception;

	/**
	 * 退款异步信息通知
	 *
	 * @param request
	 * @param response
	 * @param paymentTypeNo
	 *            支付方式编码
	 * @return
	 */
	public Object refundNotify(HttpServletRequest request,
							   HttpServletResponse response, String paymentTypeNo) throws Exception;
	/**
	 * 订单退款查询
	 * @param orderPaymentLog 退款信息
	 * @return
	 * @throws Exception
	 */
	public Map refundQuery(OrderPaymentLog orderPaymentLog) throws Exception;
}
