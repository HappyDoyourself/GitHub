package com.ninedrug.search.business.base;

public interface Pageable<T> {
	
	
	/**
	 * 页码
	 */
	int getPageNumber();

	/**
	 * 每页显示条数
	 */
	int getPageSize();

	/**
	 * 偏移值 = 页码  * 每页显示条数
	 */	
	int getOffset();

	
	T getParams();
}
