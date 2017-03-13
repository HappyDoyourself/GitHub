package cn.com.dubbo.model;


import java.math.BigDecimal;

import cn.com.dubbo.base.bo.BaseBO;

public class AccountInfo extends BaseBO{
	/** TODO */
	private static final long serialVersionUID = 7941761244227050202L;

	private Long id;

	private Long memberId;

	private BigDecimal accountFee;

	private BigDecimal frozenFee;

	private BigDecimal rechargeAmount;

	private BigDecimal consumeAmount;
	
	private String tradePassword;

	private String fieldOne;

	private String fieldTwo;

	private String addTime;

	private Long addUser;

	private String editTime;

	private Long editUser;

	private String isDelete;

	private String mTradePassword;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public BigDecimal getAccountFee() {
		return accountFee;
	}

	public void setAccountFee(BigDecimal accountFee) {
		this.accountFee = accountFee;
	}

	public BigDecimal getFrozenFee() {
		return frozenFee;
	}

	public void setFrozenFee(BigDecimal frozenFee) {
		this.frozenFee = frozenFee;
	}

	public BigDecimal getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(BigDecimal rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public BigDecimal getConsumeAmount() {
		return consumeAmount;
	}

	public void setConsumeAmount(BigDecimal consumeAmount) {
		this.consumeAmount = consumeAmount;
	}

	public String getFieldOne() {
		return fieldOne;
	}

	public void setFieldOne(String fieldOne) {
		this.fieldOne = fieldOne == null ? null : fieldOne.trim();
	}

	public String getFieldTwo() {
		return fieldTwo;
	}

	public void setFieldTwo(String fieldTwo) {
		this.fieldTwo = fieldTwo == null ? null : fieldTwo.trim();
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime == null ? null : addTime.trim();
	}

	public Long getAddUser() {
		return addUser;
	}

	public void setAddUser(Long addUser) {
		this.addUser = addUser;
	}

	public String getEditTime() {
		return editTime;
	}

	public void setEditTime(String editTime) {
		this.editTime = editTime == null ? null : editTime.trim();
	}

	public Long getEditUser() {
		return editUser;
	}

	public void setEditUser(Long editUser) {
		this.editUser = editUser;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete == null ? "N" : isDelete.trim();
	}

	public String getTradePassword() {
		return tradePassword;
	}

	public void setTradePassword(String tradePassword) {
		this.tradePassword = tradePassword;
	}

	public String getmTradePassword() {
		return mTradePassword;
	}

	public void setmTradePassword(String mTradePassword) {
		this.mTradePassword = mTradePassword;
	}
}