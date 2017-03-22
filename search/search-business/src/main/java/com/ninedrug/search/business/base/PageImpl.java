package com.ninedrug.search.business.base;

import java.util.ArrayList;
import java.util.List;

public class PageImpl<T> implements Page<T> {
	
	
	private static final long serialVersionUID = 867755909294344406L;
	
	private List<T> content = new ArrayList<T>();
	private long total;
	private Pageable<T> pageable;
	
	
	
	public PageImpl(){}
	
	
	public PageImpl(List<T> content, long total, Pageable<T> pageable) {
		this.content = content;
		this.total = total;
		this.pageable = pageable;
	}
	
	
	
	public int getTotalPages() {
		return pageable.getPageSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) pageable.getPageSize());
	}
	
	
	public long getTotalElements() {
		return total;
	}


	public List<T> getContent() {
		return content;
	}


	public void setContent(List<T> content) {
		this.content = content;
	}


	public long getTotal() {
		return total;
	}


	public void setTotal(long total) {
		this.total = total;
	}


	public Pageable<T> getPageable() {
		return pageable;
	}


	public void setPageable(Pageable<T> pageable) {
		this.pageable = pageable;
	}
	
	
	
}
