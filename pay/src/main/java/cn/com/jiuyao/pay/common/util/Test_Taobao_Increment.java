package cn.com.jiuyao.pay.common.util;

public class Test_Taobao_Increment {

	/**
	 * @param args  
	 */
	public static void main(String[] args) {
		try {
			//taobao.trades.sold.increment.get (查询卖家已卖出的增量交易数据（根据修改时间）)
/*			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
			TradesSoldIncrementGetRequest req = new TradesSoldIncrementGetRequest();
			req.setFields("tid,type,status,payment,orders,rx_audit_status");
			req.setStartModified(StringUtils.parseDateTime("2016-05-29 00:00:00"));
			req.setEndModified(StringUtils.parseDateTime("2016-05-29 23:59:59"));
			req.setStatus("TRADE_NO_CREATE_PAY");
			req.setType("fixed");
//			req.setBuyerNick("zhangsan");  //需要去掉该行
			req.setExtType("service");
			req.setTag("time_card");
			req.setPageNo(1L);
			req.setRateStatus("RATE_UNBUYER");
			req.setPageSize(40L);
			req.setUseHasNext(true);
			TradesSoldIncrementGetResponse rsp = client.execute(req, sessionKey);
			System.out.println(rsp.getBody());*/
			
			// key: D0000293   测试密钥: 6F5559D4CB82833323A235DF24F0B6E75E725DD20EF5D1B839A9C677D01F9120    13FC726AF3C77D5A5DC7D7E0682C1623EB10F8597559CC0104A4CE90BCF4F149
		} catch(Exception e ){
			e.printStackTrace();
		}
	}
}
