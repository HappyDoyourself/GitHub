package cn.com.dubbo.model;

import cn.com.dubbo.base.bo.BaseBO;


public class OrderPaymentMessageLog extends BaseBO {

	private static final long serialVersionUID = -1662546421023191099L;
	private Long messageId;
	private Long paymentLogId;
	private String messageType;
	private String responseType;
	private String message;

	@Override
	public String toString() {
		return "OrderPaymentMessageLog [messageId=" + messageId
				+ ", paymentLogId=" + paymentLogId + ", messageType="
				+ messageType + ", responseType=" + responseType + ", message="
				+ message + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((messageId == null) ? 0 : messageId.hashCode());
		result = prime * result
				+ ((paymentLogId == null) ? 0 : paymentLogId.hashCode());
		result = prime * result
				+ ((messageType == null) ? 0 : messageType.hashCode());
		result = prime * result
				+ ((responseType == null) ? 0 : responseType.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());

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
		OrderPaymentMessageLog other = (OrderPaymentMessageLog) obj;
		if (messageId == null) {
			if (other.messageId != null)
				return false;
		} else if (!messageId.equals(other.messageId))
			return false;
		if (paymentLogId == null) {
			if (other.paymentLogId != null)
				return false;
		} else if (!paymentLogId.equals(other.paymentLogId))
			return false;
		if (messageType == null) {
			if (other.messageType != null)
				return false;
		} else if (!messageType.equals(other.messageType))
			return false;
		if (responseType == null) {
			if (other.responseType != null)
				return false;
		} else if (!responseType.equals(other.responseType))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public Long getPaymentLogId() {
		return paymentLogId;
	}

	public void setPaymentLogId(Long paymentLogId) {
		this.paymentLogId = paymentLogId;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}