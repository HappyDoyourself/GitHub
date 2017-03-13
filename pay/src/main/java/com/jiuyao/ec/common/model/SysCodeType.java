package com.jiuyao.ec.common.model;

import cn.com.dubbo.base.bo.BaseBO;

public class SysCodeType extends BaseBO{

	private static final long serialVersionUID = 4240547754690909815L;
	
	private Integer codeTypeId;
    private String systemNo;
    private String codeTypeNo;
    private String codeTypeName;
    private Integer codeTypeOrder;
    private String codeTypeNotes;
	public Integer getCodeTypeId() {
		return codeTypeId;
	}
	public void setCodeTypeId(Integer codeTypeId) {
		this.codeTypeId = codeTypeId;
	}
	public String getSystemNo() {
		return systemNo;
	}
	public void setSystemNo(String systemNo) {
		this.systemNo = systemNo;
	}
	public String getCodeTypeNo() {
		return codeTypeNo;
	}
	public void setCodeTypeNo(String codeTypeNo) {
		this.codeTypeNo = codeTypeNo;
	}
	public String getCodeTypeName() {
		return codeTypeName;
	}
	public void setCodeTypeName(String codeTypeName) {
		this.codeTypeName = codeTypeName;
	}
	public Integer getCodeTypeOrder() {
		return codeTypeOrder;
	}
	public void setCodeTypeOrder(Integer codeTypeOrder) {
		this.codeTypeOrder = codeTypeOrder;
	}
	public String getCodeTypeNotes() {
		return codeTypeNotes;
	}
	public void setCodeTypeNotes(String codeTypeNotes) {
		this.codeTypeNotes = codeTypeNotes;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((codeTypeId == null) ? 0 : codeTypeId.hashCode());
		result = prime * result
				+ ((codeTypeName == null) ? 0 : codeTypeName.hashCode());
		result = prime * result
				+ ((codeTypeNo == null) ? 0 : codeTypeNo.hashCode());
		result = prime * result
				+ ((codeTypeNotes == null) ? 0 : codeTypeNotes.hashCode());
		result = prime * result
				+ ((codeTypeOrder == null) ? 0 : codeTypeOrder.hashCode());
		result = prime * result
				+ ((systemNo == null) ? 0 : systemNo.hashCode());
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
		SysCodeType other = (SysCodeType) obj;
		if (codeTypeId == null) {
			if (other.codeTypeId != null)
				return false;
		} else if (!codeTypeId.equals(other.codeTypeId))
			return false;
		if (codeTypeName == null) {
			if (other.codeTypeName != null)
				return false;
		} else if (!codeTypeName.equals(other.codeTypeName))
			return false;
		if (codeTypeNo == null) {
			if (other.codeTypeNo != null)
				return false;
		} else if (!codeTypeNo.equals(other.codeTypeNo))
			return false;
		if (codeTypeNotes == null) {
			if (other.codeTypeNotes != null)
				return false;
		} else if (!codeTypeNotes.equals(other.codeTypeNotes))
			return false;
		if (codeTypeOrder == null) {
			if (other.codeTypeOrder != null)
				return false;
		} else if (!codeTypeOrder.equals(other.codeTypeOrder))
			return false;
		if (systemNo == null) {
			if (other.systemNo != null)
				return false;
		} else if (!systemNo.equals(other.systemNo))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "SysCodeType [codeTypeId=" + codeTypeId + ", systemNo=" + systemNo
				+ ", codeTypeNo=" + codeTypeNo + ", codeTypeName="
				+ codeTypeName + ", codeTypeOrder=" + codeTypeOrder
				+ ", codeTypeNotes=" + codeTypeNotes + "]";
	}
	
	
}
