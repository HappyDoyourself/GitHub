package cn.com.dubbo.base.bo;

import java.io.Serializable;

public class BaseBO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer addUserId;
	private String addTime;
	private Integer editUserId;
	private String editTime;
	private String isDelete;
	
	private Integer startRow;
	private Integer endRow;
	private String orderBy;
	
	public Integer getAddUserId() {
		return addUserId;
	}
	public void setAddUserId(Integer addUserId) {
		this.addUserId = addUserId;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public Integer getEditUserId() {
		return editUserId;
	}
	public void setEditUserId(Integer editUserId) {
		this.editUserId = editUserId;
	}
	public String getEditTime() {
		return editTime;
	}
	public void setEditTime(String editTime) {
		this.editTime = editTime;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	
	public Integer getStartRow() {
		return startRow;
	}
	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}
	public Integer getEndRow() {
		return endRow;
	}
	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addTime == null) ? 0 : addTime.hashCode());
		result = prime * result
				+ ((addUserId == null) ? 0 : addUserId.hashCode());
		result = prime * result
				+ ((editTime == null) ? 0 : editTime.hashCode());
		result = prime * result
				+ ((editUserId == null) ? 0 : editUserId.hashCode());
		result = prime * result + ((endRow == null) ? 0 : endRow.hashCode());
		result = prime * result
				+ ((isDelete == null) ? 0 : isDelete.hashCode());
		result = prime * result + ((orderBy == null) ? 0 : orderBy.hashCode());
		result = prime * result
				+ ((startRow == null) ? 0 : startRow.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseBO other = (BaseBO) obj;
		if (addTime == null) {
			if (other.addTime != null)
				return false;
		} else if (!addTime.equals(other.addTime))
			return false;
		if (addUserId == null) {
			if (other.addUserId != null)
				return false;
		} else if (!addUserId.equals(other.addUserId))
			return false;
		if (editTime == null) {
			if (other.editTime != null)
				return false;
		} else if (!editTime.equals(other.editTime))
			return false;
		if (editUserId == null) {
			if (other.editUserId != null)
				return false;
		} else if (!editUserId.equals(other.editUserId))
			return false;
		if (endRow == null) {
			if (other.endRow != null)
				return false;
		} else if (!endRow.equals(other.endRow))
			return false;
		if (isDelete == null) {
			if (other.isDelete != null)
				return false;
		} else if (!isDelete.equals(other.isDelete))
			return false;
		if (orderBy == null) {
			if (other.orderBy != null)
				return false;
		} else if (!orderBy.equals(other.orderBy))
			return false;
		if (startRow == null) {
			if (other.startRow != null)
				return false;
		} else if (!startRow.equals(other.startRow))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BaseBO [addUserId=" + addUserId + ", addTime=" + addTime
				+ ", editUserId=" + editUserId + ", editTime=" + editTime
				+ ", isDelete=" + isDelete + ", startRow=" + startRow
				+ ", endRow=" + endRow + ", orderBy=" + orderBy + "]";
	}

}
