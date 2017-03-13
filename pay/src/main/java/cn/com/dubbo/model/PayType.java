package cn.com.dubbo.model;

import cn.com.dubbo.base.bo.BaseBO;
import cn.com.jiuyao.pay.common.util.MathUtil;

import java.math.BigDecimal;

/**
 * 渠道商品类型
 * @author jinjin
 *
 */
public class PayType extends BaseBO {

	private String SortCode;
	
	private BigDecimal SortAmount;//分类对应总金额

	public BigDecimal getSortAmount() {
		return SortAmount.setScale(2,BigDecimal.ROUND_HALF_UP); //数据库查询出来带有4位小数 截取小数点后两位
	}

	public void setSortAmount(BigDecimal sortAmount) {
		SortAmount = sortAmount;
	}

	public String getSortCode() {
		return SortCode;
	}

	public void setSortCode(String sortCode) {
		SortCode = sortCode;
	}

}
