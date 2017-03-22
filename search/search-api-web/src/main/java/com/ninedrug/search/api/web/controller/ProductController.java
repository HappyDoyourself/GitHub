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
import com.ninedrug.search.business.model.document.ProductDocument;
import com.ninedrug.search.business.model.extend.ProductModel;
import com.ninedrug.search.business.repositories.ProductRepository;
import com.ninedrug.search.business.service.ProductService;

/**
 * Created by yemengying on 16/1/10.
 */
@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {

    Logger logger = LoggerFactory.getLogger(ProductController.class);
 
    
    @Autowired
    private ProductService productService; 
    
    @Autowired
    private ProductRepository productRepository; 
    
    /***
     * 根据 product_id 单个生成索引
     * 如果已经存在则会更新
     * @param productModel
     * @return
     */
    @RequestMapping(value = "/saveOrUpdate/{productId}",method=RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ServiceMessages<String> productDocumentCreateOne(@PathVariable("productId") Long productId){
    	try{
    		//查询所有的商品
    		ProductModel productModel = new ProductModel();
    		productModel.setProductId(productId);
    		ProductModel one = productService.getProductByProductId(productModel);
        	
    		ProductDocument pd = new ProductDocument();
    		BeanUtils.copyProperties(one, pd);
    		pd.setProductBrandName2(one.getProductBrandName());
    		pd.setProductBrandId2(one.getProductBrandId());
    		
    		
        	//如果此id 已经存在 则先删除
    		if(productRepository.exists(productId.toString())){
    			productRepository.delete(productId.toString());
    		}
    		productRepository.save(pd);
    	}catch(Exception e){
    		logger.error(e.getMessage());
    		return returnError(e.getMessage(), null);
    	}
    	return returnSuc(productId+"商品索引更新成功！", null);
    }
    
    
    /**
     * 批量生成索引
     * 
     * @param productModel
     * @return
     */
    @RequestMapping(value = "/saveOrUpdate/batch",method=RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ServiceMessages<String> productDocumentCreateBatch1(ProductModel productModel){
    	try{
    		
    		if(StringUtils.isEmpty(productModel.getProductIdsStr())){
    			return returnErrorParam("缺少 ProductIdsStr 参数", null);
    		}
    		
    		List<Long> productIds = new ArrayList<>();
    		for(String str : productModel.getProductIdsStr().split(",")){
    			productIds.add(Long.valueOf(str));
    		}
    		productModel.setProductIds(productIds);
    		
    		Boolean flag = Boolean.TRUE;
    		int page = 1;
    		int size = 100;
    		do{
    			productModel.setSize(size);
    			productModel.setPage(page);
    			
    			//查询所有的商品
            	List<ProductModel> productList = productService.getProductListPage(productModel);
    			if(productList != null && productList.size() > 0){
    				for(ProductModel pm : productList){
                		ProductDocument pd = new ProductDocument();
                		BeanUtils.copyProperties(pm, pd);
                		pd.setProductBrandName2(pm.getProductBrandName());
                		pd.setProductBrandId2(pm.getProductBrandId());
                		if(productRepository.exists(pd.getId())){
                			productRepository.delete(pd.getId());
                		}
                		productRepository.save(pd);
                	}
    				page += 1 ;
    			}else{
    				flag = Boolean.FALSE;
    			}
    			
    		}while(flag);
    	}catch(Exception e){
    		logger.error(e.getMessage());
    		return returnError(e.getMessage(), null);
    	}
    	return returnSuc("批量生成索引成功", null);
    }
    
    
    /**
     * 批量生成索引
     * 
     * @param productModel
     * @return
     */
    @RequestMapping(value = "/saveOrUpdate/all", produces = "application/json")
    @ResponseBody
	@SuppressWarnings(value = "")
    public ServiceMessages<String> productDocumentCreateBatch(ProductModel productModel){
    	try{
    		//先删除所有的 然后再重新创建
    		productRepository.deleteAll();
    		
    		Boolean flag = Boolean.TRUE;
    		int page = 1;
    		int size = 100;
    		do{
    			productModel.setSize(size);
    			productModel.setPage(page);
    			
    			//查询所有的商品
            	List<ProductModel> productList = productService.getProductListPage(productModel);
    			if(productList != null && productList.size() > 0){
    				
    				for(ProductModel pm : productList){
                		ProductDocument pd = new ProductDocument();
                		BeanUtils.copyProperties(pm, pd);
                		pd.setProductBrandName2(pm.getProductBrandName());
                		pd.setProductBrandId2(pm.getProductBrandId());
                		
                		if(productRepository.exists(pd.getId())){
                			productRepository.delete(pd.getId());
                		}
                		productRepository.save(pd);
                	}
    				page += 1 ;
    			}else{
    				flag = Boolean.FALSE;
    			}
    			
    		}while(flag);
    	}catch(Exception e){
    		logger.error(e.getMessage());
    		return returnError(e.getMessage(), null);
    	}
    	return returnSuc("重建索引成功", null);
    }
    
    /**
     * 根据product_id删除单个索引
     * 
     * @param productModel
     * @return
     */
    @RequestMapping(value = "/delete/{productId}",method=RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ServiceMessages<String> productDocumentDeleteOne(@PathVariable String productId){
    	try{
    		//查询所有的商品
    		if(productRepository.exists(productId)){
    			productRepository.delete(productId);
    		}
    	}catch(Exception e){
    		logger.error(e.getMessage());
    		return returnError(e.getMessage(), null);
    	}
    	return returnSuc(productId+"商品索引删除成功！", null);
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
    		//List<Long> productIds = new ArrayList<Long>();
    		for(String str : productModel.getProductIdsStr().split(",")){
    			//查询所有的商品
        		if(productRepository.exists(str)){
        			productRepository.delete(str);
        		}
    		}
    	}catch(Exception e){
    		logger.error(e.getMessage());
    		return returnError(e.getMessage(), null);
    	}
    	return returnSuc("批量删除索引成功", null);
    }
    
    
    /**
     * 删除所有索引
     * 
     * @param productModel
     * @return
     */
    @RequestMapping(value = "/delete/all",method=RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ServiceMessages<String> productDocumentDeleteAll(ProductModel productModel){
    	try{
    		productRepository.deleteAll();
    	}catch(Exception e){
    		logger.error(e.getMessage());
    		return returnError(e.getMessage(), null);
    	}
    	return returnSuc("清空商品索引成功", null);
    }
    
    
    
    /**
     * 更新所有的商品索引
     * 下回 及没有库存的商品 删除掉
     * @param productModel
     * @return
     */
    @RequestMapping(value = "/delete/stock",method=RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ServiceMessages<String> updateAllProductDocument(){
    	try{
    		Boolean flag = Boolean.TRUE;
    		int page = 1;
    		int size = 100;
    		do{
    			ProductModel productModel = new ProductModel();
    			productModel.setSize(size);
    			productModel.setPage(page);
    			
    			//查询所有的商品
            	List<ProductModel> productList = productService.deleteProductStock(productModel);
    			if(productList != null && productList.size() > 0){
    				
    				for(ProductModel pm : productList){
                		ProductDocument pd = new ProductDocument();
                		BeanUtils.copyProperties(pm, pd);
                		
                		//如果已经没有库存则删除索引
                		if(productRepository.exists(pd.getId())){
                			productRepository.delete(pd.getId());
                		}
                	}
    				page += 1 ;
    			}else{
    				flag = Boolean.FALSE;
    			}
    			
    		}while(flag);
    	}catch(Exception e){
    		logger.error(e.getMessage());
    		return returnError(e.getMessage(), null);
    	}
    	return returnSuc("重建索引成功", null);
    }
    
    
}
