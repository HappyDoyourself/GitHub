package com.ninedrug.search.business.model.extend;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ninedrug.search.business.model.entity.Product;

public class ProductModel extends Product {
	
	/**
	 * 条数
	 */
	private int size;
	/**
	 * 页数
	 */
	private int page;
	
	private int startLimit;
	
	private int endLimit;
	
	//搜索关键词
	private String k;
	//搜索多个品牌   123,1234多个是逗号隔开的
	private String b;
	
	/**
	 * 多个商品id 用 ，号隔开
	 */
	private String productIdsStr;
	
	
	private List<Long> proCatalogIds;
	
	
	private List<Long> productBrandIds;
	
	
	private List<Long> productIds;
	
	//优惠券ID关联的 商品ID
	private List<Long> couponProductIds;
	
	/**
	 * 品牌名
	 */
	private List<String> productBrandNames;
	
	//优惠券类型  使用范围类型(1-全部,2-商品分类,3-具体商品) 
	private String applyRange;
	
	
	
	public String getApplyRange() {
		return applyRange;
	}


	public void setApplyRange(String applyRange) {
		this.applyRange = applyRange;
	}


	public List<Long> getCouponProductIds() {
		return couponProductIds;
	}


	public void setCouponProductIds(List<Long> couponProductIds) {
		this.couponProductIds = couponProductIds;
	}


	public List<Long> getProductIds() {
		return productIds;
	}


	public void setProductIds(List<Long> productIds) {
		this.productIds = productIds;
	}


	public List<String> getProductBrandNames() {
		return productBrandNames;
	}


	public void setProductBrandNames(List<String> productBrandNames) {
		this.productBrandNames = productBrandNames;
	}


	public List<Long> getProductBrandIds() {
		return productBrandIds;
	}


	public void setProductBrandIds(List<Long> productBrandIds) {
		this.productBrandIds = productBrandIds;
	}


	public String getProductIdsStr() {
		return productIdsStr;
	}


	public void setProductIdsStr(String productIdsStr) {
		this.productIdsStr = productIdsStr;
	}
	
	public List<Long> getProCatalogIds() {
		return proCatalogIds;
	}


	public void setProCatalogIds(List<Long> proCatalogIds) {
		this.proCatalogIds = proCatalogIds;
	}


	public String getK() {
		return k;
	}


	public void setK(String k) {
		if(!StringUtils.isEmpty(k)){
			setProductName(k); 
		}
		this.k = k;
	}


	public String getB() {
		return b;
	}


	public void setB(String b) {
		this.b = b;
	}


	public int getStartLimit() {
		return startLimit;
	}


	public void setStartLimit(int startLimit) {
		this.startLimit = startLimit;
	}


	public int getEndLimit() {
		return endLimit;
	}


	public void setEndLimit(int endLimit) {
		this.endLimit = endLimit;
	}


	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.endLimit = size;
		this.size = size;
	}


	public int getPage() {
		return page;
	}


	public void setPage(int page) {
		this.startLimit =( page - 1 ) * this.size;
		this.page = page;
	}
	
	
	
	
}
