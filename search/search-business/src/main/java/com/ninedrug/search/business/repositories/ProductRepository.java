package com.ninedrug.search.business.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.ninedrug.search.business.model.document.ProductDocument;

/**
 * Created by yemengying on 16/1/10.
 */
public interface ProductRepository extends CrudRepository<ProductDocument, String> {

    List<ProductDocument> findByProductName(String productName);
    
    List<ProductDocument> findByProductId(String productId);
    
    List<ProductDocument> findByEcPrice(Float ecPrice);
    
    List<ProductDocument> findByProductNameLike(String productName);
    
    Page<ProductDocument> findByProductName(String productName,Pageable pageable);
    
    
    
    /**
     * 使用Query注解指定查询语句
     * @param userName
     * @param skuName
     * @return
     */
    //双引号和不加引号都可，不能是单引号
//    @Query("{bool : {must : [ {term : {userName : ?0}}, {term : {skuName : ?1}} ]}}")  //---   field查询已经废弃，可参考当前查询语法，已换成term查询
//    @Query("{\"bool\" : {\"must\" : [ {\"term\" : {\"skuName\" : \"?1\"}}, {\"term\" : {\"userName\" : \"?0\"}} ]}}")
//    //注意：需要替换的参数？需要加双引号；需要指定参数下标，从0开始
//    public Order findByUserNameAndSkuName2(String userName, String skuName);
    
    
    
}
