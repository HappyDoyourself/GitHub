package com.ninedrug.search.business.model.extend;

public class PCViewCategoryModel {

	/** 显示分类ID **/
	private Long id;

	/** 显示分类名称 **/
	private String cateName;

	/** 显示分类级别(1: 一级, 2: 二级, 3: 三级) **/
	private Long cateLevel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCateLevel() {
		return cateLevel;
	}

	public void setCateLevel(Long cateLevel) {
		this.cateLevel = cateLevel;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

}
