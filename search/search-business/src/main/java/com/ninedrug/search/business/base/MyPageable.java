package com.ninedrug.search.business.base;

import java.io.Serializable;

public interface MyPageable extends Serializable {
	
	
	/**
	 * Returns the page to be returned.
	 * 
	 * @return the page to be returned.
	 */
	int getPage();

	/**
	 * 每页显示数
	 */
	int getPageSize();

	/**
	 * 当前
	 */
	int getOffset();
	
	
	
}
