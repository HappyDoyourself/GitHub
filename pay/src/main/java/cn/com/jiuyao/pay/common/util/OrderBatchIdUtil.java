package cn.com.jiuyao.pay.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;


public class OrderBatchIdUtil {
	
	 /*
	 * 自定义渠道订单批次号
	 * 规则 时间 
	 */
	public static long multiChannelOrderBatch(){
		SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMddHHmmss");
		return new Long(sdf.format(new Date()));
	}
	
}
