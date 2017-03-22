package com.ninedrug.search.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.commonTermsQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;
import static org.elasticsearch.index.query.QueryBuilders.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.ninedrug.search.business.base.BaseServiceImpl;
import com.ninedrug.search.business.mapper.ProductMapper;
import com.ninedrug.search.business.model.document.ProductDocument;
import com.ninedrug.search.business.model.extend.ProductModel;
import com.ninedrug.search.business.service.ProductService;


@Service
public class ProductServiceImpl extends BaseServiceImpl implements ProductService  {
	
	
	@Autowired
	ProductMapper productMapper;
	
	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;
	
	public List<ProductModel> getProductListPage(ProductModel productModel){
		return productMapper.getProductListPage(productModel) ;
	}
	
	public ProductModel  getProductByProductId(ProductModel productModel){
		return productMapper.getProductByProductId(productModel);
	}
	
	/**
	 * 查询无库存的商品更新搜索索引
	 * @param productModel
	 * @return
	 */
	public List<ProductModel> deleteProductStock(ProductModel productModel){
		return productMapper.deleteProductStock(productModel);
	}
	
	
	/**
	 * 多条件组合查询
	 * 根据product_name 查询商品分页集合
     * 默认是按商品名称搜索 匹配度降序 
     * orderBy ： 1：售价降序，2：售价升序
     * 搜索关键词 : k  1：售价降序，2：售价升序，3：销量降序，4：综合
     * 品牌ID搜索 : b  123,124
	 * @param productModel
	 * @param pageable
	 * @return
	 */
	public Page<ProductDocument>  getWapProductPageList(ProductModel productModel,Pageable pageable){
		//创建查询条件
		NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
		
		searchQueryBuilder.withQuery(createWapSearchQueryBuilder(productModel));
		
		//分页  及  排序
		searchQueryBuilder.withPageable(pageable);
		SearchQuery searchQuery = searchQueryBuilder.build();
		
		Page<ProductDocument>  page = elasticsearchTemplate.queryForPage(searchQuery,ProductDocument.class);
		return page;
	}
	
	
	/**
	 * 多条件组合查询
	 * 根据product_name 查询商品分页集合
     * 默认是按商品名称搜索 匹配度降序 
     * orderBy ： 1：售价降序，2：售价升序
     * 搜索关键词 : k  1：售价降序，2：售价升序，3：销量降序，4：综合
     * 品牌ID搜索 : b  123,124
	 * @param productModel
	 * @param pageable
	 * @return
	 */
	public Page<ProductDocument>  getPCProductPageList(ProductModel productModel,Pageable pageable){
		//创建查询条件
		NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
		
		searchQueryBuilder.withQuery(createPCSearchQueryBuilder(productModel));
		
		//分页  及  排序
		searchQueryBuilder.withPageable(pageable);
		SearchQuery searchQuery = searchQueryBuilder.build();
		Page<ProductDocument>  page = elasticsearchTemplate.queryForPage(searchQuery,ProductDocument.class);
		return page;
	}
	
	
	
	/**
	 * 查询关键词 关联的品牌列表
	 * @param productModel
	 * @return
	 */
	/**
	 * 查询关键词 关联的品牌列表
	 * @param productModel
	 * @return
	 */
	public List<Map<String,Object>> getBrandList(ProductModel productModel){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		//创建查询条件
		SearchResponse response = null;
		try {
			SearchRequestBuilder resquest  = elasticsearchTemplate.getClient().prepareSearch();
			
			//创建查询条件
			resquest.setQuery(createWapSearchQueryBuilder(productModel));
			
			//分组查询 相当于            group by 
			resquest.addAggregation(
				            AggregationBuilders.terms("productBrandName2_count").field("productBrandName2")
				            .subAggregation(AggregationBuilders.terms("productBrandId2_count").field("productBrandId2"))
				            .size(0)
				    );
			response =  resquest.execute().actionGet();
		} catch (Exception e) {
			response = elasticsearchTemplate.getClient().prepareSearch()
				    .setQuery(matchAllQuery())
				    .addAggregation(
				            AggregationBuilders.terms("productBrandName2_count").field("productBrandName2")
				            .subAggregation(AggregationBuilders.terms("productBrandId2_count").field("productBrandId2"))
				            .size(0)
				    )
				    .execute().actionGet();
		}
		
		Map<String, Aggregation> aggMap = response.getAggregations().asMap();
		Terms teamAgg1 = (Terms) aggMap.get("productBrandName2_count");
		
		Iterator<Bucket> teamBucketIt = teamAgg1.getBuckets().iterator();
		while (teamBucketIt.hasNext()) {
			Map<String,Object> map = new HashMap<String,Object>();
			
			Bucket buck = teamBucketIt.next();
			String keyName = buck.getKeyAsString();
			long count = buck.getDocCount();
			
			map.put("productBrandName", keyName);
			
			//得到所有子聚合
			Map aggMap2 = buck.getAggregations().asMap();
			Terms teamAgg2 = (Terms) aggMap2.get("productBrandId2_count");
			
			Iterator<Bucket> teamBucketIt2 = teamAgg2.getBuckets().iterator();
			while (teamBucketIt2.hasNext()) {
				
				Bucket buck2 = teamBucketIt2.next();
				String keyName2 = buck2.getKeyAsString();
				long count2 = buck2.getDocCount();
				
				map.put("productBrandId", keyName2);
			}
			list.add(map);
		}
		return list;
	}
	
	
	/**
	 * 查询关键词 关联的品牌列表
	 * @param productModel
	 * @return
	 */
	public List<Map<String,Object>> getBrandList2(ProductModel productModel){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		//创建查询条件
		SearchResponse response = null;
		try {
			SearchRequestBuilder resquest  = elasticsearchTemplate.getClient().prepareSearch();
			
			if(!StringUtils.isEmpty(productModel.getK())){
				resquest.setQuery(boolQuery().must(commonTermsQuery("productSearchStr", productModel.getK())));
			}
		    //多个品牌ID搜索
			if(productModel.getProductBrandIds() != null){
				resquest.setQuery(boolQuery().must(termsQuery("productBrandId", productModel.getProductBrandIds())));
		    }
			
			//价格区间
			RangeQueryBuilder rang = null;
			if((productModel.getMinPrice() != null 
				&& productModel.getMinPrice() > 0) 
					|| (productModel.getMaxPrice() != null 
						&& productModel.getMaxPrice() > 0 )){
				rang = rangeQuery("ecPrice");
				
				if(productModel.getMinPrice() != null 
						&& productModel.getMinPrice() > 0 ){
					rang.from(productModel.getMinPrice());
				}
				if(productModel.getMaxPrice() != null 
						&& productModel.getMaxPrice() > 0 ){
					rang.to(productModel.getMaxPrice());
				}
		    }
			if(rang != null){
				resquest.setQuery(boolQuery().must(rang));
			}
			
			//显示分类查询   
			if(productModel.getProCatalogIds() != null  && productModel.getProCatalogIds().size() > 0){
				resquest.setQuery(boolQuery().must(termsQuery("proCatalogId", productModel.getProCatalogIds())));
		    }
			
			//分组查询 相当于            group by 
			resquest.addAggregation(
				            AggregationBuilders.terms("productBrandName2_count").field("productBrandName2")
				            .subAggregation(AggregationBuilders.terms("productBrandId2_count").field("productBrandId2"))
				            .size(0)
				    );
			response =  resquest.execute().actionGet();
		} catch (Exception e) {
			response = elasticsearchTemplate.getClient().prepareSearch()
				    .setQuery(matchAllQuery())
				    .addAggregation(
				            AggregationBuilders.terms("productBrandName2_count").field("productBrandName2")
				            .subAggregation(AggregationBuilders.terms("productBrandId2_count").field("productBrandId2"))
				            .size(0)
				    )
				    .execute().actionGet();
		}
		
		Map<String, Aggregation> aggMap = response.getAggregations().asMap();
		Terms teamAgg1 = (Terms) aggMap.get("productBrandName2_count");
		
		Iterator<Bucket> teamBucketIt = teamAgg1.getBuckets().iterator();
		while (teamBucketIt.hasNext()) {
			Map<String,Object> map = new HashMap<String,Object>();
			
			Bucket buck = teamBucketIt.next();
			String keyName = buck.getKeyAsString();
			long count = buck.getDocCount();
			
			map.put("productBrandName", keyName);
			
			//得到所有子聚合
			Map aggMap2 = buck.getAggregations().asMap();
			Terms teamAgg2 = (Terms) aggMap2.get("productBrandId2_count");
			
			Iterator<Bucket> teamBucketIt2 = teamAgg2.getBuckets().iterator();
			while (teamBucketIt2.hasNext()) {
				
				Bucket buck2 = teamBucketIt2.next();
				String keyName2 = buck2.getKeyAsString();
				long count2 = buck2.getDocCount();
				
				map.put("productBrandId", keyName2);
			}
			list.add(map);
		}
		return list;
	}
	

	/**
	 * 
	 * 搜索条件共用 参数
	 * @return
	 * 
	 */
	public NativeSearchQueryBuilder createSearchQueryBuilder2(ProductModel productModel){
		
		NativeSearchQueryBuilder searchQueryBuilder =  new NativeSearchQueryBuilder();
		
		//关键词搜索  现在只搜索商品名称 
		if(!StringUtils.isEmpty(productModel.getK())){
			//能搜索欧姆龙
			searchQueryBuilder.withQuery(commonTermsQuery("productSearchStr", productModel.getK()));
		}
	    //多个品牌ID搜索
		if(productModel.getProductBrandIds() != null && productModel.getProductBrandIds().size() > 0){
	    	searchQueryBuilder.withQuery(boolQuery().filter(termsQuery("productBrandId", productModel.getProductBrandIds())));
	    }
//		if(productModel.getProductBrandId() != null){
//	    	searchQueryBuilder.withQuery(boolQuery().filter(termQuery("productBrandId", productModel.getProductBrandId())));
//	    }
		
		//价格区间
		RangeQueryBuilder rang = null;
		if((productModel.getMinPrice() != null 
			&& productModel.getMinPrice() > 0) 
				|| (productModel.getMaxPrice() != null 
					&& productModel.getMaxPrice() > 0 )){
			rang = rangeQuery("ecPrice");
			
			if(productModel.getMinPrice() != null 
					&& productModel.getMinPrice() > 0 ){
				rang.from(productModel.getMinPrice());
			}
			if(productModel.getMaxPrice() != null 
					&& productModel.getMaxPrice() > 0 ){
				rang.to(productModel.getMaxPrice());
			}
	    }
		if(rang != null){
			searchQueryBuilder.withQuery(boolQuery().filter(rang));
		}
		
		//显示分类查询   
		if(productModel.getProCatalogIds() != null && productModel.getProCatalogIds().size() > 0){
	    	searchQueryBuilder.withQuery(boolQuery().filter(termsQuery("proCatalogId", productModel.getProCatalogIds())));
	    }
//		if(productModel.getProCatalogId() != null){
//	    	searchQueryBuilder.withQuery(boolQuery().filter(termQuery("proCatalogId", productModel.getProCatalogId())));
//	    }
		
		return searchQueryBuilder;
	}
	
	
	/**
	 * 创建Wap
	 * 
	 * 搜索条件共用 参数
	 * @return
	 * 
	 */
	public BoolQueryBuilder createWapSearchQueryBuilder(ProductModel productModel){
		
		BoolQueryBuilder bool = boolQuery();
		if(!StringUtils.isEmpty(productModel.getK())){
			bool.should(commonTermsQuery("productSearchStr", productModel.getK()));
			bool.must(matchQuery("productSearchStr", productModel.getK()).minimumShouldMatch("100%"));
		}
		
		if(productModel.getProductBrandIds() != null && productModel.getProductBrandIds().size() > 0){
			bool.must(termsQuery("productBrandId", productModel.getProductBrandIds()));
	    }
		
		if(productModel.getProCatalogIds() != null && productModel.getProCatalogIds().size() > 0){
			bool.must(termsQuery("productId", productModel.getProCatalogIds()));
		}
		
		if(productModel.getCouponProductIds() != null && productModel.getCouponProductIds().size() > 0){
			//判断优惠券类型   使用范围类型(1-全部,2-商品分类,3-具体商品) 
			if("1".equals(productModel.getApplyRange())){
				bool.mustNot(termsQuery("productId", productModel.getCouponProductIds()));
			}else{
				bool.must(termsQuery("productId", productModel.getCouponProductIds()));
			}
		}
		
		//价格区间
		RangeQueryBuilder rang = null;
		if((productModel.getMinPrice() != null 
			&& productModel.getMinPrice() > 0) 
				|| (productModel.getMaxPrice() != null 
					&& productModel.getMaxPrice() > 0 )){
			rang = rangeQuery("ecPrice");
			
			if(productModel.getMinPrice() != null 
					&& productModel.getMinPrice() > 0 ){
				rang.from(productModel.getMinPrice());
			}
			if(productModel.getMaxPrice() != null 
					&& productModel.getMaxPrice() > 0 ){
				rang.to(productModel.getMaxPrice());
			}
	    }
		if(rang != null){
			bool.must(rang);
		}
		
		return bool;
	}
	
	

	/**
	 * 创建PC
	 * 搜索条件共用 参数
	 * @return
	 * 
	 */
	public BoolQueryBuilder createPCSearchQueryBuilder(ProductModel productModel){
		
		BoolQueryBuilder bool = boolQuery();
		if(!StringUtils.isEmpty(productModel.getK())){
			bool.should(commonTermsQuery("productSearchStr", productModel.getK()));
			bool.must(matchQuery("productSearchStr", productModel.getK()).minimumShouldMatch("100%"));
		}
		
		if(productModel.getProductBrandIds() != null && productModel.getProductBrandIds().size() > 0){
			bool.must(termsQuery("productBrandId", productModel.getProductBrandIds()));
	    }
		
		if(productModel.getProCatalogIds() != null && productModel.getProCatalogIds().size() > 0){
			bool.must(termsQuery("productId", productModel.getProCatalogIds()));
		}
		
		//价格区间
		RangeQueryBuilder rang = null;
		if((productModel.getMinPrice() != null 
			&& productModel.getMinPrice() > 0) 
				|| (productModel.getMaxPrice() != null 
					&& productModel.getMaxPrice() > 0 )){
			rang = rangeQuery("ecPrice");
			
			if(productModel.getMinPrice() != null 
					&& productModel.getMinPrice() > 0 ){
				rang.from(productModel.getMinPrice());
			}
			if(productModel.getMaxPrice() != null 
					&& productModel.getMaxPrice() > 0 ){
				rang.to(productModel.getMaxPrice());
			}
	    }
		if(rang != null){
			bool.must(rang);
		}
		
		return bool;
	}
	
	
	
}
