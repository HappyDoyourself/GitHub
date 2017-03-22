package com.ninedrug.search.business.model.extend;

import java.util.List;

import com.ninedrug.search.business.model.entity.CardProduct;

public class CardProductModel extends CardProduct {
	
	
	private List<Long> productIds;
	
	private String productIdsStr;

	public List<Long> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<Long> productIds) {
		this.productIds = productIds;
	}

	public String getProductIdsStr() {
		return productIdsStr;
	}

	public void setProductIdsStr(String productIdsStr) {
		this.productIdsStr = productIdsStr;
	}

	
}
