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
 * <p>Title: 商品分类搜索引擎索引</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author Administrator
 * @date 2016年11月1日
 */
@Document(indexName= "productcategoryviewbase", type= "productcategoryviewbase", shards = 1, replicas = 0, refreshInterval = "-1")
public class ProductCategoryViewBaseDocument  extends BaseBean  {
	
	
    @Id
    private String id;
    
    
     //商品三级分类ID
    @Field(type=FieldType.Long)
  	private Long baseCateId;
    
    
    //显示分类ID
    @Field(type=FieldType.Long)
  	private Long viewCateId;

    //一级分类ID
    @Field(type=FieldType.Long)
  	private Long firCatalogId;
  	
    //二级分类ID
    @Field(type=FieldType.Long)
  	private Long secCatalogId; 
    
  	//商品ID
    @Field(type=FieldType.Long)
  	private Long productId;
    
    
    public ProductCategoryViewBaseDocument() {
    	super();
    }
		
	public ProductCategoryViewBaseDocument(String id, Long baseCateId, Long viewCateId, Long firCatalogId,
			Long secCatalogId, Long productId) {
		super();
		this.id = id;
		this.baseCateId = baseCateId;
		this.viewCateId = viewCateId;
		this.firCatalogId = firCatalogId;
		this.secCatalogId = secCatalogId;
		this.productId = productId;
	}



	public Long getFirCatalogId() {
		return firCatalogId;
	}



	public void setFirCatalogId(Long firCatalogId) {
		this.firCatalogId = firCatalogId;
	}



	public Long getSecCatalogId() {
		return secCatalogId;
	}



	public void setSecCatalogId(Long secCatalogId) {
		this.secCatalogId = secCatalogId;
	}



	public Long getProductId() {
		return productId;
	}



	public void setProductId(Long productId) {
		this.productId = productId;
	}



	public String getId() {
		this.id = viewCateId +"_"+ productId;
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public Long getBaseCateId() {
		return baseCateId;
	}



	public void setBaseCateId(Long baseCateId) {
		this.baseCateId = baseCateId;
	}



	public Long getViewCateId() {
		return viewCateId;
	}



	public void setViewCateId(Long viewCateId) {
		this.viewCateId = viewCateId;
	}
    

    
    
    
}
