package cn.com.dubbo.base;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractBaseServiceImpl<T,PK extends Serializable> implements BaseService<T, PK>{

	private BaseMapper<T, PK> baseMapper;

	public void setBaseMapper(BaseMapper<T, PK> baseMapper) {
		this.baseMapper = baseMapper;
	}

	@Override
	public int deleteByPrimaryKey(PK deptid) {
		int num = this.baseMapper.deleteByPrimaryKey(deptid);
		return num;
	}

	@Override
	public int insert(T record) {
		return this.baseMapper.insert(record);
	}

	@Override
	public int insertSelective(T record) {
		return this.baseMapper.insertSelective(record);
	}

	@Override
	public T selectByPrimaryKey(PK deptid) {
		return this.baseMapper.selectByPrimaryKey(deptid);
	}

	@Override
	public int updateByPrimaryKeySelective(T record) {
		return this.baseMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(T record) {
		return this.baseMapper.updateByPrimaryKey(record);
	}
}
