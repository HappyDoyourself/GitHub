package com.ninedrug.search.business.service;

import java.util.List;

import com.ninedrug.search.business.base.BaseService;
import com.ninedrug.search.business.base.Pageable;
import com.ninedrug.search.business.model.extend.CardProductModel;

public interface CardProductService extends BaseService {
	/**
	 * 根据商品ID或者卡ID查询商品关联的卡列表
	 * @param pageable
	 * @return
	 */
	public List<CardProductModel> selectCardProductList(int page,int size, CardProductModel cardProductModel);
	
	
	/**
	 * 根据商品ID或者卡ID查询商品关联的卡列表
	 * @param pageable
	 * @return
	 */
	public List<CardProductModel> selectCardProductListById(CardProductModel cardProductModel);
	
	
	
}
