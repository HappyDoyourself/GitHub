package com.ninedrug.search.business.mapper;

import java.util.List;

import com.ninedrug.search.business.base.Pageable;
import com.ninedrug.search.business.model.extend.CardProductModel;

public interface CardProductModelMapper {
	
	
	/**
	 * 根据商品ID或者卡ID查询商品关联的卡列表
	 * @param pageable
	 * @return
	 */
	public List<CardProductModel> selectProductCardList(Pageable<CardProductModel> pageable);
	
	
	
	
	
}
