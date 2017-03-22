package com.ninedrug.search.business.model.entity;

public class CardProduct extends BaseBean  {
	
	
	private Long productId;
	
	private Long cardId ;
	
	private String cardCode;
	
	private String cardName;
	//卡片描述(PC列表显示)
	private String cardDesc;
	//PC列表卡片图标地址
	private String cardIconUrlPc;
	//H5列表卡片图标地址 
	private String cardIconUrlApp ;
	
	
	
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getCardId() {
		return cardId;
	}
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
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
	public String getCardIconUrlApp() {
		return cardIconUrlApp;
	}
	public void setCardIconUrlApp(String cardIconUrlApp) {
		this.cardIconUrlApp = cardIconUrlApp;
	}
	
	
	
	
}
