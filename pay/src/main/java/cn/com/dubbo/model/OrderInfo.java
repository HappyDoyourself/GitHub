package cn.com.dubbo.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.com.dubbo.base.bo.BaseBO;

public class OrderInfo extends BaseBO{

	private static final long serialVersionUID = 633292968332005613L;
	private String orderNo;
    private String erpOrderId;
    private Long memberId;
    private Long parentOrderId;
    private Integer multiChannelId;
    private String multiChannelName;
    private String isPaid;
    private String paymentMode;
    private Integer paymentTypeId;
    private Integer paymentId;
    private String paymentNo;
    private String paidTime;
    private Integer offlinePaidUserId;
    private Integer logisticCompanyId;
    private Integer orderState;
    private Integer orderOutState;
    private String limitDate;
    private BigDecimal skuFee;
    private BigDecimal discountFee;
    private Integer orderPoints;
    private BigDecimal orderVouchers;
    private BigDecimal otherDiscounts;
    private BigDecimal deliveryFee;
    private BigDecimal orderFee;
    private String orderMsg;
    private String orderNotes;
    private String auditNotes;
    private Integer auditUserId;
    private String auditTime;
    private String isLock;
    private String lockTime;
    private Integer lockUserId;
    private String commitTime;
    private String finishTime;
    private String cancelTime;
    private Integer cancelUserId;
    private String cancelNotes;
    private BigDecimal skuWeights;
    private BigDecimal skuVolume;
    private String isIntoErp;
    private String intoTime;
    private String receiveUser;
    private Integer provinceId;
    private Integer cityId;
    private Integer areaId;
    private String provinceName;
    private String cityName;
    private String areaName;
    private String receiveAddress;
    private String receiveFullAddress;
    private Integer receivePost;
    private String receiveTel;
    private String receiveMobile;
    private String receiveEmail;
    private String receiveDateType;
    private String receiveTimeType;
    private String invoiceType;
    private String invoiceTitle;
    private String invoiceContent;
    private String invoiceTaxNo;
    private String invoiceTaxAddress;
    private String invoiceTaxTel;
    private String invoiceTaxBank;
    private String invoiceTaxAccount;
    private Long multiChannelOrderId;
    private Long multiChannelOrderBatchId;
    private BigDecimal paidFee;
    private String invoiceContentType;
    private BigDecimal orderPayFee;//订单待支付金额
    

	private String memberRealName;
    private String memberLoginName;
    private String memberRankName;
    private String auditUserName;
    private String stateName;
    private String commitTimeBg;
    private String commitTimeEd;
    private String auditTimeBg;
    private String auditTimeEd;
    private String paymentTypeNo;
    private String ids;
    
    private String paymentTypeLogo;
    private String urlFromNo;
    private String isEMS;
    private String channelNo;
    
    private String[] paymentModeArray;
    private String tempPrhase;
    
    private List orderLogList;
    
    private BigDecimal skuAmount;
    private String allUsedPromoteRuleIds;
    private BigDecimal tempOrderFee;
    
    private String returnType;
    private String returnValue;
    
    private String paymentTypeName;
    private String paidTimeBg;
    private String paidTimeEd;
    private String searchOldOrder;
    
    private String proImageUrl;
    private String imageOrign;
    private String codeValue;
    private String codeNo;
    private String codeOrder;
    private String companyNo;
    private String companyName;
    
    private String commitTimeEnd;
    private String website;
    
    private String countId;
    private String countFee;
    
    private Integer couponTypeUsId;
    private String couponNo;
    
    private String isUpload;
    private Integer uploadUserId;
    private String uploadTime;
    private String promoteRuleIds;
    private String goodsBatchNo;
    private String goodsNo;
    
    private String isTc;
    /**优惠券批次号*/
    private Long publishId;
    /**用户状态*/
    private String userState;

	private String refundState;

	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}

	public String getRefundState() {

		return refundState;
	}

	/**
     * 前台weblist专用，标记是否为处方药组合订单
     */
    private String isItemTc;

	private Integer haveCfy; //'处方订单标识(0-普通订单, 1-单轨处方药订单, 2-双轨处方药订单)',

	public void setHaveCfy(Integer haveCfy) {
		this.haveCfy = haveCfy;
	}

	public Integer getHaveCfy() {

		return haveCfy;
	}

	public String getAuditTimeBg() {
		return auditTimeBg;
	}

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public void setAuditTimeBg(String auditTimeBg) {
		this.auditTimeBg = auditTimeBg;
	}

	public String getAuditTimeEd() {
		return auditTimeEd;
	}

	public void setAuditTimeEd(String auditTimeEd) {
		this.auditTimeEd = auditTimeEd;
	}


	public String getIsTc() {
		return isTc;
	}

	public void setIsTc(String isTc) {
		this.isTc = isTc;
	}


	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getCouponTypeUsId() {
		return couponTypeUsId;
	}

	public void setCouponTypeUsId(Integer couponTypeUsId) {
		this.couponTypeUsId = couponTypeUsId;
	}

	public String getCouponNo() {
		return couponNo;
	}

	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}

	public String getCodeOrder() {
		return codeOrder;
	}

	public void setCodeOrder(String codeOrder) {
		this.codeOrder = codeOrder;
	}

	public String getCountId() {
		return countId;
	}

	public void setCountId(String countId) {
		this.countId = countId;
	}

	public String getCountFee() {
		return countFee;
	}

	public void setCountFee(String countFee) {
		this.countFee = countFee;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public List getOrderLogList() {
		return orderLogList;
	}

	public void setOrderLogList(List orderLogList) {
		this.orderLogList = orderLogList;
	}

	public String getTempPrhase() {
		return tempPrhase;
	}

	public void setTempPrhase(String tempPrhase) {
		this.tempPrhase = tempPrhase;
	}

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public String getIsEMS() {
		return isEMS;
	}

	public void setIsEMS(String isEMS) {
		this.isEMS = isEMS;
	}

	public String getUrlFromNo() {
		return urlFromNo;
	}

	public void setUrlFromNo(String urlFromNo) {
		this.urlFromNo = urlFromNo;
	}

	public String getPaymentTypeLogo() {
		return paymentTypeLogo;
	}

	public void setPaymentTypeLogo(String paymentTypeLogo) {
		this.paymentTypeLogo = paymentTypeLogo;
	}
	public BigDecimal getOrderPayFee() {
			return orderPayFee;
		}

		public void setOrderPayFee(BigDecimal orderPayFee) {
			this.orderPayFee = orderPayFee;
		}
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

    public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	public BigDecimal getTempOrderFee() {
		return tempOrderFee;
	}

	public void setTempOrderFee(BigDecimal tempOrderFee) {
		this.tempOrderFee = tempOrderFee;
	}


	public String getAllUsedPromoteRuleIds() {
		return allUsedPromoteRuleIds;
	}

	public void setAllUsedPromoteRuleIds(String allUsedPromoteRuleIds) {
		this.allUsedPromoteRuleIds = allUsedPromoteRuleIds;
	}

	public BigDecimal getSkuAmount() {
		return skuAmount;
	}

	public void setSkuAmount(BigDecimal skuAmount) {
		this.skuAmount = skuAmount;
	}

	public String getPaymentTypeNo() {
		return paymentTypeNo;
	}

	public void setPaymentTypeNo(String paymentTypeNo) {
		this.paymentTypeNo = paymentTypeNo;
	}

    public String getCommitTimeEnd() {
		return commitTimeEnd;
	}

	public void setCommitTimeEnd(String commitTimeEnd) {
		this.commitTimeEnd = commitTimeEnd;
	}

	private String timeId;
    
    public String getTimeId() {
		return timeId;
	}

	public void setTimeId(String timeId) {
		this.timeId = timeId;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCodeNo() {
		return codeNo;
	}

	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
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

	public BigDecimal getSkuVolume() {
		return skuVolume;
	}

	public void setSkuVolume(BigDecimal skuVolume) {
		this.skuVolume = skuVolume;
	}

    
    public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getErpOrderId() {
        return erpOrderId;
    }

    public void setErpOrderId(String erpOrderId) {
        this.erpOrderId = erpOrderId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getParentOrderId() {
        return parentOrderId;
    }

    public void setParentOrderId(Long parentOrderId) {
        this.parentOrderId = parentOrderId;
    }
    
    public Integer getMultiChannelId() {
		return multiChannelId;
	}

	public void setMultiChannelId(Integer multiChannelId) {
		this.multiChannelId = multiChannelId;
	}

	public Long getMultiChannelOrderId() {
		return multiChannelOrderId;
	}

	public void setMultiChannelOrderId(Long multiChannelOrderId) {
		this.multiChannelOrderId = multiChannelOrderId;
	}

	public Long getMultiChannelOrderBatchId() {
		return multiChannelOrderBatchId;
	}

	public void setMultiChannelOrderBatchId(Long multiChannelOrderBatchId) {
		this.multiChannelOrderBatchId = multiChannelOrderBatchId;
	}

	public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Integer getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(Integer paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public String getPaidTime() {
        return paidTime;
    }

    public void setPaidTime(String paidTime) {
        this.paidTime = paidTime;
    }

    public Integer getOfflinePaidUserId() {
        return offlinePaidUserId;
    }

    public void setOfflinePaidUserId(Integer offlinePaidUserId) {
        this.offlinePaidUserId = offlinePaidUserId;
    }

    public Integer getLogisticCompanyId() {
        return logisticCompanyId;
    }

    public void setLogisticCompanyId(Integer logisticCompanyId) {
        this.logisticCompanyId = logisticCompanyId;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public Integer getOrderOutState() {
        return orderOutState;
    }

    public void setOrderOutState(Integer orderOutState) {
        this.orderOutState = orderOutState;
    }

    public String getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(String limitDate) {
        this.limitDate = limitDate;
    }

    public BigDecimal getSkuFee() {
        return skuFee;
    }

    public void setSkuFee(BigDecimal skuFee) {
        this.skuFee = skuFee;
    }

    public BigDecimal getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(BigDecimal discountFee) {
        this.discountFee = discountFee;
    }

    public Integer getOrderPoints() {
        return orderPoints;
    }

    public void setOrderPoints(Integer orderPoints) {
        this.orderPoints = orderPoints;
    }

    public BigDecimal getOrderVouchers() {
        return orderVouchers;
    }

    public void setOrderVouchers(BigDecimal orderVouchers) {
        this.orderVouchers = orderVouchers;
    }

    public BigDecimal getOtherDiscounts() {
        return otherDiscounts;
    }

    public void setOtherDiscounts(BigDecimal otherDiscounts) {
        this.otherDiscounts = otherDiscounts;
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public BigDecimal getOrderFee() {
        return orderFee;
    }

    public void setOrderFee(BigDecimal orderFee) {
        this.orderFee = orderFee;
    }

    public String getOrderMsg() {
        return orderMsg;
    }

    public void setOrderMsg(String orderMsg) {
        this.orderMsg = orderMsg;
    }

    public String getOrderNotes() {
        return orderNotes;
    }

    public void setOrderNotes(String orderNotes) {
        this.orderNotes = orderNotes;
    }

    public String getAuditNotes() {
        return auditNotes;
    }

    public void setAuditNotes(String auditNotes) {
        this.auditNotes = auditNotes;
    }

    public Integer getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(Integer auditUserId) {
        this.auditUserId = auditUserId;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public String getIsLock() {
        return isLock;
    }

    public void setIsLock(String isLock) {
        this.isLock = isLock;
    }

    public String getLockTime() {
        return lockTime;
    }

    public void setLockTime(String lockTime) {
        this.lockTime = lockTime;
    }

    public Integer getLockUserId() {
        return lockUserId;
    }

    public void setLockUserId(Integer lockUserId) {
        this.lockUserId = lockUserId;
    }

    public String getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(String commitTime) {
        this.commitTime = commitTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Integer getCancelUserId() {
        return cancelUserId;
    }

    public void setCancelUserId(Integer cancelUserId) {
        this.cancelUserId = cancelUserId;
    }

    public String getCancelNotes() {
        return cancelNotes;
    }

    public void setCancelNotes(String cancelNotes) {
        this.cancelNotes = cancelNotes;
    }

    public BigDecimal getSkuWeights() {
        return skuWeights;
    }

    public void setSkuWeights(BigDecimal skuWeights) {
        this.skuWeights = skuWeights;
    }

    public String getIsIntoErp() {
        return isIntoErp;
    }

    public void setIsIntoErp(String isIntoErp) {
        this.isIntoErp = isIntoErp;
    }

    public String getIntoTime() {
        return intoTime;
    }

    public void setIntoTime(String intoTime) {
        this.intoTime = intoTime;
    }
    
    public String getReceiveUser() {
		return receiveUser;
	}

	public void setReceiveUser(String receiveUser) {
		this.receiveUser = receiveUser;
	}

	public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }
    
    public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	public String getReceiveFullAddress() {
		return receiveFullAddress;
	}

	public void setReceiveFullAddress(String receiveFullAddress) {
		this.receiveFullAddress = receiveFullAddress;
	}

	public Integer getReceivePost() {
		return receivePost;
	}

	public void setReceivePost(Integer receivePost) {
		this.receivePost = receivePost;
	}

	public String getReceiveTel() {
		return receiveTel;
	}

	public void setReceiveTel(String receiveTel) {
		this.receiveTel = receiveTel;
	}

	public String getReceiveMobile() {
		return receiveMobile;
	}

	public void setReceiveMobile(String receiveMobile) {
		this.receiveMobile = receiveMobile;
	}

	public String getReceiveEmail() {
		return receiveEmail;
	}

	public void setReceiveEmail(String receiveEmail) {
		this.receiveEmail = receiveEmail;
	}

	public String getReceiveDateType() {
		return receiveDateType;
	}

	public void setReceiveDateType(String receiveDateType) {
		this.receiveDateType = receiveDateType;
	}

	public String getReceiveTimeType() {
		return receiveTimeType;
	}

	public void setReceiveTimeType(String receiveTimeType) {
		this.receiveTimeType = receiveTimeType;
	}

	public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public String getInvoiceTaxNo() {
        return invoiceTaxNo;
    }

    public void setInvoiceTaxNo(String invoiceTaxNo) {
        this.invoiceTaxNo = invoiceTaxNo;
    }

    public String getInvoiceTaxAddress() {
        return invoiceTaxAddress;
    }

    public void setInvoiceTaxAddress(String invoiceTaxAddress) {
        this.invoiceTaxAddress = invoiceTaxAddress;
    }

    public String getInvoiceTaxTel() {
        return invoiceTaxTel;
    }

    public void setInvoiceTaxTel(String invoiceTaxTel) {
        this.invoiceTaxTel = invoiceTaxTel;
    }

    public String getInvoiceTaxBank() {
        return invoiceTaxBank;
    }

    public void setInvoiceTaxBank(String invoiceTaxBank) {
        this.invoiceTaxBank = invoiceTaxBank;
    }

    public String getInvoiceTaxAccount() {
        return invoiceTaxAccount;
    }

    public void setInvoiceTaxAccount(String invoiceTaxAccount) {
        this.invoiceTaxAccount = invoiceTaxAccount;
    }

    public String getCommitTimeBg() {
		return commitTimeBg;
	}

	public void setCommitTimeBg(String commitTimeBg) {
		this.commitTimeBg = commitTimeBg;
	}

	public String getCommitTimeEd() {
		return commitTimeEd;
	}

	public void setCommitTimeEd(String commitTimeEd) {
		this.commitTimeEd = commitTimeEd;
	}

	public String getMemberRealName() {
		return memberRealName;
	}

	public void setMemberRealName(String memberRealName) {
		this.memberRealName = memberRealName;
	}

	public String getMemberLoginName() {
		return memberLoginName;
	}

	public void setMemberLoginName(String memberLoginName) {
		this.memberLoginName = memberLoginName;
	}

	public BigDecimal getPaidFee() {
		return paidFee;
	}

	public void setPaidFee(BigDecimal paidFee) {
		this.paidFee = paidFee;
	}

	public String getMemberRankName() {
		return memberRankName;
	}

	public void setMemberRankName(String memberRankName) {
		this.memberRankName = memberRankName;
	}

	public String getPaymentTypeName() {
		return paymentTypeName;
	}
	public String getInvoiceContentType() {
		return invoiceContentType;
	}

	public void setInvoiceContentType(String invoiceContentType) {
		this.invoiceContentType = invoiceContentType;
	}

	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}

	public String getPaidTimeBg() {
		return paidTimeBg;
	}

	public void setPaidTimeBg(String paidTimeBg) {
		this.paidTimeBg = paidTimeBg;
	}

	public String getPaidTimeEd() {
		return paidTimeEd;
	}

	public void setPaidTimeEd(String paidTimeEd) {
		this.paidTimeEd = paidTimeEd;
	}

	public String getSearchOldOrder() {
		return searchOldOrder;
	}

	public void setSearchOldOrder(String searchOldOrder) {
		this.searchOldOrder = searchOldOrder;
	}

	public String getAuditUserName() {
		return auditUserName;
	}

	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String[] getPaymentModeArray() {
		return paymentModeArray;
	}

	public void setPaymentModeArray(String[] paymentModeArray) {
		this.paymentModeArray = paymentModeArray;
	}

	public String getIsUpload() {
		return isUpload;
	}

	public void setIsUpload(String isUpload) {
		this.isUpload = isUpload;
	}

	public Integer getUploadUserId() {
		return uploadUserId;
	}

	public void setUploadUserId(Integer uploadUserId) {
		this.uploadUserId = uploadUserId;
	}

	public String getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getPromoteRuleIds() {
		return promoteRuleIds;
	}

	public void setPromoteRuleIds(String promoteRuleIds) {
		this.promoteRuleIds = promoteRuleIds;
	}

	public String getGoodsBatchNo() {
		return goodsBatchNo;
	}

	public void setGoodsBatchNo(String goodsBatchNo) {
		this.goodsBatchNo = goodsBatchNo;
	}
	
	public String getMultiChannelName() {
		return multiChannelName;
	}

	public void setMultiChannelName(String multiChannelName) {
		this.multiChannelName = multiChannelName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Long getPublishId() {
		return publishId;
	}

	public void setPublishId(Long publishId) {
		this.publishId = publishId;
	}
	
	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}
	
	public String getIsItemTc() {
		return isItemTc;
	}

	public void setIsItemTc(String isItemTc) {
		this.isItemTc = isItemTc;
	}
	
}