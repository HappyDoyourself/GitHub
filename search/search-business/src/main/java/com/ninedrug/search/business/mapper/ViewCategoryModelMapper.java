package com.ninedrug.search.business.mapper;

import java.util.List;

import com.ninedrug.search.business.model.extend.ViewCategoryModel;

public interface ViewCategoryModelMapper {

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
	
	
}
