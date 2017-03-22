package com.ninedrug.search.business.model.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.ninedrug.search.business.model.entity.BaseBean;


/**
 * 
 * 
 * <p>Title: 商品搜索引擎索引</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author Administrator
 * @date 2016年11月1日
 */
@Document(indexName= "product", type= "product", shards = 1, replicas = 0, refreshInterval = "-1")
public class ProductDocument  extends BaseBean  {
	
	
    @Id
    private String id;
    
//    @Version
//    private Long version;
    
    //商品ID
    @Field(type=FieldType.Long)
  	private Long productId;
    
     //商品一级分类ID
    @Field(type=FieldType.Long)
  	private Long proCatalogId;
    
    
  	//商品名称
    @Field(type=FieldType.String)
  	private String productName;
    
    
    //库存
    @Field(type=FieldType.Long)
  	private Long availableStock;
    
  	//商品描述
    @Field(type=FieldType.String)
  	private String productDesc;
    
    
  	//市场价
    @Field(type=FieldType.Double)
  	private String ecPrice;
    
    
  	//图片路径
    @Field(type=FieldType.String)
  	private String imageUrl;
    
    //包装规格
    @Field(type=FieldType.String)
  	private String packageStandard;
    
    //品牌ID
    @Field(type=FieldType.Long)
  	private Long productBrandId;
    
    //品牌ID  聚合用
    @Field(type=FieldType.Long)
  	private Long productBrandId2;
    
    //品牌名称 
  	@Field(type=FieldType.String,index = FieldIndex.not_analyzed)
    private String productBrandName;
  	
  	//品牌名称   聚合用
	@Field(type=FieldType.String,index = FieldIndex.not_analyzed)
  	private String productBrandName2;
    
	//品牌名称
	@Field(type=FieldType.String)
	private String productSearchStr;
	
	//处方类型；1:非处方(甲类) 2:非处方(乙类) 3:单轨处方药 5:双轨处方药 4:其他
	@Field(type=FieldType.String)
	private String drugPrescriptionType;
	
	public ProductDocument() {
		
	}


	public ProductDocument(String id, Long productId, Long proCatalogId, String productName, Long availableStock,
			String productDesc, String ecPrice, String imageUrl, String packageStandard, Long productBrandId,
			Long productBrandId2, String productBrandName, String productBrandName2, String productSearchStr,
			String drugPrescriptionType) {
		super();
		this.id = id;
		this.productId = productId;
		this.proCatalogId = proCatalogId;
		this.productName = productName;
		this.availableStock = availableStock;
		this.productDesc = productDesc;
		this.ecPrice = ecPrice;
		this.imageUrl = imageUrl;
		this.packageStandard = packageStandard;
		this.productBrandId = productBrandId;
		this.productBrandId2 = productBrandId2;
		this.productBrandName = productBrandName;
		this.productBrandName2 = productBrandName2;
		this.productSearchStr = productSearchStr;
		this.drugPrescriptionType = drugPrescriptionType;
	}


	public String getDrugPrescriptionType() {
		return drugPrescriptionType;
	}


	public void setDrugPrescriptionType(String drugPrescriptionType) {
		this.drugPrescriptionType = drugPrescriptionType;
	}


	public String getProductSearchStr() {
		this.productSearchStr = this.productName + " " + this.productDesc;
		return productSearchStr;
	}


	public void setProductSearchStr(String productSearchStr) {
		this.productSearchStr = productSearchStr;
	}


	public Long getProductBrandId2() {
		return productBrandId2;
	}


	public void setProductBrandId2(Long productBrandId2) {
		this.productBrandId2 = productBrandId2;
	}

	public String getProductBrandName2() {
		return productBrandName2;
	}


	public void setProductBrandName2(String productBrandName2) {
		this.productBrandName2 = productBrandName2;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.setId(productId.toString());
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
//		BigDecimal  b = new BigDecimal(ecPrice);  
//		this.ecPrice = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		this.ecPrice = ecPrice;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
    
    
    
}
