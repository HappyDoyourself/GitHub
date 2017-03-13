package cn.com.jiuyao.task;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cn.com.dubbo.bean.Employee;
import cn.com.dubbo.service.EmployeeService;

/**
 * 
 */
@Component
public class ServiceTimeTask {

	private static final Logger logger = Logger.getLogger(ServiceTimeTask.class);

	@Resource
	private EmployeeService employeeService;

	/**
	 * 方法注释
	 */
	public void testTimeTask() {

		try {
			System.out.println("ServiceTimeTask--------开始--------------------");
			insert();
			System.out.println("ServiceTimeTask---------结束--------------------");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}

	}

	public void insert() {
		try {
			Employee emp = new Employee();
			emp.setEmpage("26");
			emp.setEmpname("root");
			emp.setEmppass("root");
			emp.setEmpsex("男");
			employeeService.insert(emp);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
