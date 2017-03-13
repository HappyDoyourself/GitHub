package cn.com.dubbo.model;

import cn.com.dubbo.base.bo.BaseBO;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class OrderPaymentLog  extends BaseBO{

	/**
	 *
	 */
	private static final long serialVersionUID = -3538923811030116356L;

	private Long paymentLogId;//支付日志id

	private String businessType;//支付业务类型

	private Long businessId;//业务单据id

	private Integer paymentTypeId;//支付方式id

	private String paymentNo;//支付订单号（支付流水号）

	private BigDecimal paymentFee;//支付金额（应付）

	private BigDecimal paidFee;//支付金额（实付）

	private String paymentTime;//支付时间

	private Long memberId;//会员id


	private String backNo;//返回支付流水号


	private String backTime;//返回流水号时间

	private String backState;//返回状态


	private String backNotes;//返回结果

	private String tempPrhase;//临时促销语

	private String paymentMode;//普通订单的一些字段

	private String channel;//支付渠道

	private String returnUrl;//支付完成后的回调路径

	private String cardNo;//预付卡卡号

	private String reqTxnTime;//用户请求时间

	private String openId;//第三方用户标识

	private String fieldOne;//备用字段1

	private String fieldTwo;//备用字段2

	private String queryState;//支付查询状态

	private String paidState;//最终支付状态

	private String refundAmt;
	private String refundState;
	private String accountFee;
	private String payFee;
	private String needPayFee;
	private String oldPaymentNo;
	private String startTime;
	private String endTime;
	private EcPaymentType ecPaymentType;//支付方式
	private Map<String, String> ecPaymentTypeParames;//支付参数
//	private List<EcPromoteRule> ruleList;//支付促销规则list
//	private AccountLog accountLog;
//	private AccountInfo accountInfo;
	private OrderPaymentLog orderPaymentLog;

	public OrderPaymentLog getOrderPaymentLog() {
		return orderPaymentLog;
	}

	public void setOrderPaymentLog(OrderPaymentLog orderPaymentLog) {
		this.orderPaymentLog = orderPaymentLog;
	}

	public String getPaymentMode() {
		return paymentMode;
	}


	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}


	public Long getPaymentLogId() {
		return paymentLogId;
	}


	public void setPaymentLogId(Long paymentLogId) {
		this.paymentLogId = paymentLogId;
	}


	public String getTempPrhase() {
		return tempPrhase;
	}


	public void setTempPrhase(String tempPrhase) {
		this.tempPrhase = tempPrhase;
	}


	@Override
	public String toString() {
		return "OrderPaymentLog [paymentLogId=" + paymentLogId
				+ ", businessType=" + businessType + ", businessId="
				+ businessId + ", paymentTypeId=" + paymentTypeId
				+ ", paymentNo=" + paymentNo + ", paymentFee=" + paymentFee
				+ ", paidFee=" + paidFee + ", paymentTime=" + paymentTime
				+ ", memberId=" + memberId + ", backNo=" + backNo
				+ ", backTime=" + backTime + ", backState=" + backState
				+ ", backNotes=" + backNotes + ", tempPrhase=" + tempPrhase
				+ ", paymentMode=" + paymentMode + ", channel=" + channel
				+ ", returnUrl=" + returnUrl +", cardNo=" + cardNo
				+ ", reqTxnTime=" + reqTxnTime
				+ ", fieldOne=" + fieldOne + ", fieldTwo=" + fieldTwo +"]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((backNo == null) ? 0 : backNo.hashCode());
		result = prime * result
				+ ((backNotes == null) ? 0 : backNotes.hashCode());
		result = prime * result
				+ ((backState == null) ? 0 : backState.hashCode());
		result = prime * result
				+ ((backTime == null) ? 0 : backTime.hashCode());
		result = prime * result
				+ ((businessId == null) ? 0 : businessId.hashCode());
		result = prime * result
				+ ((businessType == null) ? 0 : businessType.hashCode());
		result = prime * result
				+ ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result + ((paidFee == null) ? 0 : paidFee.hashCode());
		result = prime * result
				+ ((paymentFee == null) ? 0 : paymentFee.hashCode());
		result = prime * result
				+ ((paymentLogId == null) ? 0 : paymentLogId.hashCode());
		result = prime * result
				+ ((paymentMode == null) ? 0 : paymentMode.hashCode());
		result = prime * result
				+ ((paymentNo == null) ? 0 : paymentNo.hashCode());
		result = prime * result
				+ ((paymentTime == null) ? 0 : paymentTime.hashCode());
		result = prime * result
				+ ((paymentTypeId == null) ? 0 : paymentTypeId.hashCode());
		result = prime * result
				+ ((tempPrhase == null) ? 0 : tempPrhase.hashCode());
		result = prime * result
				+ ((channel == null) ? 0 : channel.hashCode());
		result = prime * result
				+ ((returnUrl == null) ? 0 : returnUrl.hashCode());
		result = prime * result
				+ ((cardNo == null) ? 0 : cardNo.hashCode());
		result = prime * result
				+ ((reqTxnTime == null) ? 0 : reqTxnTime.hashCode());
		result = prime * result
				+ ((fieldOne == null) ? 0 : fieldOne.hashCode());
		result = prime * result
				+ ((fieldTwo == null) ? 0 : fieldTwo.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderPaymentLog other = (OrderPaymentLog) obj;
		if (backNo == null) {
			if (other.backNo != null)
				return false;
		} else if (!backNo.equals(other.backNo))
			return false;
		if (backNotes == null) {
			if (other.backNotes != null)
				return false;
		} else if (!backNotes.equals(other.backNotes))
			return false;
		if (backState == null) {
			if (other.backState != null)
				return false;
		} else if (!backState.equals(other.backState))
			return false;
		if (backTime == null) {
			if (other.backTime != null)
				return false;
		} else if (!backTime.equals(other.backTime))
			return false;
		if (businessId == null) {
			if (other.businessId != null)
				return false;
		} else if (!businessId.equals(other.businessId))
			return false;
		if (businessType == null) {
			if (other.businessType != null)
				return false;
		} else if (!businessType.equals(other.businessType))
			return false;
		if (memberId == null) {
			if (other.memberId != null)
				return false;
		} else if (!memberId.equals(other.memberId))
			return false;
		if (paidFee == null) {
			if (other.paidFee != null)
				return false;
		} else if (!paidFee.equals(other.paidFee))
			return false;
		if (paymentFee == null) {
			if (other.paymentFee != null)
				return false;
		} else if (!paymentFee.equals(other.paymentFee))
			return false;
		if (paymentLogId == null) {
			if (other.paymentLogId != null)
				return false;
		} else if (!paymentLogId.equals(other.paymentLogId))
			return false;
		if (paymentMode == null) {
			if (other.paymentMode != null)
				return false;
		} else if (!paymentMode.equals(other.paymentMode))
			return false;
		if (paymentNo == null) {
			if (other.paymentNo != null)
				return false;
		} else if (!paymentNo.equals(other.paymentNo))
			return false;
		if (paymentTime == null) {
			if (other.paymentTime != null)
				return false;
		} else if (!paymentTime.equals(other.paymentTime))
			return false;
		if (paymentTypeId == null) {
			if (other.paymentTypeId != null)
				return false;
		} else if (!paymentTypeId.equals(other.paymentTypeId))
			return false;
		if (tempPrhase == null) {
			if (other.tempPrhase != null)
				return false;
		} else if (!tempPrhase.equals(other.tempPrhase))
			return false;
		if (channel == null) {
			if (other.channel != null)
				return false;
		} else if (!channel.equals(other.channel))
			return false;
		if (returnUrl == null) {
			if (other.returnUrl != null)
				return false;
		} else if (!returnUrl.equals(other.returnUrl))
			return false;
		if (cardNo == null) {
			if (other.cardNo != null)
				return false;
		} else if (!cardNo.equals(other.cardNo))
			return false;
		if (reqTxnTime == null) {
			if (other.reqTxnTime != null)
				return false;
		} else if (!reqTxnTime.equals(other.reqTxnTime))
			return false;
		if (fieldOne == null) {
			if (other.fieldOne != null)
				return false;
		} else if (!fieldOne.equals(other.fieldOne))
			return false;
		if (fieldTwo == null) {
			if (other.fieldTwo != null)
				return false;
		} else if (!fieldTwo.equals(other.fieldTwo))
			return false;
		return true;
	}


	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}


	public Long getBusinessId() {
		return businessId;
	}


	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}


	public Integer getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(Integer paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}


	public String getPaymentNo() {
		return paymentNo;
	}


	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}


	public BigDecimal getPaymentFee() {
		return paymentFee;
	}


	public void setPaymentFee(BigDecimal paymentFee) {
		this.paymentFee = paymentFee;
	}


	public BigDecimal getPaidFee() {
		return paidFee;
	}


	public void setPaidFee(BigDecimal paidFee) {
		this.paidFee = paidFee;
	}


	public String getPaymentTime() {
		return paymentTime;
	}


	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}


	public Long getMemberId() {
		return memberId;
	}


	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getBackNo() {
		return backNo;
	}


	public void setBackNo(String backNo) {
		this.backNo = backNo;
	}


	public String getBackTime() {
		return backTime;
	}


	public void setBackTime(String backTime) {
		this.backTime = backTime;
	}


	public String getBackState() {
		return backState;
	}


	public void setBackState(String backState) {
		this.backState = backState;
	}


	public String getBackNotes() {
		return backNotes;
	}


	public void setBackNotes(String backNotes) {
		this.backNotes = backNotes;
	}


	public String getChannel() {
		return channel;
	}


	public void setChannel(String channel) {
		this.channel = channel;
	}


	public String getReturnUrl() {
		return returnUrl;
	}


	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}


	public String getCardNo() {
		return cardNo;
	}


	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}


	public String getAccountFee() {
		return accountFee;
	}


	public void setAccountFee(String accountFee) {
		this.accountFee = accountFee;
	}


	public String getPayFee() {
		return payFee;
	}


	public void setPayFee(String payFee) {
		this.payFee = payFee;
	}


	public String getNeedPayFee() {
		return needPayFee;
	}


	public void setNeedPayFee(String needPayFee) {
		this.needPayFee = needPayFee;
	}


	public EcPaymentType getEcPaymentType() {
		return ecPaymentType;
	}


	public void setEcPaymentType(EcPaymentType ecPaymentType) {
		this.ecPaymentType = ecPaymentType;
	}


	public Map<String, String> getEcPaymentTypeParames() {
		return ecPaymentTypeParames;
	}


	public void setEcPaymentTypeParames(Map<String, String> ecPaymentTypeParames) {
		this.ecPaymentTypeParames = ecPaymentTypeParames;
	}

	public String getReqTxnTime() {
		return reqTxnTime;
	}

	public void setReqTxnTime(String reqTxnTime) {
		this.reqTxnTime = reqTxnTime;
	}

	public String getFieldOne() {
		return fieldOne;
	}

	public void setFieldOne(String fieldOne) {
		this.fieldOne = fieldOne;
	}

	public String getFieldTwo() {
		return fieldTwo;
	}

	public void setFieldTwo(String fieldTwo) {
		this.fieldTwo = fieldTwo;
	}

	public String getQueryState() {
		return queryState;
	}

	public void setQueryState(String queryState) {
		this.queryState = queryState;
	}

	public String getPaidState() {
		return paidState;
	}

	public void setPaidState(String paidState) {
		this.paidState = paidState;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

//	public List<EcPromoteRule> getRuleList() {
//		return ruleList;
//	}
//
//	public void setRuleList(List<EcPromoteRule> ruleList) {
//		this.ruleList = ruleList;
//	}
//
//	public AccountLog getAccountLog() {
//		return accountLog;
//	}
//
//	public void setAccountLog(AccountLog accountLog) {
//		this.accountLog = accountLog;
//	}
//
//	public AccountInfo getAccountInfo() {
//		return accountInfo;
//	}
//
//	public void setAccountInfo(AccountInfo accountInfo) {
//		this.accountInfo = accountInfo;
//	}

	public String getRefundAmt() {
		return refundAmt;
	}

	public void setRefundAmt(String refundAmt) {
		this.refundAmt = refundAmt;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getOldPaymentNo() {
		return oldPaymentNo;
	}

	public void setOldPaymentNo(String oldPaymentNo) {
		this.oldPaymentNo = oldPaymentNo;
	}

	public String getRefundState() {
		return refundState;
	}

	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}
}