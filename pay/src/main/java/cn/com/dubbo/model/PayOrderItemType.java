package cn.com.dubbo.model;

import cn.com.dubbo.base.bo.BaseBO;

import java.math.BigDecimal;

/**
 * 订单明细商品类型
 * @author jinjin
 *
 */
public class PayOrderItemType {

	//分类编号
	private String code;
	
	private String name;
	
	private BigDecimal price;
	
	private String number;
	
	private String SortAmount;//分类对应总金额

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSortAmount() {
		return SortAmount;
	}

	public void setSortAmount(String sortAmount) {
		SortAmount = sortAmount;
	}
	
}
