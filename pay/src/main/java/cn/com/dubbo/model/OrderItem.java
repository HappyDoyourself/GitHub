package cn.com.dubbo.model;

import java.math.BigDecimal;

import cn.com.dubbo.base.bo.BaseBO;


/**
 * 订单明细表
 */
public class OrderItem extends BaseBO {

	private static final long serialVersionUID = 60935243040207065L;
	/**
	 * 主键
	 */
	private Long orderItemId;
	/**
	 * 订单ID
	 */
	private String orderNo;
	/**
	 * 商品类型
	 */
	private String goodsType;
	/**
	 * 货品ID
	 */
	private Long goodsId;
	/**
	 * 商品编码
	 */
	private String goodsNo;
	/**
	 * 商品名称
	 */
	private String goodsName;
	/**
	 * 单位
	 */
	private String goodsUnit;
	/**
	 * 原价
	 */
	private BigDecimal goodsOldPrice;
	/**
	 * 成交价
	 */
	private BigDecimal goodsPrice;
	/**
	 * 商品数量
	 */
	private BigDecimal goodsAmount;
	/**
	 * 小计金额
	 */
	private BigDecimal goodsSumFee;
	/**
	 * 商品的宽
	 */
	private BigDecimal weight;
	/**
	 * 商品的体积
	 */
	private BigDecimal volume;
	/**
	 * 商品库存
	 */
	private BigDecimal goodStock;
	/**
	 * 商品图片的URL
	 */
	private String proImageUrl;
	/**
	 * 原始图片路径
	 */
	private String imageOrign;
	private String producer;
	/**
	 * 支付介绍
	 */
	private String deliveryNotes;
	/**
	 * 商品ID
	 */
	private Long productId;
	/**
	 * 成本价
	 */
	private BigDecimal goodsCostPrice;
	/**
	 * 药品处方类型
	 */
	private String drugPrescriptionType;
	/**
	 * 标准
	 */
	private String packageStandard;
	/**
	 * 多个ID
	 */
	private String ids;
	/**
	 * 组合套餐ID
	 */
	private Integer productCombinationId;
	/**
	 * 订单ID的集合
	 */
	private String[] orderIDS;
	
	private String standbyTime;   //判断库存是否同步
	/**
	 * 接收orderID
	 */
	private String strOrderID;
	// 赠品的goodsId
	private Long giftGoodsId;
	// 赠品的productId
	private Long giftProductId;
	// 订单提交时间
	private String commitTime;
	// 订单状态
	private Integer orderState;
	// 订单状态名称
	private String codeValue;
	// 订单状态编码
	private String codeNo;
	private String isvalid;
	private String isTc ;
	private Long memberId;
	private String stockNo; //仓库编码
	/**
	 * 查询条件是否查询所有订单，包括处方药和非处方药
	 * 前台我的订单明细专用
	 * Y:无限制
	 * N:非处方药
	 */
    private String isAll;
    
    private String typeNo;
	
	public String getTypeNo() {
		return typeNo;
	}
	public void setTypeNo(String typeNo) {
		this.typeNo = typeNo;
	}
	public String getStandbyTime() {
		return standbyTime;
	}
	public void setStandbyTime(String standbyTime) {
		this.standbyTime = standbyTime;
	}
	public String getIsvalid() {
		return isvalid;
	}
	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}
	public String getIsTc() {
		return isTc;
	}
	public void setIsTc(String isTc) {
		this.isTc = isTc;
	}
	public String getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(String commitTime) {
		this.commitTime = commitTime;
	}

	public Integer getOrderState() {
		return orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String getCodeNo() {
		return codeNo;
	}

	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getGiftGoodsId() {
		return giftGoodsId;
	}

	public void setGiftGoodsId(Long giftGoodsId) {
		this.giftGoodsId = giftGoodsId;
	}

	public Long getGiftProductId() {
		return giftProductId;
	}

	public void setGiftProductId(Long giftProductId) {
		this.giftProductId = giftProductId;
	}

	public String getStrOrderID() {
		return strOrderID;
	}

	public void setStrOrderID(String strOrderID) {
		this.strOrderID = strOrderID;
	}

	public String[] getOrderIDS() {
		return orderIDS;
	}

	public void setOrderIDS(String[] orderIDS) {
		this.orderIDS = orderIDS;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getPackageStandard() {

		return packageStandard;
	}

	public void setPackageStandard(String packageStandard) {
		this.packageStandard = packageStandard;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getDrugPrescriptionType() {
		return drugPrescriptionType;
	}

	public void setDrugPrescriptionType(String drugPrescriptionType) {
		this.drugPrescriptionType = drugPrescriptionType;
	}

	public BigDecimal getGoodsCostPrice() {
		return goodsCostPrice;
	}

	public void setGoodsCostPrice(BigDecimal goodsCostPrice) {
		this.goodsCostPrice = goodsCostPrice;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getDeliveryNotes() {
		return deliveryNotes;
	}

	public void setDeliveryNotes(String deliveryNotes) {
		this.deliveryNotes = deliveryNotes;
	}

	public String getProImageUrl() {
		return proImageUrl;
	}

	public void setProImageUrl(String proImageUrl) {
		this.proImageUrl = proImageUrl;
	}

	public String getImageOrign() {
		return imageOrign;
	}

	public void setImageOrign(String imageOrign) {
		this.imageOrign = imageOrign;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsUnit() {
		return goodsUnit;
	}

	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}

	public BigDecimal getGoodsOldPrice() {
		return goodsOldPrice;
	}

	public void setGoodsOldPrice(BigDecimal goodsOldPrice) {
		this.goodsOldPrice = goodsOldPrice;
	}

	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public BigDecimal getGoodsAmount() {
		return goodsAmount;
	}

	public void setGoodsAmount(BigDecimal goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	public BigDecimal getGoodsSumFee() {
		return goodsSumFee;
	}

	public void setGoodsSumFee(BigDecimal goodsSumFee) {
		this.goodsSumFee = goodsSumFee;
	}

	public BigDecimal getGoodStock() {
		return goodStock;
	}

	public void setGoodStock(BigDecimal goodStock) {
		this.goodStock = goodStock;
	}

	public Integer getProductCombinationId() {
		return productCombinationId;
	}

	public void setProductCombinationId(Integer productCombinationId) {
		this.productCombinationId = productCombinationId;
	}
	
	public String getStockNo() {
		return stockNo;
	}
	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}
	public String getIsAll() {
		return isAll;
	}
	public void setIsAll(String isAll) {
		this.isAll = isAll;
	}
	@Override
	public int hashCode() {
		final int prime = 32;
		int result = super.hashCode();
		result = prime * result
				+ ((deliveryNotes == null) ? 0 : deliveryNotes.hashCode());
		result = prime
				* result
				+ ((drugPrescriptionType == null) ? 0 : drugPrescriptionType
						.hashCode());
		result = prime * result
				+ ((goodStock == null) ? 0 : goodStock.hashCode());
		result = prime * result
				+ ((goodsAmount == null) ? 0 : goodsAmount.hashCode());
		result = prime * result
				+ ((goodsCostPrice == null) ? 0 : goodsCostPrice.hashCode());
		result = prime * result + ((goodsId == null) ? 0 : goodsId.hashCode());
		result = prime * result
				+ ((goodsName == null) ? 0 : goodsName.hashCode());
		result = prime * result + ((goodsNo == null) ? 0 : goodsNo.hashCode());
		result = prime * result
				+ ((goodsOldPrice == null) ? 0 : goodsOldPrice.hashCode());
		result = prime * result
				+ ((goodsPrice == null) ? 0 : goodsPrice.hashCode());
		result = prime * result
				+ ((goodsSumFee == null) ? 0 : goodsSumFee.hashCode());
		result = prime * result
				+ ((goodsType == null) ? 0 : goodsType.hashCode());
		result = prime * result
				+ ((goodsUnit == null) ? 0 : goodsUnit.hashCode());
		result = prime * result + ((ids == null) ? 0 : ids.hashCode());
		result = prime * result
				+ ((imageOrign == null) ? 0 : imageOrign.hashCode());
		result = prime * result + ((orderNo == null) ? 0 : orderNo.hashCode());
		result = prime * result
				+ ((orderItemId == null) ? 0 : orderItemId.hashCode());
		result = prime * result
				+ ((packageStandard == null) ? 0 : packageStandard.hashCode());
		result = prime * result
				+ ((proImageUrl == null) ? 0 : proImageUrl.hashCode());
		result = prime * result
				+ ((producer == null) ? 0 : producer.hashCode());
		result = prime
				* result
				+ ((productCombinationId == null) ? 0 : productCombinationId
						.hashCode());
		result = prime * result + ((volume == null) ? 0 : volume.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
		result = prime * result
				+ ((productId == null) ? 0 : productId.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItem other = (OrderItem) obj;
		if (deliveryNotes == null) {
			if (other.deliveryNotes != null)
				return false;
		} else if (!deliveryNotes.equals(other.deliveryNotes))
			return false;
		if (drugPrescriptionType == null) {
			if (other.drugPrescriptionType != null)
				return false;
		} else if (!drugPrescriptionType.equals(other.drugPrescriptionType))
			return false;
		if (goodStock == null) {
			if (other.goodStock != null)
				return false;
		} else if (!goodStock.equals(other.goodStock))
			return false;
		if (goodsAmount == null) {
			if (other.goodsAmount != null)
				return false;
		} else if (!goodsAmount.equals(other.goodsAmount))
			return false;
		if (goodsCostPrice == null) {
			if (other.goodsCostPrice != null)
				return false;
		} else if (!goodsCostPrice.equals(other.goodsCostPrice))
			return false;
		if (goodsId == null) {
			if (other.goodsId != null)
				return false;
		} else if (!goodsId.equals(other.goodsId))
			return false;
		if (goodsName == null) {
			if (other.goodsName != null)
				return false;
		} else if (!goodsName.equals(other.goodsName))
			return false;
		if (goodsNo == null) {
			if (other.goodsNo != null)
				return false;
		} else if (!goodsNo.equals(other.goodsNo))
			return false;
		if (goodsOldPrice == null) {
			if (other.goodsOldPrice != null)
				return false;
		} else if (!goodsOldPrice.equals(other.goodsOldPrice))
			return false;
		if (goodsPrice == null) {
			if (other.goodsPrice != null)
				return false;
		} else if (!goodsPrice.equals(other.goodsPrice))
			return false;
		if (goodsSumFee == null) {
			if (other.goodsSumFee != null)
				return false;
		} else if (!goodsSumFee.equals(other.goodsSumFee))
			return false;
		if (goodsType == null) {
			if (other.goodsType != null)
				return false;
		} else if (!goodsType.equals(other.goodsType))
			return false;
		if (goodsUnit == null) {
			if (other.goodsUnit != null)
				return false;
		} else if (!goodsUnit.equals(other.goodsUnit))
			return false;
		if (ids == null) {
			if (other.ids != null)
				return false;
		} else if (!ids.equals(other.ids))
			return false;
		if (imageOrign == null) {
			if (other.imageOrign != null)
				return false;
		} else if (!imageOrign.equals(other.imageOrign))
			return false;
		if (orderNo == null) {
			if (other.orderNo != null)
				return false;
		} else if (!orderNo.equals(other.orderNo))
			return false;
		if (orderItemId == null) {
			if (other.orderItemId != null)
				return false;
		} else if (!orderItemId.equals(other.orderItemId))
			return false;
		if (packageStandard == null) {
			if (other.packageStandard != null)
				return false;
		} else if (!packageStandard.equals(other.packageStandard))
			return false;
		if (proImageUrl == null) {
			if (other.proImageUrl != null)
				return false;
		} else if (!proImageUrl.equals(other.proImageUrl))
			return false;
		if (producer == null) {
			if (other.producer != null)
				return false;
		} else if (!producer.equals(other.producer))
			return false;
		if (productCombinationId == null) {
			if (other.productCombinationId != null)
				return false;
		} else if (!productCombinationId.equals(other.productCombinationId))
			return false;
		if (volume == null) {
			if (other.volume != null)
				return false;
		} else if (!volume.equals(other.volume))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderItem [orderItemId=" + orderItemId + ", orderNo=" + orderNo
				+ ", goodsType=" + goodsType + ", goodsId=" + goodsId
				+ ", goodsNo=" + goodsNo + ", goodsName=" + goodsName
				+ ", goodsUnit=" + goodsUnit + ", goodsOldPrice="
				+ goodsOldPrice + ", goodsPrice=" + goodsPrice
				+ ", goodsAmount=" + goodsAmount + ", goodsSumFee="
				+ goodsSumFee + ", weight=" + weight + ", volume=" + volume
				+ ", goodStock=" + goodStock + ", proImageUrl=" + proImageUrl
				+ ", imageOrign=" + imageOrign + ", producer=" + producer
				+ ", deliveryNotes=" + deliveryNotes + ", goodsCostPrice="
				+ goodsCostPrice + ", productId=" + productId
				+ ", drugPrescriptionType=" + drugPrescriptionType
				+ ", packageStandard=" + packageStandard + ", ids=" + ids
				+ ", productCombinationId=" + productCombinationId + "]";
	}
}