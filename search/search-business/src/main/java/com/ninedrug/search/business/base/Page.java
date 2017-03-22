package com.ninedrug.search.business.base;

import java.io.Serializable;

public interface Page<T> extends Serializable {
	
	
	/**
	 * 总页数
	 */
	int getTotalPages();

	/**
	 * 总条数
	 */
	long getTotalElements();
	
}
