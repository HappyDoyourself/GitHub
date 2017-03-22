package com.ninedrug.search.business.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ninedrug.search.business.base.BaseService;
import com.ninedrug.search.business.model.document.ProductDocument;
import com.ninedrug.search.business.model.extend.ProductModel;

public interface ProductService extends BaseService {
	
	public List<ProductModel>  getProductListPage(ProductModel productModel);
	
	
	public ProductModel  getProductByProductId(ProductModel productModel);
	
	
	/**
	 * wap 多条件组合查询
	 * 
	 * @param productModel	 * @param pageable
	 * @return
	 */
	public Page<ProductDocument>  getWapProductPageList(ProductModel productModel,Pageable pageable);
	
	
	
	/**
	 * pc 多条件组合查询
	 * 
	 * @param productModel
	 * @param pageable
	 * @return
	 */
	public Page<ProductDocument>  getPCProductPageList(ProductModel productModel,Pageable pageable);
	
	
	
	/**
	 * 查询关键词 关联的品牌列表
	 * @param productModel
	 * @return
	 */
	public List<Map<String,Object>> getBrandList(ProductModel productModel);
	
	/**
	 * 查询无库存的商品更新搜索索引
	 * @param productModel
	 * @return
	 */
	public List<ProductModel> deleteProductStock(ProductModel productModel);

	
}
