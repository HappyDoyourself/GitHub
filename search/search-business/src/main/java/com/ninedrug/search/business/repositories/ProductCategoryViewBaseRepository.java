package com.ninedrug.search.business.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ninedrug.search.business.model.document.ProductCategoryViewBaseDocument;

/**
 * Created by yemengying on 16/1/10.
 */
public interface ProductCategoryViewBaseRepository extends CrudRepository<ProductCategoryViewBaseDocument, String> {

	
    /**
     * 
     * 根据分类ID查询所有的产品ID
     * 
     * @param catalogId
     * @return
     */
	List<ProductCategoryViewBaseDocument> findByViewCateId(Long viewCateId);
	
	
}
