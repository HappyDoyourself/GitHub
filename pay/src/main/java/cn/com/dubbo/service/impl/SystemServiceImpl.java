package cn.com.dubbo.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jiuyao.ec.common.model.SysCode;
import com.jiuyao.ec.common.model.SysCodeType;

import cn.com.dubbo.mapper.SysCodeMapper;
import cn.com.dubbo.service.SystemService;


@Service
public class SystemServiceImpl extends BaseServiceImpl implements SystemService {

	@Autowired
	private SysCodeMapper sysCodeMapper;

	public Map<String, Object> getCodesByTypeNo(String codeTypeNo) {
		Map<String,Object> resMap =  new HashMap<String,Object>();
		List list = sysCodeMapper.getCodesByTypeNo(codeTypeNo);
		resMap.put("rows", list !=null ? list : new ArrayList<SysCode>());
		return resMap;
	}
	
	public SysCode getCode(SysCode code){
		return sysCodeMapper.findCode(code);
	}
	
}
