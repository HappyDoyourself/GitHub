package com.ninedrug.search.api.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ninedrug.search.business.base.ServiceMessages;
import com.ninedrug.search.business.model.document.CardProductDocument;
import com.ninedrug.search.business.model.extend.CardProductModel;
import com.ninedrug.search.business.model.extend.ProductModel;
import com.ninedrug.search.business.repositories.CardProductRepository;
import com.ninedrug.search.business.service.CardProductService;

/**
 * 商品支持一些第三方卡支付管理
 */
@Controller
@RequestMapping("/card")
public class CardController extends BaseController {

    Logger logger = LoggerFactory.getLogger(CardController.class);
 
    
    @Autowired
    private CardProductService cardProductService; 
    
    @Autowired
    private CardProductRepository cardProductRepository; 
    
    /***
     * 根据 product_id 单个生成索引
     * 如果已经存在则会更新
     * @param productModel
     * @return
     */
    @RequestMapping(value = "/saveOrUpdate/{productId}", produces = "application/json")
    @ResponseBody
    public ServiceMessages<String> productDocumentCreateOne(@PathVariable("productId") Long productId){
    	try{
    		//查询所有的商品
    		CardProductModel cardProductModel = new CardProductModel();
    		cardProductModel.setProductId(productId);
    		List<CardProductModel> list = cardProductService.selectCardProductListById(cardProductModel);
        	
    		if(list != null && list.size() > 0){
    			for(CardProductModel ca : list){
        			CardProductDocument pd = new CardProductDocument();
            		BeanUtils.copyProperties(ca, pd);
        			//如果此id 已经存在 则先删除
            		if(cardProductRepository.exists(productId.toString())){
            			cardProductRepository.delete(productId.toString());
            		}
            		cardProductRepository.save(pd);
        		}
    		}
    		
    	}catch(Exception e){
    		logger.error(e.getMessage());
    		return returnError(e.getMessage(), null);
    	}
    	return returnSuc(productId+"商品关联卡索引更新成功！", null);
    }
    
    
    /**
     * 批量生成索引
     * 
     * @param productModel
     * @return
     */
    @RequestMapping(value = "/saveOrUpdate/batch",method=RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ServiceMessages<String> productDocumentCreateBatch1(CardProductModel cardProductModel){
    	try{
    		if(StringUtils.isEmpty(cardProductModel.getProductIdsStr())){
    			return returnErrorParam("缺少 ProductIdsStr 参数", null);
    		}
    		
    		List<Long> productIds = new ArrayList<Long>();
    		for(String str : cardProductModel.getProductIdsStr().split(",")){
    			productIds.add(Long.valueOf(str));
    		}
    		cardProductModel.setProductIds(productIds);
    		
    		Boolean flag = Boolean.TRUE;
    		int page = 1;
    		int size = 100;
    		do{
    			//查询所有的商品
        		List<CardProductModel> list = cardProductService.selectCardProductList(page,size,cardProductModel);
            	
        		if(list != null && list.size() > 0){
        			for(CardProductModel ca : list){
            			CardProductDocument pd = new CardProductDocument();
                		BeanUtils.copyProperties(ca, pd);
            			//如果此id 已经存在 则先删除
                		if(cardProductRepository.exists(pd.getId())){
                			cardProductRepository.delete(pd.getId());
                		}
                		cardProductRepository.save(pd);
            		}
        			page += 1;
        		}else{
        			flag = Boolean.FALSE;
        		}
    			
    		}while(flag);
    	}catch(Exception e){
    		logger.error(e.getMessage());
    		return returnError(e.getMessage(), null);
    	}
    	return returnSuc("批量生成商品关联卡索引成功", null);
    }
    
    
    /**
     * 批量生成索引
     * 
     * @param productModel
     * @return
     */
    @RequestMapping(value = "/saveOrUpdate/all", produces = "application/json")
    @ResponseBody
    public ServiceMessages<String> productDocumentCreateBatch(CardProductModel cardProductModel){
    	try{
    		//先删除所有的 然后再重新创建
    		cardProductRepository.deleteAll();
    		
    		Boolean flag = Boolean.TRUE;
    		int page = 1;
    		int size = 100;
    		do{
    			//查询所有的商品
        		List<CardProductModel> list = cardProductService.selectCardProductList(page,size,cardProductModel);
            	
        		if(list != null && list.size() > 0){
        			for(CardProductModel ca : list){
            			CardProductDocument pd = new CardProductDocument();
                		BeanUtils.copyProperties(ca, pd);
            			//如果此id 已经存在 则先删除
                		if(cardProductRepository.exists(pd.getId())){
                			cardProductRepository.delete(pd.getId());
                		}
                		cardProductRepository.save(pd);
            		}
        			page += 1;
        		}else{
        			flag = Boolean.FALSE;
        		}
    			
    		}while(flag);
    	}catch(Exception e){
    		logger.error(e.getMessage());
    		return returnError(e.getMessage(), null);
    	}
    	return returnSuc("重建商品关联卡索引成功", null);
    }
    
    /**
     * 根据product_id删除单个索引
     * 
     * @param productModel
     * @return
     */
    @RequestMapping(value = "/delete/{productId}/{cardId}",method=RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ServiceMessages<String> productDocumentDeleteOne(@PathVariable String productId,@PathVariable String cardId){
    	try{
    		//查询所有的商品
    		if(cardProductRepository.exists(productId+"_"+cardId)){
    			cardProductRepository.delete(productId+"_"+cardId);
    		}
    	}catch(Exception e){
    		logger.error(e.getMessage());
    		return returnError(e.getMessage(), null);
    	}
    	return returnSuc(productId+"商品关联卡索引删除成功！", null);
    }
    
    
    /**
     * 批量删除索引
     * 
     * @param productModel
     * @return
     */
    @RequestMapping(value = "/delete/batch",method=RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ServiceMessages<String> productDocumentDeleteBatch(ProductModel productModel){
    	try{
    		
    		if(StringUtils.isEmpty(productModel.getProductIdsStr())){
    			return returnErrorParam("缺少 ProductIdsStr 参数", null);
    		}
    		List<Long> productIds = new ArrayList<Long>();
    		for(String str : productModel.getProductIdsStr().split(",")){
    			//查询所有的商品
        		if(cardProductRepository.exists(str)){
        			cardProductRepository.delete(str);
        		}
    		}
    	}catch(Exception e){
    		logger.error(e.getMessage());
    		return returnError(e.getMessage(), null);
    	}
    	return returnSuc("批量删除商品关联卡索引成功", null);
    }
    
    
    /**
     * 删除所有索引
     * 
     * @param productModel
     * @return
     */
    @RequestMapping(value = "/delete/all",method=RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ServiceMessages<String> productDocumentDeleteAll(CardProductModel cardProductModel){
    	try{
    		cardProductRepository.deleteAll();
    	}catch(Exception e){
    		logger.error(e.getMessage());
    		return returnError(e.getMessage(), null);
    	}
    	return returnSuc("清空商品关联卡索引成功", null);
    }
    
    
    
}
