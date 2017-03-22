package com.ninedrug.search.business.model.entity;

/**
 * 
 * 
 * <p>Title: 商品显示分类搜索引擎索引</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author Administrator
 * @date 2016年11月1日
 */
public class ProductCategoryViewBase  extends BaseBean  {
	
    
    
     //商品一级分类ID
  	private Long baseCateId;
  	
  	//显示分类ID
  	private Long viewCateId;
  	
  	//一级分类ID
  	private Long firCatalogId;
  	
    //二级分类ID
  	private Long secCatalogId; 
    
  	//商品ID
  	private Long productId;
  	
    
  	public ProductCategoryViewBase() {
  		super();
  	}


	public ProductCategoryViewBase(Long baseCateId, Long viewCateId, Long firCatalogId, Long secCatalogId,
			Long productId) {
		super();
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
