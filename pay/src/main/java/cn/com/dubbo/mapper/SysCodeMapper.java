package cn.com.dubbo.mapper;

import java.util.List;

import com.jiuyao.ec.common.model.SysCode;

public interface SysCodeMapper {

	List<SysCode> getCodesByTypeNo(String codeTypeNo);
	
	SysCode findCode(SysCode sysCode);
}
