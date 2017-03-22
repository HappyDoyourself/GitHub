package com.ninedrug.search.business.model.extend;

import java.util.List;

import com.ninedrug.search.business.model.entity.ProductCategoryViewBase;

public class ProductCategoryViewBaseModel extends ProductCategoryViewBase {
	
	
	
	private String viewCateIdsStr;

	
	private List<Long> viewCateIds;
	

	public String getViewCateIdsStr() {
		return viewCateIdsStr;
	}

	public void setViewCateIdsStr(String viewCateIdsStr) {
		this.viewCateIdsStr = viewCateIdsStr;
	}

	public List<Long> getViewCateIds() {
		return viewCateIds;
	}

	public void setViewCateIds(List<Long> viewCateIds) {
		this.viewCateIds = viewCateIds;
	}
	
	
	
	
	
	
}

