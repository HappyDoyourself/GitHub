package cn.com.dubbo.mapper;

import cn.com.dubbo.bean.Employee;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Long empid);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Long empid);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);
}