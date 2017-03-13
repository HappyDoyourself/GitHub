package cn.com.dubbo.model;

import java.math.BigDecimal;
import java.util.Date;

import cn.com.dubbo.base.bo.BaseBO;


public class CardLog extends BaseBO {
	 
	/** TODO */
	private static final long serialVersionUID = 8426130481001890293L;
	private Long id;
	private String cardNo;
	private Long businessId;
	private String type;
	private BigDecimal changeFee;
	private BigDecimal beforeFee;
	private BigDecimal afterFee;
	private Date dateTime;
	private Long memberId; 
	private Long addUser;
	private Long editUser;

	@Override
	public String toString() {
		return "CardLog [id=" + id + ", cardNo="
				+ cardNo + ", businessId=" + businessId + ", type=" + type
				+ ", changeFee=" + changeFee + ", beforeFee=" + beforeFee
				+ ", afterFee=" + afterFee + ", dateTime=" + dateTime
				+ ", memberId=" + memberId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((cardNo == null) ? 0 : cardNo.hashCode());
		result = prime * result + ((businessId == null) ? 0 : businessId.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((changeFee == null) ? 0 : changeFee.hashCode());
		result = prime * result + ((beforeFee == null) ? 0 : beforeFee.hashCode());
		result = prime * result + ((afterFee == null) ? 0 : afterFee.hashCode());
		result = prime * result + ((dateTime == null) ? 0 : dateTime.hashCode());
		result = prime * result + ((memberId == null) ? 0 : memberId.hashCode());
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
		CardLog other = (CardLog) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)){
			return false;
		}
		if (cardNo == null) {
			if (other.cardNo != null)
				return false;
		} else if (!cardNo.equals(other.cardNo)){
			return false;
		}
		if (businessId == null) {
			if (other.businessId != null)
				return false;
		} else if (!businessId.equals(other.businessId)){
			return false;
		}
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type)){
			return false;
		}
		if (changeFee == null) {
			if (other.changeFee != null)
				return false;
		} else if (!changeFee.equals(other.changeFee)){
			return false;
		}
		if (beforeFee == null) {
			if (other.beforeFee != null)
				return false;
		} else if (!beforeFee.equals(other.beforeFee)){
			return false;
		}
		if (afterFee == null) {
			if (other.afterFee != null)
				return false;
		} else if (!afterFee.equals(other.afterFee)){
			return false;
		}
		if (dateTime == null) {
			if (other.dateTime != null)
				return false;
		} else if (!dateTime.equals(other.dateTime)){
			return false;
		}
		if (memberId == null) {
			if (other.memberId != null)
				return false;
		} else if (!memberId.equals(other.memberId)){
			return false;
		}
		 
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getChangeFee() {
		return changeFee;
	}

	public void setChangeFee(BigDecimal changeFee) {
		this.changeFee = changeFee;
	}

	public BigDecimal getBeforeFee() {
		return beforeFee;
	}

	public void setBeforeFee(BigDecimal beforeFee) {
		this.beforeFee = beforeFee;
	}

	public BigDecimal getAfterFee() {
		return afterFee;
	}

	public void setAfterFee(BigDecimal afterFee) {
		this.afterFee = afterFee;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getAddUser() {
		return addUser;
	}

	public void setAddUser(Long addUser) {
		this.addUser = addUser;
	}

	public Long getEditUser() {
		return editUser;
	}

	public void setEditUser(Long editUser) {
		this.editUser = editUser;
	}

}