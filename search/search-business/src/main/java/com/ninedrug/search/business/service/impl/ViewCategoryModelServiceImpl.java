package com.ninedrug.search.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.ninedrug.search.business.base.BaseServiceImpl;
import com.ninedrug.search.business.mapper.ViewCategoryModelMapper;
import com.ninedrug.search.business.model.document.ProductCategoryViewBaseDocument;
import com.ninedrug.search.business.model.extend.ViewCategoryModel;
import com.ninedrug.search.business.service.ViewCategoryModelService;

/**
 * 
 * 显示分类 Service 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author Administrator
 * @date 2016年12月18日
 */
@SuppressWarnings("rawtypes")
@Service
public class ViewCategoryModelServiceImpl extends BaseServiceImpl implements ViewCategoryModelService {
	
	
	@Autowired
	ViewCategoryModelMapper  viewCategoryModelMapper;
	
	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;
	
	/**
	 * 根据ID查询显示分类
	 * 
	 * @param viewCategoryModel
	 * @return
	 */
	public ViewCategoryModel queryViewCategoryById(ViewCategoryModel viewCategoryModel){
		return viewCategoryModelMapper.queryViewCategoryById(viewCategoryModel);
	}
	
	
	/**
	 * 根据父级ID查询显示分类
	 * 
	 * @param viewCategoryModel
	 * @return
	 */
	public List<ViewCategoryModel> queryViewCategoryByParentId(ViewCategoryModel viewCategoryModel){
		return viewCategoryModelMapper.queryViewCategoryByParentId(viewCategoryModel);
	}
	
	/**
	 * 
	 * 
	 * @param viewCategoryModel
	 * @return
	 * 
	 */
	public List<Long> queryProductIdByCategory(ViewCategoryModel viewCategoryModel){
		
		List<Long>  list = new ArrayList<Long>();
		//创建查询条件
		NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
				
		searchQueryBuilder.withQuery(createViewCategorySearchQueryBuilder(viewCategoryModel));
		
		//分页  及  排序
		SearchQuery searchQuery = searchQueryBuilder.build();
		Page<ProductCategoryViewBaseDocument>  page = elasticsearchTemplate.queryForPage(searchQuery,ProductCategoryViewBaseDocument.class);
		for(ProductCategoryViewBaseDocument pa : page.getContent()){
			list.add(pa.getProductId());
		}
		return list;
	}
	
	public BoolQueryBuilder createViewCategorySearchQueryBuilder(ViewCategoryModel viewCategoryModel){
		BoolQueryBuilder bool = boolQuery();
		
		if(viewCategoryModel.getCatalogIds() != null && viewCategoryModel.getCatalogIds().size() > 0){
			bool.must(termsQuery("viewCateId", viewCategoryModel.getCatalogIds()));
		}
		return bool;
	}
	
	
}
