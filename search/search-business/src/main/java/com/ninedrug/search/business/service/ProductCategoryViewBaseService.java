package com.ninedrug.search.business.service;

import java.util.List;

import com.ninedrug.search.business.base.BaseService;
import com.ninedrug.search.business.model.extend.ProductCategoryViewBaseModel;

public interface ProductCategoryViewBaseService extends BaseService {
	
	public List<ProductCategoryViewBaseModel>  getProductCategoryViewBaseListPage(
			int page ,int size,
			ProductCategoryViewBaseModel productCategoryViewBaseModel);
	
	
	public List<ProductCategoryViewBaseModel>  getByViewCategoryId(Long viewCateId);
	
}
