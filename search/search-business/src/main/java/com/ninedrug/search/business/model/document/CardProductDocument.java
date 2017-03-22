package com.ninedrug.search.business.model.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.ninedrug.search.business.model.entity.BaseBean;

/**
 * 
 * @author Administrator
 * @date 2016年11月1日
 */
@Document(indexName = "cardproduct", type = "card_product", shards = 1, replicas = 0, refreshInterval = "-1")
public class CardProductDocument extends BaseBean {

	@Id
	private String id;

	// 商品ID
	@Field(type = FieldType.Long)
	private Long productId;

	// 卡ID
	@Field(type = FieldType.Long)
	private Long cardId;

	// 卡片编号
	@Field(type = FieldType.String)
	private String cardCode;

	// 卡片名称
	@Field(type = FieldType.String)
	private String cardName;

	//卡片描述(PC列表显示)
	@Field(type = FieldType.String)
	private String cardDesc;
	
	//PC列表卡片图标地址
	@Field(type = FieldType.String)
	private String cardIconUrlPc;
	
	//H5列表卡片图标地址 
	@Field(type = FieldType.String)
	private String cardIconUrlApp;
	
	
	public CardProductDocument(){}
	
	public CardProductDocument(String id, Long productId, Long cardId, String cardCode, String cardName,
			String cardDesc, String cardIconUrlPc, String cardIconUrlApp) {
		super();
		this.id = id;
		this.productId = productId;
		this.cardId = cardId;
		this.cardCode = cardCode;
		this.cardName = cardName;
		this.cardDesc = cardDesc;
		this.cardIconUrlPc = cardIconUrlPc;
		this.cardIconUrlApp = cardIconUrlApp;
	}
	public String getId() {
		if(id == null){
			id = productId + "_" + cardId;
		}
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
