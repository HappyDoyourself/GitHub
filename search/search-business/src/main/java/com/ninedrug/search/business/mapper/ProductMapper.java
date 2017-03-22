package com.ninedrug.search.business.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.web.PageableDefault;

import com.ninedrug.search.business.base.Pageable;
import com.ninedrug.search.business.model.extend.ProductCategoryViewBaseModel;
import com.ninedrug.search.business.model.extend.ProductModel;

public interface ProductMapper {
	
	/**
	 * 查询所有的
	 * @param productModel
	 * @return
	 */
	public List<ProductModel> getProductListPage(ProductModel productModel);
	
	/**
	 * 查询所有的
	 * @param productModel
	 * @return
	 */
	public ProductModel getProductByProductId(ProductModel productModel);
	
	/**
	 * 查询所有的
	 * @param productModel
	 * @return
	 */
	public List<ProductModel> queryProductViewCategory(ProductModel productModel);
	
	/**
	 * 查询所有的移动端口显示分类列表
	 * @param productModel
	 * @return
	 */
	public List<ProductCategoryViewBaseModel> queryWapProductViewCategoryList(Pageable<ProductCategoryViewBaseModel> pageable);
	
	/**
	 * 查询无库存的商品更新搜索索引
	 * @param productModel
	 * @return
	 */
	public List<ProductModel> deleteProductStock(ProductModel productModel);
	
	/**
	 * 查询所有的移动端口显示分类列表
	 * @param productModel
	 * @return
	 */
	public List<ProductCategoryViewBaseModel> queryPCProductViewCategoryList(Pageable<ProductCategoryViewBaseModel> pageable);
	
	
	
	
	
	
}
