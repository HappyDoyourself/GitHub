package cn.com.dubbo.mapper;

import java.util.List;

import cn.com.dubbo.model.EcPaymentType;
import cn.com.dubbo.model.EcPaymentTypeParams;

public interface EcPaymentMapper {
	
	EcPaymentType getPaymentInfoByNo(String paymentTypeNo);

	EcPaymentType getPaymentInfoById(String paymentTypeId);
	
	List<EcPaymentType> getEcPaymentTypeList(EcPaymentType ecPaymentType);
	
	List<EcPaymentTypeParams> getEcPaymentTypeParamsBytypeId(Integer paymentTypeId);
}
