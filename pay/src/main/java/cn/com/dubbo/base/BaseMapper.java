package cn.com.dubbo.base;

import java.io.Serializable;

public interface BaseMapper<T,PK extends Serializable> {

	int deleteByPrimaryKey(PK deptid);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(PK deptid);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);
}
