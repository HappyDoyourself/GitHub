package cn.com.dubbo.model;

import java.math.BigDecimal;

import cn.com.dubbo.base.bo.BaseBO;

public class OrderPaymentTypeList extends BaseBO {

	private static final long serialVersionUID = 1475870191803518757L;

	private Long listId;
	private String orderId;
	private Long paymentTypeId;
	private String paymentNo;
	private String paidTime;
	private BigDecimal paidFee;
	
	private String paymentTypeName;
	private String paymentTypeLogo;

	@Override
	public String toString() {
		return "OrderPaymentTypeList [listId=" + listId + ", orderId="
				+ orderId + ", paymentTypeId=" + paymentTypeId + ", paymentNo="
				+ paymentNo + ", paidTime=" + paidTime + ", paidFee=" + paidFee
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((listId == null) ? 0 : listId.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result
				+ ((paymentTypeId == null) ? 0 : paymentTypeId.hashCode());
		result = prime * result
				+ ((paymentNo == null) ? 0 : paymentNo.hashCode());
		result = prime * result
				+ ((paidTime == null) ? 0 : paidTime.hashCode());
		result = prime * result + ((paidFee == null) ? 0 : paidFee.hashCode());
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
		OrderPaymentTypeList other = (OrderPaymentTypeList) obj;
		if (listId == null) {
			if (other.listId != null)
				return false;
		} else if (!listId.equals(other.listId))
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (paymentTypeId == null) {
			if (other.paymentTypeId != null)
				return false;
		} else if (!paymentTypeId.equals(other.paymentTypeId))
			return false;
		if (paymentNo == null) {
			if (other.paymentNo != null)
				return false;
		} else if (!paymentNo.equals(other.paymentNo))
			return false;
		if (paidTime == null) {
			if (other.paidTime != null)
				return false;
		} else if (!paidTime.equals(other.paidTime))
			return false;
		if (paidFee == null) {
			if (other.paidFee != null)
				return false;
		} else if (!paidFee.equals(other.paidFee))
			return false;
		return true;
	}

	public Long getListId() {
		return listId;
	}

	public void setListId(Long listId) {
		this.listId = listId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(Long paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public String getPaidTime() {
		return paidTime;
	}

	public void setPaidTime(String paidTime) {
		this.paidTime = paidTime;
	}

	public BigDecimal getPaidFee() {
		return paidFee;
	}

	public void setPaidFee(BigDecimal paidFee) {
		this.paidFee = paidFee;
	}

	public String getPaymentTypeName() {
		return paymentTypeName;
	}

	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}

	public String getPaymentTypeLogo() {
		return paymentTypeLogo;
	}

	public void setPaymentTypeLogo(String paymentTypeLogo) {
		this.paymentTypeLogo = paymentTypeLogo;
	}

}