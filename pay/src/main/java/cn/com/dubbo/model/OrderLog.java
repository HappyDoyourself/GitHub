package cn.com.dubbo.model;

import cn.com.dubbo.base.bo.BaseBO;

public class OrderLog extends BaseBO{
	
	private static final long serialVersionUID = 6454292184338231454L;
	
	private Long orderLogId;
    private String orderId;
    private Integer orderStateId;
    private String logContent;
    private String logTime;
    private Integer logUserId;
// #orderId#, #orderStateId#, #logContent#, #logTime#, #logUserId#,   #addUserId#, #addTime#, #editUserId#, #editTime#,#orderLogType# 
    private String logUserName;
    private String memberLoginName;
    private String memberId;
    private String memberRealName;
    private String orderStateName;
    private String orderLogType;
    
    public String getOrderLogType() {
		return orderLogType;
	}

	public void setOrderLogType(String orderLogType) {
		this.orderLogType = orderLogType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getOrderLogId() {
        return orderLogId;
    }

    public void setOrderLogId(Long orderLogId) {
        this.orderLogId = orderLogId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderStateId() {
        return orderStateId;
    }

    public void setOrderStateId(Integer orderStateId) {
        this.orderStateId = orderStateId;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public Integer getLogUserId() {
        return logUserId;
    }

    public void setLogUserId(Integer logUserId) {
        this.logUserId = logUserId;
    }

	public String getLogUserName() {
		return logUserName;
	}

	public void setLogUserName(String logUserName) {
		this.logUserName = logUserName;
	}

	public void setMemberLoginName(String memberLoginName) {
		this.memberLoginName = memberLoginName;
	}

	public String getMemberLoginName() {
		return memberLoginName;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberRealName(String memberRealName) {
		this.memberRealName = memberRealName;
	}

	public String getMemberRealName() {
		return memberRealName;
	}

	public void setOrderStateName(String orderStateName) {
		this.orderStateName = orderStateName;
	}

	public String getOrderStateName() {
		return orderStateName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((logContent == null) ? 0 : logContent.hashCode());
		result = prime * result + ((logTime == null) ? 0 : logTime.hashCode());
		result = prime * result
				+ ((logUserId == null) ? 0 : logUserId.hashCode());
		result = prime * result
				+ ((logUserName == null) ? 0 : logUserName.hashCode());
		result = prime * result
				+ ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result
				+ ((memberLoginName == null) ? 0 : memberLoginName.hashCode());
		result = prime * result
				+ ((memberRealName == null) ? 0 : memberRealName.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result
				+ ((orderLogId == null) ? 0 : orderLogId.hashCode());
		result = prime * result
				+ ((orderLogType == null) ? 0 : orderLogType.hashCode());
		result = prime * result
				+ ((orderStateId == null) ? 0 : orderStateId.hashCode());
		result = prime * result
				+ ((orderStateName == null) ? 0 : orderStateName.hashCode());
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
		OrderLog other = (OrderLog) obj;
		if (logContent == null) {
			if (other.logContent != null)
				return false;
		} else if (!logContent.equals(other.logContent))
			return false;
		if (logTime == null) {
			if (other.logTime != null)
				return false;
		} else if (!logTime.equals(other.logTime))
			return false;
		if (logUserId == null) {
			if (other.logUserId != null)
				return false;
		} else if (!logUserId.equals(other.logUserId))
			return false;
		if (logUserName == null) {
			if (other.logUserName != null)
				return false;
		} else if (!logUserName.equals(other.logUserName))
			return false;
		if (memberId == null) {
			if (other.memberId != null)
				return false;
		} else if (!memberId.equals(other.memberId))
			return false;
		if (memberLoginName == null) {
			if (other.memberLoginName != null)
				return false;
		} else if (!memberLoginName.equals(other.memberLoginName))
			return false;
		if (memberRealName == null) {
			if (other.memberRealName != null)
				return false;
		} else if (!memberRealName.equals(other.memberRealName))
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (orderLogId == null) {
			if (other.orderLogId != null)
				return false;
		} else if (!orderLogId.equals(other.orderLogId))
			return false;
		if (orderLogType == null) {
			if (other.orderLogType != null)
				return false;
		} else if (!orderLogType.equals(other.orderLogType))
			return false;
		if (orderStateId == null) {
			if (other.orderStateId != null)
				return false;
		} else if (!orderStateId.equals(other.orderStateId))
			return false;
		if (orderStateName == null) {
			if (other.orderStateName != null)
				return false;
		} else if (!orderStateName.equals(other.orderStateName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderLog [orderLogId=" + orderLogId + ", orderId=" + orderId
				+ ", orderStateId=" + orderStateId + ", logContent="
				+ logContent + ", logTime=" + logTime + ", logUserId="
				+ logUserId + ", logUserName=" + logUserName
				+ ", memberLoginName=" + memberLoginName + ", memberId="
				+ memberId + ", memberRealName=" + memberRealName
				+ ", orderStateName=" + orderStateName + ", orderLogType="
				+ orderLogType + "]";
	}

}