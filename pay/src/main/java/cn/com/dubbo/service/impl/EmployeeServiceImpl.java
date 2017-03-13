package cn.com.dubbo.service.impl;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.dubbo.bean.Employee;
import cn.com.dubbo.mapper.EmployeeMapper;
import cn.com.dubbo.service.EmployeeService;


@Service("employeeService")
//@Transactional
public class EmployeeServiceImpl implements EmployeeService{

	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static final Logger logger = Logger.getLogger(EmployeeServiceImpl.class);
	
	@Autowired
	private EmployeeMapper employeeMapper;

	@Override
	public int insert(Employee emp) {
		return employeeMapper.insert(emp);
	}
}
