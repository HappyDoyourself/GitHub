package com.ninedrug.search.business.base;

import java.io.Serializable;


public class PageRequest<T> implements Pageable<T>, Serializable {
	
	private static final long serialVersionUID = -4541509938956089562L;
	
	private int page = 1;
	private int size = 20;
	private T params;
	
	
	public PageRequest(int page, int size) {
		this(page,  size,  null);
	}
	
	
	public PageRequest(int page, int size, T params) {
		this.page = page;
		this.size = size;
		this.params = params;
	}

	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setParams(T params) {
		this.params = params;
	}

	public T getParams() {
		return this.params;
	}
	
	public int getPageNumber() {
		return this.page;
	}
	
	public int getPageSize() {
		return this.size;
	}
	
	public int getOffset() {
		return (this.page - 1) * this.size;
	}
	
}
