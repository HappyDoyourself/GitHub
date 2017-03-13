package cn.com.jiuyao.pay.common.util;


public class Test_Taobao {

	/**
	 * @param args  
	 */
	public static void main(String[] args) {
		
//		 order_no-"TM"+tid  multi_channel_order_batch_id--来源于自己    multi_channel_order_no-"TM"+tid   valid-status  member_nick-buyer_nick
//		 is_pay--status   payment_type--type   delivery_fee--post_fee   order_vouchers--discount_fee  paid_fee--payment   order_fee--total_fee
//		 receive_user--receiver_name  province_id--receiver_state   city_id--receiver_city   area_id--receiver_district   receive_address--省+市+县（名称）
//		 receive_full_address--receiver_address   receive_post--receiver_zip  receive_tel--receiver_phone   receive_mobile--receiver_mobile   commit_time--created
		
		
/*		 OrderModel orderModel=new OrderModel();
		 orderModel.setOrderNo("TM"+tid);
		 orderModel.setMultiChannelOrderNo("TM"+tid);
		 orderModel.setValid(status);
		 orderModel.setMemberNick(buyer_nick);
		 orderModel.setIsPay(status);
		 orderModel.setPaymentType(type);
		 orderModel.setDeliveryFee(post_fee);
		 orderModel.setOrderVouchers(discount_fee);
		 orderModel.setPaidFee(payment);
		 orderModel.setOrderFee(total_fee);
		 orderModel.setReceiveUser(receiver_name);
		 orderModel.setProvinceId(receiver_state);
		 
		 orderModel.setCityId(receiver_city);
		 orderModel.setAreaId(receiver_district);
		 orderModel.setReceiveaddress(receiver_state.concat(receiver_city).concat(receiver_district));
		 orderModel.setReceiveFullAddress(receiver_address);
		 orderModel.setReceivePost(receiver_zip);
		 orderModel.setReceiveTel(receiver_phone);
		 orderModel.setReceiveMobile(receiver_mobile);
		 orderModel.setCommitTime(created);*/
		 
//		 name--title:  old_price--price   price--total_fee   amount-num  price_dis-discount_fee  goods_sum_fee-payment		 
/*		 OrderItemModel orderItemModel=new OrderItemModel();
		 orderItemModel.setOrderNo("TM"+tid);
		 orderItemModel.setGoodsType(rx_audit_status);
		 orderItemModel.seGoodsNo(num_iid);*/
		
		try {
			//taobao.trades.sold.get (查询卖家已卖出的交易数据（根据创建时间）)
/*			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
			TradesSoldGetRequest req = new TradesSoldGetRequest();
			req.setFields("tid,type,status,payment,orders,rx_audit_status");
			req.setStartCreated(StringUtils.parseDateTime("2016-05-29 00:00:00"));
			req.setEndCreated(StringUtils.parseDateTime("2016-05-29 23:59:59"));
			req.setStatus("ALL_WAIT_PAY");
//			req.setBuyerNick("zhangsan");    //需要去掉该行
			req.setType("game_equipment");
			req.setExtType("service");
			req.setRateStatus("RATE_UNBUYER");
			req.setTag("time_card");
			req.setPageNo(1L);
			req.setPageSize(40L);
			req.setUseHasNext(true);
			TradesSoldGetResponse rsp = client.execute(req, sessionKey);
			System.out.println(rsp.getBody());*/
			
			// key: D0000293   测试密钥: 6F5559D4CB82833323A235DF24F0B6E75E725DD20EF5D1B839A9C677D01F9120    13FC726AF3C77D5A5DC7D7E0682C1623EB10F8597559CC0104A4CE90BCF4F149
		} catch(Exception e ){
			e.printStackTrace();
		}
	}
}
