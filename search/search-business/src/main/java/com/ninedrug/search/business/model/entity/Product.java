package com.ninedrug.search.business.model.entity;

import java.math.BigDecimal;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 商品列表信息
 *
 */
public class Product extends BaseBean {
	
	//商品ID
	private Long productId;
	
	//商品一级分类ID
	private Long proCatalogId;
	
	//商品名称
	private String productName;
	//商品描述
	private String productDesc;
	//市场价
	private String ecPrice;
	
	//市场价
    private Double minPrice;
    
    //市场价
    private Double maxPrice;
    
	//图片路径
	private String imageUrl;
	//品牌ID
	private String packageStandard;
	 //库存
  	private Long availableStock;
  	
	//品牌名称 
	private Long productBrandId;
	
	//品牌名称 
	private String productBrandName;
	
	//处方类型；1:非处方(甲类) 2:非处方(乙类) 3:单轨处方药 5:双轨处方药 4:其他
	private String drugPrescriptionType;
	
	public Product() {
		super();
	}


	public Product(Long productId, Long proCatalogId, String productName, String productDesc, String ecPrice,
			Double minPrice, Double maxPrice, String imageUrl, String packageStandard, Long availableStock,
			Long productBrandId, String productBrandName, String drugPrescriptionType) {
		super();
		this.productId = productId;
		this.proCatalogId = proCatalogId;
		this.productName = productName;
		this.productDesc = productDesc;
		this.ecPrice = ecPrice;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.imageUrl = imageUrl;
		this.packageStandard = packageStandard;
		this.availableStock = availableStock;
		this.productBrandId = productBrandId;
		this.productBrandName = productBrandName;
		this.drugPrescriptionType = drugPrescriptionType;
	}


	public String getDrugPrescriptionType() {
		return drugPrescriptionType;
	}

	public void setDrugPrescriptionType(String drugPrescriptionType) {
		this.drugPrescriptionType = drugPrescriptionType;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public Double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Long getProCatalogId() {
		return proCatalogId;
	}

	public void setProCatalogId(Long proCatalogId) {
		this.proCatalogId = proCatalogId;
	}

	public Long getAvailableStock() {
		return availableStock;
	}

	public void setAvailableStock(Long availableStock) {
		this.availableStock = availableStock;
	}

	public Long getProductBrandId() {
		return productBrandId;
	}



	public void setProductBrandId(Long productBrandId) {
		this.productBrandId = productBrandId;
	}



	public String getPackageStandard() {
		return packageStandard;
	}

	public void setPackageStandard(String packageStandard) {
		this.packageStandard = packageStandard;
	}

	public String getProductBrandName() {
		return productBrandName;
	}

	public void setProductBrandName(String productBrandName) {
		this.productBrandName = productBrandName;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getEcPrice() {
		return ecPrice;
	}

	public void setEcPrice(String ecPrice) {
		this.ecPrice = ecPrice;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
	
	
	
}
