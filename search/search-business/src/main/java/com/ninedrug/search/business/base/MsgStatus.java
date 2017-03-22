package com.ninedrug.search.business.base;

public enum MsgStatus {
	
	
	ERROR("服务器异常",500),
	
	ERROR_PARAMS("缺少参数",400),
	
	SUC("成功",0);
	
	private String name;
    private long code;
	
	
	private MsgStatus(String name, long code) {
		this.name = name;
		this.code = code;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public long getCode() {
		return code;
	}



	public void setCode(long code) {
		this.code = code;
	}



	
	
	
	public static void main(String[] args){
		System.out.println(MsgStatus.SUC.getCode());
	}
	
}
