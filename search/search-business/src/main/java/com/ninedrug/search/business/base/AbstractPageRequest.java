package com.ninedrug.search.business.base;

import java.io.Serializable;


public abstract class AbstractPageRequest<T> implements Pageable<T> , Serializable{
	
	private static final long serialVersionUID = 1232825578694716871L;
	
	private int page = 1;
	private int size = 20;
	
	
	public AbstractPageRequest(int page, int size) {

		if (page < 1) {
			throw new IllegalArgumentException("Page index must not be less than zero!");
		}

		if (size < 1) {
			throw new IllegalArgumentException("Page size must not be less than one!");
		}

		this.page = page;
		this.size = size;
	}
	
	
	public int getPageSize() {
		return size;
	}
	
	public int getPageNumber() {
		return page;
	}
	
	public int getOffset() {
		return (page - 1) * size;
	}
	
	public boolean hasPrevious() {
		return page > 0;
	}
	
	
	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;

		result = prime * result + page;
		result = prime * result + size;

		return result;
	}
	
	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		AbstractPageRequest other = (AbstractPageRequest) obj;
		return this.page == other.page && this.size == other.size;
	}
	
	
}
