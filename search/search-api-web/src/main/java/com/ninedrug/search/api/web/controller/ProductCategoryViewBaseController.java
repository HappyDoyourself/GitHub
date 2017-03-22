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
import com.ninedrug.search.business.model.document.ProductCategoryViewBaseDocument;
import com.ninedrug.search.business.model.extend.ProductCategoryViewBaseModel;
import com.ninedrug.search.business.repositories.ProductCategoryViewBaseRepository;
import com.ninedrug.search.business.service.ProductCategoryViewBaseService;

/**
 * 
 * 
 * 移动显示分类索引管理
 * 
 * 
 */
@Controller
@RequestMapping("/productViewCategory")
public class ProductCategoryViewBaseController extends BaseController {
	
    Logger logger = LoggerFactory.getLogger(ProductCategoryViewBaseController.class);
    
    @Autowired
    private ProductCategoryViewBaseService productCategoryViewBaseService; 
    
    @Autowired
    private ProductCategoryViewBaseRepository productCategoryViewBaseRepository; 
    
    /***
     * 根据 viewCateId 单个生成索引
     * 如果已经存在则会更新
     * @param productModel
     * @return
     */
    @RequestMapping(value = "/saveOrUpdate/{viewCateId}",method=RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ServiceMessages<String> productDocumentCreateOne(@PathVariable("viewCateId") Long viewCateId){
    	try{
    		//先删除原来分类索引
    		List<ProductCategoryViewBaseDocument> list1 = productCategoryViewBaseRepository.findByViewCateId(viewCateId);
    		for(ProductCategoryViewBaseDocument doc : list1){
    			if(productCategoryViewBaseRepository.exists(doc.getId())){
        			productCategoryViewBaseRepository.delete(doc.getId());
        		}
    		}
    		
    		//查询所有的商品
    		List<ProductCategoryViewBaseModel> list = productCategoryViewBaseService.getByViewCategoryId(viewCateId);
        	
    		for(ProductCategoryViewBaseModel one : list){
    			ProductCategoryViewBaseDocument pd = new ProductCategoryViewBaseDocument();
        		BeanUtils.copyProperties(one, pd);
        		
        		//如果此id 已经存在 则先删除
        		if(productCategoryViewBaseRepository.exists(pd.getId())){
        			productCategoryViewBaseRepository.delete(pd.getId());
        		}
        		productCategoryViewBaseRepository.save(pd);
    		}
    	}catch(Exception e){
    		logger.error(e.getMessage());
    		return returnError(e.getMessage(), null);
    	}
    	return returnSuc(viewCateId+"显示分类索引更新成功！", null);
    }
    
    
    /**
     * 批量生成索引
     * 
     * @param productModel
     * @return
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveOrUpdate/batch",method=RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ServiceMessages<String> productDocumentCreateBatch1(String viewCateIdsStr){
    	try{
    		
    		if(StringUtils.isEmpty(viewCateIdsStr)){
    			return returnErrorParam("缺少 viewCateIdsStr 参数", null);
    		}
    		
    		ProductCategoryViewBaseModel productCategoryViewBaseModel = new ProductCategoryViewBaseModel();
    		
    		List<Long> viewCateIds = new ArrayList<Long>();
    		for(String str : viewCateIdsStr.split(",")){
    			viewCateIds.add(Long.valueOf(str));
    		}
    		productCategoryViewBaseModel.setViewCateIds(viewCateIds);
    		
    		//先删除原来分类索引
    		for(Long view : viewCateIds){
    			List<ProductCategoryViewBaseDocument> list1 = productCategoryViewBaseRepository.findByViewCateId(view);
        		for(ProductCategoryViewBaseDocument doc : list1){
        			if(productCategoryViewBaseRepository.exists(doc.getId())){
            			productCategoryViewBaseRepository.delete(doc.getId());
            		}
        		}
    		}
    		
    		
    		Boolean flag = Boolean.TRUE;
    		int page = 1;
    		int size = 300;
    		do{
    			
    			//查询所有的商品显示分类 
            	List<ProductCategoryViewBaseModel> pcvbList = productCategoryViewBaseService.getProductCategoryViewBaseListPage(page,size,productCategoryViewBaseModel);
    			if(pcvbList != null && pcvbList.size() > 0){
    				for(ProductCategoryViewBaseModel one : pcvbList){
                		ProductCategoryViewBaseDocument pd = new ProductCategoryViewBaseDocument();
                		BeanUtils.copyProperties(one, pd);
                		//如果此id 已经存在 则先删除
                		if(productCategoryViewBaseRepository.exists(pd.getId())){
                			productCategoryViewBaseRepository.delete(pd.getId());
                		}
                		productCategoryViewBaseRepository.save(pd);
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
    public ServiceMessages<String> productDocumentCreateBatch(){
    	try{
    		//先删除所有的 然后再重新创建
    		productCategoryViewBaseRepository.deleteAll();
    		
    		Boolean flag = Boolean.TRUE;
    		int page = 1;
    		int size = 300;
    		do{
    			
    			//查询所有的商品显示分类 
            	List<ProductCategoryViewBaseModel> pcvbList = productCategoryViewBaseService.getProductCategoryViewBaseListPage(page,size,new ProductCategoryViewBaseModel());
    			if(pcvbList != null && pcvbList.size() > 0){
    				for(ProductCategoryViewBaseModel one : pcvbList){
                		ProductCategoryViewBaseDocument pd = new ProductCategoryViewBaseDocument();
                		BeanUtils.copyProperties(one, pd);
                		//如果此id 已经存在 则先删除
                		if(productCategoryViewBaseRepository.exists(pd.getId())){
                			productCategoryViewBaseRepository.delete(pd.getId());
                		}
                		productCategoryViewBaseRepository.save(pd);
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
    @RequestMapping(value = "/delete/{viewCateId}",method=RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ServiceMessages<String> productDocumentDeleteOne(@PathVariable("viewCateId") Long viewCateId){
    	try{
    		//查询所有的商品
    		List<ProductCategoryViewBaseModel> list = productCategoryViewBaseService.getByViewCategoryId(viewCateId);
        	
    		for(ProductCategoryViewBaseModel one : list){
    			ProductCategoryViewBaseDocument pd = new ProductCategoryViewBaseDocument();
        		BeanUtils.copyProperties(one, pd);
        		
        		//如果此id 已经存在 则先删除
        		if(productCategoryViewBaseRepository.exists(pd.getId())){
        			productCategoryViewBaseRepository.delete(pd.getId());
        		}
    		}
    	}catch(Exception e){
    		logger.error(e.getMessage());
    		return returnError(e.getMessage(), null);
    	}
    	return returnSuc(viewCateId+"商品索引删除成功！", null);
    }
    
    
    /**
     * 批量删除索引
     * 
     * @param productModel
     * @return
     */
    @RequestMapping(value = "/delete/batch",method=RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ServiceMessages<String> productDocumentDeleteBatch(String viewCateIdsStr){
    	try{
    		
//    		if(StringUtils.isEmpty(productCategoryViewBaseModel.)){
//    			return returnErrorParam("缺少 ProductIdsStr 参数", null);
//    		}
//    		List<Long> productIds = new ArrayList<Long>();
//    		for(String str : productModel.getProductIdsStr().split(",")){
//    			//查询所有的商品
//        		if(productCategoryViewBaseRepository.exists(str)){
//        			productCategoryViewBaseRepository.delete(str);
//        		}
//    		}
//    		
    		
    		if(StringUtils.isEmpty(viewCateIdsStr)){
    			return returnErrorParam("缺少 ProductIdsStr 参数", null);
    		}
    		
    		ProductCategoryViewBaseModel productCategoryViewBaseModel = new ProductCategoryViewBaseModel();
    		
    		List<Long> viewCateIds = new ArrayList<Long>();
    		for(String str : productCategoryViewBaseModel.getViewCateIdsStr().split(",")){
    			viewCateIds.add(Long.valueOf(str));
    		}
    		productCategoryViewBaseModel.setViewCateIds(viewCateIds);
    		
    		Boolean flag = Boolean.TRUE;
    		int page = 1;
    		int size = 100;
    		do{
    			//查询所有的商品显示分类 
            	List<ProductCategoryViewBaseModel> pcvbList = productCategoryViewBaseService.getProductCategoryViewBaseListPage(page,size,productCategoryViewBaseModel);
    			if(pcvbList != null && pcvbList.size() > 0){
    				for(ProductCategoryViewBaseModel one : pcvbList){
                		ProductCategoryViewBaseDocument pd = new ProductCategoryViewBaseDocument();
                		BeanUtils.copyProperties(one, pd);
                		//如果此id 已经存在 则先删除
                		if(productCategoryViewBaseRepository.exists(pd.getId())){
                			productCategoryViewBaseRepository.delete(pd.getId());
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
    public ServiceMessages<String> productDocumentDeleteAll(){
    	try{
    		productCategoryViewBaseRepository.deleteAll();
    	}catch(Exception e){
    		logger.error(e.getMessage());
    		return returnError(e.getMessage(), null);
    	}
    	return returnSuc("清空商品索引成功", null);
    }
    
}
