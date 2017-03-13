package cn.com.dubbo.model;

import java.math.BigDecimal;

import cn.com.dubbo.base.bo.BaseBO;


public class EcPaymentType extends BaseBO{

	private static final long serialVersionUID = -3333686199097659052L;
	
	private Integer paymentTypeId;
    private String paymentTypeNo;
    private String paymentTypeName;
    private String paymentTypeLogo;
    private Short paymentTypeOrder;
    private String isOffline;
    private String isEnable;
    private String arrivalTime;
    private BigDecimal commisionRate;
    private String paymentTypeNotes;
    private String partnerId;
    private String partnerKey;
    private String partnerName;
    private String showUrl;
    private String returnUrl;
    private String notifyUrl;
    private String managerEmail;
    private String privateDomain;
    private String serviceVersion;
    private String privateKeyPath;
    private String publicKeyPath;
    private String offlineBankName;
    private String offlineAccountName;
    private String offlineAccountPrivate;
    private String offlineAccountPublic;
    private String ecPreActionUrl;

    public String getEcPreActionUrl() {
		return ecPreActionUrl;
	}

	public void setEcPreActionUrl(String ecPreActionUrl) {
		this.ecPreActionUrl = ecPreActionUrl;
	}

	public Integer getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(Integer paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public String getPaymentTypeNo() {
        return paymentTypeNo;
    }

    public void setPaymentTypeNo(String paymentTypeNo) {
        this.paymentTypeNo = paymentTypeNo;
    }

    public String getPaymentTypeName() {
        return paymentTypeName;
    }

    public void setPaymentTypeName(String paymentTypeName) {
        this.paymentTypeName = paymentTypeName;
    }

    public String getPaymentTypeLogo() {
        return paymentTypeLogo;
    }

    public void setPaymentTypeLogo(String paymentTypeLogo) {
        this.paymentTypeLogo = paymentTypeLogo;
    }

    public Short getPaymentTypeOrder() {
        return paymentTypeOrder;
    }

    public void setPaymentTypeOrder(Short paymentTypeOrder) {
        this.paymentTypeOrder = paymentTypeOrder;
    }

    public String getIsOffline() {
        return isOffline;
    }

    public void setIsOffline(String isOffline) {
        this.isOffline = isOffline;
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public BigDecimal getCommisionRate() {
        return commisionRate;
    }

    public void setCommisionRate(BigDecimal commisionRate) {
        this.commisionRate = commisionRate;
    }

    public String getPaymentTypeNotes() {
        return paymentTypeNotes;
    }

    public void setPaymentTypeNotes(String paymentTypeNotes) {
        this.paymentTypeNotes = paymentTypeNotes;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerKey() {
        return partnerKey;
    }

    public void setPartnerKey(String partnerKey) {
        this.partnerKey = partnerKey;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getShowUrl() {
        return showUrl;
    }

    public void setShowUrl(String showUrl) {
        this.showUrl = showUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public String getPrivateDomain() {
        return privateDomain;
    }

    public void setPrivateDomain(String privateDomain) {
        this.privateDomain = privateDomain;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    public String getPublicKeyPath() {
        return publicKeyPath;
    }

    public void setPublicKeyPath(String publicKeyPath) {
        this.publicKeyPath = publicKeyPath;
    }

    public String getOfflineBankName() {
        return offlineBankName;
    }

    public void setOfflineBankName(String offlineBankName) {
        this.offlineBankName = offlineBankName;
    }

    public String getOfflineAccountName() {
        return offlineAccountName;
    }

    public void setOfflineAccountName(String offlineAccountName) {
        this.offlineAccountName = offlineAccountName;
    }

    public String getOfflineAccountPrivate() {
        return offlineAccountPrivate;
    }

    public void setOfflineAccountPrivate(String offlineAccountPrivate) {
        this.offlineAccountPrivate = offlineAccountPrivate;
    }

    public String getOfflineAccountPublic() {
        return offlineAccountPublic;
    }

    public void setOfflineAccountPublic(String offlineAccountPublic) {
        this.offlineAccountPublic = offlineAccountPublic;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((arrivalTime == null) ? 0 : arrivalTime.hashCode());
		result = prime * result
				+ ((commisionRate == null) ? 0 : commisionRate.hashCode());
		result = prime * result
				+ ((ecPreActionUrl == null) ? 0 : ecPreActionUrl.hashCode());
		result = prime * result
				+ ((isEnable == null) ? 0 : isEnable.hashCode());
		result = prime * result
				+ ((isOffline == null) ? 0 : isOffline.hashCode());
		result = prime * result
				+ ((managerEmail == null) ? 0 : managerEmail.hashCode());
		result = prime * result
				+ ((notifyUrl == null) ? 0 : notifyUrl.hashCode());
		result = prime
				* result
				+ ((offlineAccountName == null) ? 0 : offlineAccountName
						.hashCode());
		result = prime
				* result
				+ ((offlineAccountPrivate == null) ? 0 : offlineAccountPrivate
						.hashCode());
		result = prime
				* result
				+ ((offlineAccountPublic == null) ? 0 : offlineAccountPublic
						.hashCode());
		result = prime * result
				+ ((offlineBankName == null) ? 0 : offlineBankName.hashCode());
		result = prime * result
				+ ((partnerId == null) ? 0 : partnerId.hashCode());
		result = prime * result
				+ ((partnerKey == null) ? 0 : partnerKey.hashCode());
		result = prime * result
				+ ((partnerName == null) ? 0 : partnerName.hashCode());
		result = prime * result
				+ ((paymentTypeId == null) ? 0 : paymentTypeId.hashCode());
		result = prime * result
				+ ((paymentTypeLogo == null) ? 0 : paymentTypeLogo.hashCode());
		result = prime * result
				+ ((paymentTypeName == null) ? 0 : paymentTypeName.hashCode());
		result = prime * result
				+ ((paymentTypeNo == null) ? 0 : paymentTypeNo.hashCode());
		result = prime
				* result
				+ ((paymentTypeNotes == null) ? 0 : paymentTypeNotes.hashCode());
		result = prime
				* result
				+ ((paymentTypeOrder == null) ? 0 : paymentTypeOrder.hashCode());
		result = prime * result
				+ ((privateDomain == null) ? 0 : privateDomain.hashCode());
		result = prime * result
				+ ((privateKeyPath == null) ? 0 : privateKeyPath.hashCode());
		result = prime * result
				+ ((publicKeyPath == null) ? 0 : publicKeyPath.hashCode());
		result = prime * result
				+ ((returnUrl == null) ? 0 : returnUrl.hashCode());
		result = prime * result
				+ ((serviceVersion == null) ? 0 : serviceVersion.hashCode());
		result = prime * result + ((showUrl == null) ? 0 : showUrl.hashCode());
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
		EcPaymentType other = (EcPaymentType) obj;
		if (arrivalTime == null) {
			if (other.arrivalTime != null)
				return false;
		} else if (!arrivalTime.equals(other.arrivalTime))
			return false;
		if (commisionRate == null) {
			if (other.commisionRate != null)
				return false;
		} else if (!commisionRate.equals(other.commisionRate))
			return false;
		if (ecPreActionUrl == null) {
			if (other.ecPreActionUrl != null)
				return false;
		} else if (!ecPreActionUrl.equals(other.ecPreActionUrl))
			return false;
		if (isEnable == null) {
			if (other.isEnable != null)
				return false;
		} else if (!isEnable.equals(other.isEnable))
			return false;
		if (isOffline == null) {
			if (other.isOffline != null)
				return false;
		} else if (!isOffline.equals(other.isOffline))
			return false;
		if (managerEmail == null) {
			if (other.managerEmail != null)
				return false;
		} else if (!managerEmail.equals(other.managerEmail))
			return false;
		if (notifyUrl == null) {
			if (other.notifyUrl != null)
				return false;
		} else if (!notifyUrl.equals(other.notifyUrl))
			return false;
		if (offlineAccountName == null) {
			if (other.offlineAccountName != null)
				return false;
		} else if (!offlineAccountName.equals(other.offlineAccountName))
			return false;
		if (offlineAccountPrivate == null) {
			if (other.offlineAccountPrivate != null)
				return false;
		} else if (!offlineAccountPrivate.equals(other.offlineAccountPrivate))
			return false;
		if (offlineAccountPublic == null) {
			if (other.offlineAccountPublic != null)
				return false;
		} else if (!offlineAccountPublic.equals(other.offlineAccountPublic))
			return false;
		if (offlineBankName == null) {
			if (other.offlineBankName != null)
				return false;
		} else if (!offlineBankName.equals(other.offlineBankName))
			return false;
		if (partnerId == null) {
			if (other.partnerId != null)
				return false;
		} else if (!partnerId.equals(other.partnerId))
			return false;
		if (partnerKey == null) {
			if (other.partnerKey != null)
				return false;
		} else if (!partnerKey.equals(other.partnerKey))
			return false;
		if (partnerName == null) {
			if (other.partnerName != null)
				return false;
		} else if (!partnerName.equals(other.partnerName))
			return false;
		if (paymentTypeId == null) {
			if (other.paymentTypeId != null)
				return false;
		} else if (!paymentTypeId.equals(other.paymentTypeId))
			return false;
		if (paymentTypeLogo == null) {
			if (other.paymentTypeLogo != null)
				return false;
		} else if (!paymentTypeLogo.equals(other.paymentTypeLogo))
			return false;
		if (paymentTypeName == null) {
			if (other.paymentTypeName != null)
				return false;
		} else if (!paymentTypeName.equals(other.paymentTypeName))
			return false;
		if (paymentTypeNo == null) {
			if (other.paymentTypeNo != null)
				return false;
		} else if (!paymentTypeNo.equals(other.paymentTypeNo))
			return false;
		if (paymentTypeNotes == null) {
			if (other.paymentTypeNotes != null)
				return false;
		} else if (!paymentTypeNotes.equals(other.paymentTypeNotes))
			return false;
		if (paymentTypeOrder == null) {
			if (other.paymentTypeOrder != null)
				return false;
		} else if (!paymentTypeOrder.equals(other.paymentTypeOrder))
			return false;
		if (privateDomain == null) {
			if (other.privateDomain != null)
				return false;
		} else if (!privateDomain.equals(other.privateDomain))
			return false;
		if (privateKeyPath == null) {
			if (other.privateKeyPath != null)
				return false;
		} else if (!privateKeyPath.equals(other.privateKeyPath))
			return false;
		if (publicKeyPath == null) {
			if (other.publicKeyPath != null)
				return false;
		} else if (!publicKeyPath.equals(other.publicKeyPath))
			return false;
		if (returnUrl == null) {
			if (other.returnUrl != null)
				return false;
		} else if (!returnUrl.equals(other.returnUrl))
			return false;
		if (serviceVersion == null) {
			if (other.serviceVersion != null)
				return false;
		} else if (!serviceVersion.equals(other.serviceVersion))
			return false;
		if (showUrl == null) {
			if (other.showUrl != null)
				return false;
		} else if (!showUrl.equals(other.showUrl))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EcPaymentType [paymentTypeId=" + paymentTypeId
				+ ", paymentTypeNo=" + paymentTypeNo + ", paymentTypeName="
				+ paymentTypeName + ", paymentTypeLogo=" + paymentTypeLogo
				+ ", paymentTypeOrder=" + paymentTypeOrder + ", isOffline="
				+ isOffline + ", isEnable=" + isEnable + ", arrivalTime="
				+ arrivalTime + ", commisionRate=" + commisionRate
				+ ", paymentTypeNotes=" + paymentTypeNotes + ", partnerId="
				+ partnerId + ", partnerKey=" + partnerKey + ", partnerName="
				+ partnerName + ", showUrl=" + showUrl + ", returnUrl="
				+ returnUrl + ", notifyUrl=" + notifyUrl + ", managerEmail="
				+ managerEmail + ", privateDomain=" + privateDomain
				+ ", serviceVersion=" + serviceVersion + ", privateKeyPath="
				+ privateKeyPath + ", publicKeyPath=" + publicKeyPath
				+ ", offlineBankName=" + offlineBankName
				+ ", offlineAccountName=" + offlineAccountName
				+ ", offlineAccountPrivate=" + offlineAccountPrivate
				+ ", offlineAccountPublic=" + offlineAccountPublic
				+ ", ecPreActionUrl=" + ecPreActionUrl + "]";
	}
    
    
    

}