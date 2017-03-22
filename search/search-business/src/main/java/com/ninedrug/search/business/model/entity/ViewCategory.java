package com.ninedrug.search.business.model.entity;

/**
 * 
 * 商品显示分类
 * 
 */
@SuppressWarnings("serial")
public class ViewCategory extends BaseBean {

	/**显示分类ID**/
	 private Long id;

	/**所属平台(1: PC，2: APP)**/
	 private Long platform;

	/**上级分类ID**/
	 private Long parentId;

	/**所有上级分类IDS**/
	 private String parentIds;

	/**显示分类名称**/
	 private String cateName;

	/**显示分类级别(1: 一级, 2: 二级, 3: 三级)**/
	 private Long cateLevel;

	/**分类顺序**/
	 private Long cateOrder;

	/**分类图标URL**/
	 private String cateIconUrl;

	/**分类类型(1: 关联基础分类，2: 链接)**/
	 private Long cateType;

	/**分类链接URL(仅分类类型为2时有效)**/
	 private String cateLinkUrl;

	/**添加人**/
	 private Long addUserId;

	/**修改人**/
	 private Long editUserId;



	public void setId(Long id){
		this.id = id;
	}

	public Long getId(){
		return this.id;
	}

	public void setPlatform(Long platform){
		this.platform = platform;
	}

	public Long getPlatform(){
		return this.platform;
	}

	public void setParentId(Long parentId){
		this.parentId = parentId;
	}

	public Long getParentId(){
		return this.parentId;
	}

	public void setParentIds(String parentIds){
		this.parentIds = parentIds;
	}

	public String getParentIds(){
		return this.parentIds;
	}

	public void setCateName(String cateName){
		this.cateName = cateName;
	}

	public String getCateName(){
		return this.cateName;
	}

	public void setCateLevel(Long cateLevel){
		this.cateLevel = cateLevel;
	}

	public Long getCateLevel(){
		return this.cateLevel;
	}

	public void setCateOrder(Long cateOrder){
		this.cateOrder = cateOrder;
	}

	public Long getCateOrder(){
		return this.cateOrder;
	}

	public void setCateIconUrl(String cateIconUrl){
		this.cateIconUrl = cateIconUrl;
	}

	public String getCateIconUrl(){
		return this.cateIconUrl;
	}

	public void setCateType(Long cateType){
		this.cateType = cateType;
	}

	public Long getCateType(){
		return this.cateType;
	}

	public void setCateLinkUrl(String cateLinkUrl){
		this.cateLinkUrl = cateLinkUrl;
	}

	public String getCateLinkUrl(){
		return this.cateLinkUrl;
	}

	public void setAddUserId(Long addUserId){
		this.addUserId = addUserId;
	}

	public Long getAddUserId(){
		return this.addUserId;
	}

	public void setEditUserId(Long editUserId){
		this.editUserId = editUserId;
	}

	public Long getEditUserId(){
		return this.editUserId;
	}

}
