package com.ninedrug.search.business.service;

import java.util.List;

import com.ninedrug.search.business.model.extend.ViewCategoryModel;

public interface ViewCategoryModelService {
	
	
	/**
	 * 根据ID查询显示分类
	 * 
	 * @param viewCategoryModel
	 * @return
	 */
	public ViewCategoryModel queryViewCategoryById(ViewCategoryModel viewCategoryModel);
	
	
	/**
	 * 根据父级ID查询显示分类
	 * 
	 * @param viewCategoryModel
	 * @return
	 */
	public List<ViewCategoryModel> queryViewCategoryByParentId(ViewCategoryModel viewCategoryModel);
	
	
	
	/**
	 * 根据显示分类查询关联的产品ID
	 * 
	 * @param viewCategoryModel
	 * @return
	 * 
	 */
	public List<Long> queryProductIdByCategory(ViewCategoryModel viewCategoryModel);
}
