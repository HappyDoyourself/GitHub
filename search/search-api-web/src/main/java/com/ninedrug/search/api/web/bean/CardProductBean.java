package com.ninedrug.search.api.web.bean;

import com.ninedrug.search.business.model.entity.BaseBean;

/**
 * 
 * @author Administrator
 * @date 2016年11月1日
 */
public class CardProductBean extends BaseBean {


	//卡片描述(PC列表显示)
	private String cardDesc;
	
	//PC列表卡片图标地址
	private String cardIconUrlPc;

	public String getCardDesc() {
		return cardDesc;
	}

	public void setCardDesc(String cardDesc) {
		this.cardDesc = cardDesc;
	}

	public String getCardIconUrlPc() {
		return cardIconUrlPc;
	}

	public void setCardIconUrlPc(String cardIconUrlPc) {
		this.cardIconUrlPc = cardIconUrlPc;
	}
	
	
}
