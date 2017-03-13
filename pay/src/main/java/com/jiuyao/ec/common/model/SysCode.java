package com.jiuyao.ec.common.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.type.TypeReference;

import org.codehaus.jackson.type.TypeReference;

import cn.com.dubbo.base.bo.BaseBO;
import cn.com.jiuyao.pay.common.util.StringUtil;

public class SysCode extends BaseBO{

	private static final long serialVersionUID = 3896510649410449638L;
	
	private Integer codeId;
    private String codeTypeNo;
    private Integer codeOrder;
    private String codeNo;
    private String codeValue;
    private String codeNotes;
	
	public SysCode() {
		super();
	}

	public SysCode(String codeTypeNo) {
		super();
		this.codeTypeNo = codeTypeNo;
	}

	public SysCode(String codeTypeNo, String codeNo) {
		super();
		this.codeTypeNo = codeTypeNo;
		this.codeNo = codeNo;
	}

	//编码类型数组
    private String[] typeNoList;
    private String[] noList;
   	
	private String jasonContent;
	
	public String getJasonContent() {
		return jasonContent;
	}
	
	public void setJasonContent(String jasonContent) {
		if(StringUtil.isNotEmpty(jasonContent)){
			jasonContent = jasonContent.replace("\n","");
			jasonContent = jasonContent.replace("\r","");
			this.jasonContent = jasonContent;
		}
	}
	
	public List<SysCode> getCodeListFromJson(){
		List<SysCode> codeList = new ArrayList<SysCode>();
		if(StringUtil.isNotEmpty(this.jasonContent)){
			try{
				ObjectMapper om = new ObjectMapper();
				codeList = om.readValue(getJasonContent(), new TypeReference<List<SysCode>>(){});
				
			}catch(Exception e){
				
			}
		}
		return codeList;
	}

	public Integer getCodeId() {
		return codeId;
	}

	public void setCodeId(Integer codeId) {
		this.codeId = codeId;
	}

	public String getCodeTypeNo() {
		return codeTypeNo;
	}

	public void setCodeTypeNo(String codeTypeNo) {
		this.codeTypeNo = codeTypeNo;
	}

	public Integer getCodeOrder() {
		return codeOrder;
	}

	public void setCodeOrder(Integer codeOrder) {
		this.codeOrder = codeOrder;
	}

	public String getCodeNo() {
		return codeNo;
	}

	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String getCodeNotes() {
		return codeNotes;
	}

	public void setCodeNotes(String codeNotes) {
		this.codeNotes = codeNotes;
	}

	public String[] getTypeNoList() {
		return typeNoList;
	}

	public void setTypeNoList(String[] typeNoList) {
		this.typeNoList = typeNoList;
	}

	public String[] getNoList() {
		return noList;
	}

	public void setNoList(String[] noList) {
		this.noList = noList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codeId == null) ? 0 : codeId.hashCode());
		result = prime * result + ((codeNo == null) ? 0 : codeNo.hashCode());
		result = prime * result
				+ ((codeNotes == null) ? 0 : codeNotes.hashCode());
		result = prime * result
				+ ((codeOrder == null) ? 0 : codeOrder.hashCode());
		result = prime * result
				+ ((codeTypeNo == null) ? 0 : codeTypeNo.hashCode());
		result = prime * result
				+ ((codeValue == null) ? 0 : codeValue.hashCode());
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
		SysCode other = (SysCode) obj;
		if (codeId == null) {
			if (other.codeId != null)
				return false;
		} else if (!codeId.equals(other.codeId))
			return false;
		if (codeNo == null) {
			if (other.codeNo != null)
				return false;
		} else if (!codeNo.equals(other.codeNo))
			return false;
		if (codeNotes == null) {
			if (other.codeNotes != null)
				return false;
		} else if (!codeNotes.equals(other.codeNotes))
			return false;
		if (codeOrder == null) {
			if (other.codeOrder != null)
				return false;
		} else if (!codeOrder.equals(other.codeOrder))
			return false;
		if (codeTypeNo == null) {
			if (other.codeTypeNo != null)
				return false;
		} else if (!codeTypeNo.equals(other.codeTypeNo))
			return false;
		if (codeValue == null) {
			if (other.codeValue != null)
				return false;
		} else if (!codeValue.equals(other.codeValue))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SysCode [codeId=" + codeId + ", codeTypeNo=" + codeTypeNo
				+ ", codeOrder=" + codeOrder + ", codeNo=" + codeNo
				+ ", codeValue=" + codeValue + ", codeNotes=" + codeNotes + "]";
	}
	
	
}
