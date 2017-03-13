package cn.com.dubbo.model;


import java.math.BigDecimal;
import java.util.Date;

import cn.com.dubbo.base.bo.BaseBO;

public class AccountLog extends BaseBO {
	private static final long serialVersionUID = 1699533431344172277L;
	private Long id;
	private Long businessId;//订单号
	private String tradeNo;//业务流水
	private String type;
	private BigDecimal money;
	private Long paymentTypeId;
	private Long memberId;
	private BigDecimal amount;
	private Date createTime;
	private Long consumeId;
	private String fieldOne;
	private String fieldTwo;
	private String balance;
	private String paymentTypeName;
	private String createTimeStr;
	private Long withdrawTypeId;
	private Date refundTime;
	private String createTimeBeg;
	private String createTimeEnd;
	private String memberName;
	private String businessType;
	
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getCreateTimeBeg() {
		return createTimeBeg;
	}

	public void setCreateTimeBeg(String createTimeBeg) {
		this.createTimeBeg = createTimeBeg;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Long getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(Long paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getConsumeId() {
		return consumeId;
	}

	public void setConsumeId(Long consumeId) {
		this.consumeId = consumeId;
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

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getPaymentTypeName() {
		return paymentTypeName;
	}

	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public Long getWithdrawTypeId() {
		return withdrawTypeId;
	}

	public void setWithdrawTypeId(Long withdrawTypeId) {
		this.withdrawTypeId = withdrawTypeId;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}
}