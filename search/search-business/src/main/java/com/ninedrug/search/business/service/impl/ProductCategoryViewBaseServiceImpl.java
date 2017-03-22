package com.ninedrug.search.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ninedrug.search.business.base.BaseServiceImpl;
import com.ninedrug.search.business.base.PageRequest;
import com.ninedrug.search.business.base.Pageable;
import com.ninedrug.search.business.mapper.ProductMapper;
import com.ninedrug.search.business.model.extend.ProductCategoryViewBaseModel;
import com.ninedrug.search.business.service.ProductCategoryViewBaseService;

@Service
public class ProductCategoryViewBaseServiceImpl extends BaseServiceImpl implements ProductCategoryViewBaseService {
	
	
	@Autowired
	ProductMapper productMapper;  
	
	
	public List<ProductCategoryViewBaseModel>  getProductCategoryViewBaseListPage(
			int page ,int size,
			ProductCategoryViewBaseModel productCategoryViewBaseModel){
		Pageable<ProductCategoryViewBaseModel> pageable =new PageRequest<ProductCategoryViewBaseModel>(page, size, productCategoryViewBaseModel);
		return productMapper.queryWapProductViewCategoryList(pageable);
	}
	
	
	public List<ProductCategoryViewBaseModel>  getByViewCategoryId(Long viewCateId){
		ProductCategoryViewBaseModel productCategoryViewBaseModel = new ProductCategoryViewBaseModel();
		productCategoryViewBaseModel.setViewCateId(viewCateId);
		
		Pageable<ProductCategoryViewBaseModel> pageable =new PageRequest<ProductCategoryViewBaseModel>(1, 1000, productCategoryViewBaseModel);
		
		return productMapper.queryWapProductViewCategoryList(pageable);
	}




	
}
