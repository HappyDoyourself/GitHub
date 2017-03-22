package com.ninedrug.search.api.web.bean;

import java.io.Serializable;
import java.util.List;

public class PCSearchBean implements Serializable {
	
	//商品ID
  	private Long productId;
    
     //商品一级分类ID
  	private Long proCatalogId;
    
    
  	//商品名称
  	private String productName;
    
    
    //库存
  	private Long availableStock;
    
  	//商品描述
  	private String productDesc;
    
    
  	//市场价
  	private String ecPrice;
    
    
  	//图片路径
  	private String imageUrl;
    
    //包装规格
  	private String packageStandard;
    
    //品牌ID
  	private Long productBrandId;
    
  //处方类型；1:非处方(甲类) 2:非处方(乙类) 3:单轨处方药 5:双轨处方药 4:其他
  	private String drugPrescriptionType;
  	
    //品牌名称 
    private String productBrandName;
    
    //商品可使用的卡列表    
    private List<CardProductBean> suppprtCards;
    
    
    
    

	public String getDrugPrescriptionType() {
		return drugPrescriptionType;
	}


	public void setDrugPrescriptionType(String drugPrescriptionType) {
		this.drugPrescriptionType = drugPrescriptionType;
	}


	public List<CardProductBean> getSuppprtCards() {
		return suppprtCards;
	}


	public void setSuppprtCards(List<CardProductBean> suppprtCards) {
		this.suppprtCards = suppprtCards;
	}


	public Long getProductId() {
		return productId;
	}


	public void setProductId(Long productId) {
		this.productId = productId;
	}


	public Long getProCatalogId() {
		return proCatalogId;
	}


	public void setProCatalogId(Long proCatalogId) {
		this.proCatalogId = proCatalogId;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public Long getAvailableStock() {
		return availableStock;
	}


	public void setAvailableStock(Long availableStock) {
		this.availableStock = availableStock;
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


	public String getPackageStandard() {
		return packageStandard;
	}


	public void setPackageStandard(String packageStandard) {
		this.packageStandard = packageStandard;
	}


	public Long getProductBrandId() {
		return productBrandId;
	}


	public void setProductBrandId(Long productBrandId) {
		this.productBrandId = productBrandId;
	}


	public String getProductBrandName() {
		return productBrandName;
	}


	public void setProductBrandName(String productBrandName) {
		this.productBrandName = productBrandName;
	}
  	
    
	
}
