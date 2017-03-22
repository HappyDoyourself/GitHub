package com.ninedrug.search.business.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ninedrug.search.business.model.document.CardProductDocument;

/**
 * Created by yemengying on 16/1/10.
 */
public interface CardProductRepository extends CrudRepository<CardProductDocument, String> {

	List<CardProductDocument> findByProductId(Long productId);
	
}
