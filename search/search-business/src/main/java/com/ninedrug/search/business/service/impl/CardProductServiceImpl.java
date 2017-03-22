package com.ninedrug.search.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ninedrug.search.business.base.BaseServiceImpl;
import com.ninedrug.search.business.base.PageRequest;
import com.ninedrug.search.business.base.Pageable;
import com.ninedrug.search.business.mapper.CardProductModelMapper;
import com.ninedrug.search.business.model.extend.CardProductModel;
import com.ninedrug.search.business.service.CardProductService;


@Service
public class CardProductServiceImpl extends BaseServiceImpl implements CardProductService  {
	
	@Autowired
	CardProductModelMapper cardProductModelMapper;
	
	/**
	 * 根据商品ID或者卡ID查询商品关联的卡列表
	 * @param pageable
	 * @return
	 */
	public List<CardProductModel> selectCardProductList(int page,int size, CardProductModel cardProductModel){
		Pageable<CardProductModel> pageable =new PageRequest<CardProductModel>(page, size, cardProductModel);
		return cardProductModelMapper.selectProductCardList(pageable);
	}
	
	/**
	 * 
	 * 根据商品ID或者卡ID查询商品关联的卡列表
	 * @param pageable
	 * @return
	 * 
	 */
	public List<CardProductModel> selectCardProductListById(CardProductModel cardProductModel){
		Pageable<CardProductModel> pageable =new PageRequest<CardProductModel>(0, 0, cardProductModel);
		return cardProductModelMapper.selectProductCardList(pageable);
	}
	
}
