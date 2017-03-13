package cn.com.dubbo.model;

import cn.com.dubbo.base.bo.BaseBO;


/**
 * 支付手段的一些常见的支付参数
 * @author guo_zhifeng
 * 
 */
public class EcPaymentTypeParams extends BaseBO {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 6397581073985711478L;
	
	private Integer paymentTypeParamsId;//支付参数id
    private Integer paymentTypeId;//支付类型的id
    private String paramsName;//参数名
    private String paramsValue;//参数值  
    private String paramsNotes;//参数备注
    public Integer getPaymentTypeParamsId() {
        return paymentTypeParamsId;
    }
    public void setPaymentTypeParamsId(Integer paymentTypeParamsId) {
        this.paymentTypeParamsId = paymentTypeParamsId;
    }

    
    public Integer getPaymentTypeId() {
        return paymentTypeId;
    }

    
    public void setPaymentTypeId(Integer paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

   
    public String getParamsName() {
        return paramsName;
    }

    
    public void setParamsName(String paramsName) {
        this.paramsName = paramsName;
    }

   
    public String getParamsValue() {
        return paramsValue;
    }

    
    public void setParamsValue(String paramsValue) {
        this.paramsValue = paramsValue;
    }

    
    public String getParamsNotes() {
        return paramsNotes;
    }

    
    public void setParamsNotes(String paramsNotes) {
        this.paramsNotes = paramsNotes;
    }
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((paramsName == null) ? 0 : paramsName.hashCode());
		result = prime * result
				+ ((paramsNotes == null) ? 0 : paramsNotes.hashCode());
		result = prime * result
				+ ((paramsValue == null) ? 0 : paramsValue.hashCode());
		result = prime * result
				+ ((paymentTypeId == null) ? 0 : paymentTypeId.hashCode());
		result = prime
				* result
				+ ((paymentTypeParamsId == null) ? 0 : paymentTypeParamsId
						.hashCode());
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
		EcPaymentTypeParams other = (EcPaymentTypeParams) obj;
		if (paramsName == null) {
			if (other.paramsName != null)
				return false;
		} else if (!paramsName.equals(other.paramsName))
			return false;
		if (paramsNotes == null) {
			if (other.paramsNotes != null)
				return false;
		} else if (!paramsNotes.equals(other.paramsNotes))
			return false;
		if (paramsValue == null) {
			if (other.paramsValue != null)
				return false;
		} else if (!paramsValue.equals(other.paramsValue))
			return false;
		if (paymentTypeId == null) {
			if (other.paymentTypeId != null)
				return false;
		} else if (!paymentTypeId.equals(other.paymentTypeId))
			return false;
		if (paymentTypeParamsId == null) {
			if (other.paymentTypeParamsId != null)
				return false;
		} else if (!paymentTypeParamsId.equals(other.paymentTypeParamsId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "EcPaymentTypeParams [paymentTypeParamsId="
				+ paymentTypeParamsId + ", paymentTypeId=" + paymentTypeId
				+ ", paramsName=" + paramsName + ", paramsValue=" + paramsValue
				+ ", paramsNotes=" + paramsNotes + "]";
	}
    
    
}