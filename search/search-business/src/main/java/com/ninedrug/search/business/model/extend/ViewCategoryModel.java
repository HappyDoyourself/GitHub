package com.ninedrug.search.business.model.extend;

import java.util.List;

import com.ninedrug.search.business.model.entity.ViewCategory;


/**
 * 
 * 商品显示分类扩展类
 * 
 */

@SuppressWarnings("serial")
public class ViewCategoryModel extends ViewCategory {

	
	/**
	 * 分类ID
	 */
	private List<Long> catalogIds;

	public List<Long> getCatalogIds() {
		return catalogIds;
	}

	public void setCatalogIds(List<Long> catalogIds) {
		this.catalogIds = catalogIds;
	}
	
	
}
