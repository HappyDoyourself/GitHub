package cn.com.dubbo.model;

import java.math.BigDecimal;

import cn.com.dubbo.base.bo.BaseBO;


/**
 * 类 CardInfo{健一卡信息表}更改数据类型
 * 
 * @author modify by lk
 * @version 2014-9-1 下午1:56:53
 */
public class CardInfo extends BaseBO
{
	private static final long serialVersionUID = 1699533431344172277L;
	private String cardNo;
	private Long cardBatchId;
	private String cardPass;
	private String cardType;
	private BigDecimal cardFee;
	private BigDecimal saleFee;
	private BigDecimal cardBalance;
	private BigDecimal frozenFee;
	private String valid;
	private String state;
	private String memberName;
	private String phone;
	private Long memberId;
	private String activationDate;
	private String sellDate;
	private Long addUser;
	private Long editUser;
	private Long buyState;
	// alter table card_info add now_version NUMBER(8)
	private Long nowVersion;
	private Long prevVersion;
	private long amount;
	private String buyTime;
	private Long cardOrder;
	private BigDecimal discount;
	//创建一个构造器用于卡信息导出是使用 add by lk 2014-9-2
	
	public CardInfo(){
		
	}
	public CardInfo(String cardNo, Long cardBatchId, String cardPass) {
		super();
		this.cardNo = cardNo;
		this.cardBatchId = cardBatchId;
		this.cardPass = cardPass;
	}
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((addUser == null) ? 0 : addUser.hashCode());
		result = prime * result
				+ ((buyState == null) ? 0 : buyState.hashCode());
		result = prime * result
				+ ((cardBalance == null) ? 0 : cardBalance.hashCode());
		result = prime * result
				+ ((cardBatchId == null) ? 0 : cardBatchId.hashCode());
		result = prime * result + ((cardFee == null) ? 0 : cardFee.hashCode());
		result = prime * result + ((cardNo == null) ? 0 : cardNo.hashCode());
		result = prime * result
				+ ((cardPass == null) ? 0 : cardPass.hashCode());
		result = prime * result
				+ ((cardType == null) ? 0 : cardType.hashCode());
		result = prime * result
				+ ((editUser == null) ? 0 : editUser.hashCode());
		result = prime * result
				+ ((frozenFee == null) ? 0 : frozenFee.hashCode());
		result = prime * result
				+ ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result
				+ ((memberName == null) ? 0 : memberName.hashCode());
		result = prime * result
				+ ((nowVersion == null) ? 0 : nowVersion.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result
				+ ((prevVersion == null) ? 0 : prevVersion.hashCode());
		result = prime * result + ((saleFee == null) ? 0 : saleFee.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CardInfo other = (CardInfo) obj;
		if (addUser == null)
		{
			if (other.addUser != null)
				return false;
		}
		else if (!addUser.equals(other.addUser))
			return false;
		if (buyState == null)
		{
			if (other.buyState != null)
				return false;
		}
		else if (!buyState.equals(other.buyState))
			return false;
		if (cardBalance == null)
		{
			if (other.cardBalance != null)
				return false;
		}
		else if (!cardBalance.equals(other.cardBalance))
			return false;
		if (cardBatchId == null)
		{
			if (other.cardBatchId != null)
				return false;
		}
		else if (!cardBatchId.equals(other.cardBatchId))
			return false;
		if (cardFee == null)
		{
			if (other.cardFee != null)
				return false;
		}
		else if (!cardFee.equals(other.cardFee))
			return false;
		if (cardNo == null)
		{
			if (other.cardNo != null)
				return false;
		}
		else if (!cardNo.equals(other.cardNo))
			return false;
		if (cardPass == null)
		{
			if (other.cardPass != null)
				return false;
		}
		else if (!cardPass.equals(other.cardPass))
			return false;
		if (cardType == null)
		{
			if (other.cardType != null)
				return false;
		}
		else if (!cardType.equals(other.cardType))
			return false;
		if (editUser == null)
		{
			if (other.editUser != null)
				return false;
		}
		else if (!editUser.equals(other.editUser))
			return false;
		if (frozenFee == null)
		{
			if (other.frozenFee != null)
				return false;
		}
		else if (!frozenFee.equals(other.frozenFee))
			return false;
		if (memberId == null)
		{
			if (other.memberId != null)
				return false;
		}
		else if (!memberId.equals(other.memberId))
			return false;
		if (memberName == null)
		{
			if (other.memberName != null)
				return false;
		}
		else if (!memberName.equals(other.memberName))
			return false;
		if (nowVersion == null)
		{
			if (other.nowVersion != null)
				return false;
		}
		else if (!nowVersion.equals(other.nowVersion))
			return false;
		if (phone == null)
		{
			if (other.phone != null)
				return false;
		}
		else if (!phone.equals(other.phone))
			return false;
		if (prevVersion == null)
		{
			if (other.prevVersion != null)
				return false;
		}
		else if (!prevVersion.equals(other.prevVersion))
			return false;
		if (saleFee == null)
		{
			if (other.saleFee != null)
				return false;
		}
		else if (!saleFee.equals(other.saleFee))
			return false;
		if (state == null)
		{
			if (other.state != null)
				return false;
		}
		else if (!state.equals(other.state))
			return false;
		return true;
	}

	public String getCardNo()
	{
		return cardNo;
	}

	public void setCardNo(String cardNo)
	{
		this.cardNo = cardNo;
	}

	public Long getCardBatchId()
	{
		return cardBatchId;
	}

	public void setCardBatchId(Long cardBatchId)
	{
		this.cardBatchId = cardBatchId;
	}

	public String getCardPass()
	{
		return cardPass;
	}

	public void setCardPass(String cardPass)
	{
		this.cardPass = cardPass;
	}

	public String getCardType()
	{
		return cardType;
	}

	public void setCardType(String cardType)
	{
		this.cardType = cardType;
	}

	public BigDecimal getCardFee()
	{
		return cardFee;
	}

	public void setCardFee(BigDecimal cardFee)
	{
		this.cardFee = cardFee;
	}

	public BigDecimal getSaleFee()
	{
		return saleFee;
	}

	public void setSaleFee(BigDecimal saleFee)
	{
		this.saleFee = saleFee;
	}

	public BigDecimal getCardBalance()
	{
		return cardBalance;
	}

	public void setCardBalance(BigDecimal cardBalance)
	{
		this.cardBalance = cardBalance;
	}

	public BigDecimal getFrozenFee()
	{
		return frozenFee;
	}

	public void setFrozenFee(BigDecimal frozenFee)
	{
		this.frozenFee = frozenFee;
	}

	public String getValid()
	{
		return valid;
	}

	public void setValid(String valid)
	{
		this.valid = valid;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getMemberName()
	{
		return memberName;
	}

	public void setMemberName(String memberName)
	{
		this.memberName = memberName;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public Long getMemberId()
	{
		return memberId;
	}

	public void setMemberId(Long memberId)
	{
		this.memberId = memberId;
	}

	public String getActivationDate()
	{
		return activationDate;
	}

	public void setActivationDate(String activationDate)
	{
		this.activationDate = activationDate;
	}

	public String getSellDate()
	{
		return sellDate;
	}

	public void setSellDate(String sellDate)
	{
		this.sellDate = sellDate;
	}

	public Long getAddUser()
	{
		return addUser;
	}

	public void setAddUser(Long addUser)
	{
		this.addUser = addUser;
	}

	public Long getEditUser()
	{
		return editUser;
	}

	public void setEditUser(Long editUser)
	{
		this.editUser = editUser;
	}

	public Long getBuyState()
	{
		return buyState;
	}

	public void setBuyState(Long buyState)
	{
		this.buyState = buyState;
	}

	public Long getNowVersion()
	{
		return nowVersion;
	}

	public void setNowVersion(Long nowVersion)
	{
		this.nowVersion = nowVersion;
	}

	public Long getPrevVersion()
	{
		return prevVersion;
	}

	public void setPrevVersion(Long prevVersion)
	{
		this.prevVersion = prevVersion;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(String buyTime) {
		this.buyTime = buyTime;
	}

	public Long getCardOrder() {
		return cardOrder;
	}

	public void setCardOrder(Long cardOrder) {
		this.cardOrder = cardOrder;
	}
	
	
}