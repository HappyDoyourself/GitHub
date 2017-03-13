package cn.com.dubbo.service.impl;

import cn.com.dubbo.mapper.OrderInfoMapper;
import cn.com.dubbo.service.BaseService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class BaseServiceImpl implements BaseService
{
	protected final Log logger = LogFactory.getLog("Service");
	
	public String convertStreamToString(java.io.InputStream input)
			throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(input,
				"UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		input.close();
		return sb.toString();
	}
	
	/**
	 * 验证订单是否属于这个会员
	 * @param orderId
	 * @param memberId
	 * @return
	 */
	public boolean validateOrder(Long orderId,Long memberId,OrderInfoMapper orderMapper){
		boolean ok = false;
		Map<String, Long> params = new HashMap<String, Long>(); 
		params.put("orderId", orderId);
		params.put("memberId", memberId);
		Integer count =orderMapper.countValidateOrder(params);
		if (count > 0) {
			ok = true;
		}
		return ok;
	}
}
