package com.ninedrug.search.api.web.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ninedrug.search.api.web.bean.CardProductBean;
import com.ninedrug.search.api.web.bean.PCSearchBean;
import com.ninedrug.search.business.base.ServiceMessages;
import com.ninedrug.search.business.model.document.CardProductDocument;
import com.ninedrug.search.business.model.document.ProductCategoryViewBaseDocument;
import com.ninedrug.search.business.model.document.ProductDocument;
import com.ninedrug.search.business.model.extend.PCViewCategoryModel;
import com.ninedrug.search.business.model.extend.ProductModel;
import com.ninedrug.search.business.model.extend.ViewCategoryModel;
import com.ninedrug.search.business.repositories.CardProductRepository;
import com.ninedrug.search.business.repositories.ProductCategoryViewBaseRepository;
import com.ninedrug.search.business.service.ProductService;
import com.ninedrug.search.business.service.ViewCategoryModelService;

/**
 * Created by yemengying on 16/1/10.
 */
@Controller
@RequestMapping("/search")
public class PCSearchController extends BaseController {

    Logger logger = LoggerFactory.getLogger(PCSearchController.class);
    
    @Autowired
    private ProductCategoryViewBaseRepository productCategoryViewBaseRepository;
    
    @Autowired
    private ProductService productService; 
    
    @Autowired
    private ViewCategoryModelService viewCategoryModelService; 
    
    @Autowired
    private CardProductRepository cardProductRepository;
    
    
    /**
     * 根据product_name 查询商品分页集合
     * 默认是按商品名称搜索 匹配度降序 
     * orderBy ： 1：售价降序，2：售价升序
     * couponId 优惠券ID （查询优惠券所能使用的商品列表）
     * @param productModel
     * @return
     */
    @RequestMapping(value = "/pcsearch", produces = "application/json")
    @ResponseBody
    public ServiceMessages searchPageByProductNamePc(
    		@RequestParam(value="pageNum",  defaultValue="1")int pageNum,
    		@RequestParam(value="pageSize",  defaultValue="20")int pageSize,
    		String b,String k,Long c3,Double minPrice,Double maxPrice,
    		@RequestParam(value="orderBy",  defaultValue="1") int orderBy){
    	try{
    		
    		ProductModel productModel = new ProductModel();
    		if(!StringUtils.isEmpty(k)){
    			k = URLDecoder.decode(k, "UTF-8");
    			productModel.setK(k);
    		}
    		
    		//搜索多个品牌
    		if(!StringUtils.isEmpty(b)){
    			List<Long> productBrandIds = new ArrayList<Long>();
    			for(String s : b.split(",")){
    				productBrandIds.add(Long.valueOf(s));
    			}
    			productModel.setProductBrandIds(productBrandIds);
    		}
    		
    		productModel.setMinPrice(minPrice);
    		productModel.setMaxPrice(maxPrice);
    		
    		Map<String,Object> map2 = null;
    		//按分类查询
    		if(c3 != null){
    			//判断这个分类ID是第几级 
    			map2 = getViewCateThird(c3); 
    			//查询显示分类关联的三分类ID     searchTitle cateList
    			if(map2 != null && map2.containsKey("cateIdList")){
    				List<Long> catalogIds = (List<Long>)map2.get("cateIdList");
    				
    				if(catalogIds != null && catalogIds.size() > 0 ){
        				List<Long> proCatalogId = new ArrayList<Long>();
        				for(Long ca : catalogIds){
        					List<ProductCategoryViewBaseDocument> list = productCategoryViewBaseRepository.findByViewCateId(ca);
        	    			if(list != null && list.size() > 0){
        	        			for(ProductCategoryViewBaseDocument pp : list){
        	        				if(pp.getProductId() != null){
        	        					proCatalogId.add(pp.getProductId());
        	        				}
        	        			}
        	    			}
        				}
        				productModel.setProCatalogIds(proCatalogId);
        			}
    			}
    		}
    		
    		
    		Map map = new HashMap();
    		map.put("list", new ArrayList());
    		map.put("totalElements", 0);
    		map.put("totalPages", 0);
    		map.put("brandList", new ArrayList());
    		
			//搜索页面面包屑
			if(map2 != null && map2.containsKey("cateList")){
				map.put("cateList", map2.get("cateList"));
			}
			//搜索页面 title 
    		String searchTitle = productModel.getK() == null ? "" : productModel.getK();
			if(map2 != null && map2.containsKey("searchTitle")){
				searchTitle = searchTitle + (String)map2.get("searchTitle");
			}
			map.put("searchTitle", searchTitle);
			
    		if((productModel.getProCatalogIds() == null || productModel.getProCatalogIds().size() < 1) 
    				&& StringUtils.isEmpty(productModel.getK())){
    			return returnSuc("", map);
    		}
    		
    		//排序字段  1 :  productName ,2 :  ecPrice
    		int  orderNum =  2 ;
    		String orderStr = "productName";
    		if(orderNum == 2){
    			orderStr = "ecPrice";
    		}
    		
    		//排序类型  1 :ASC, 2 desc
    		Direction direction = Direction.ASC;
    		if(orderBy == 2){
    			direction = Direction.DESC;
    		}
    		Sort sort = new Sort(direction,orderStr);
    		
    		Pageable pageable = new PageRequest(pageNum - 1,pageSize,sort);
    		Page<ProductDocument> page =  productService.getPCProductPageList(productModel,pageable);
    		
    		List<PCSearchBean> list = new ArrayList<PCSearchBean>();
    		for(ProductDocument pd : page.getContent()){
    			PCSearchBean pc = new PCSearchBean();
    			BeanUtils.copyProperties(pd, pc);
    			
    			//查询商品所关联的可使用卡列表
    			List<CardProductDocument> cardPro = cardProductRepository.findByProductId(pc.getProductId());
    			if(cardPro != null && cardPro.size() > 0){
    				List<CardProductBean> list2 = new ArrayList<CardProductBean>();
    				
    				for(CardProductDocument cp : cardPro){
    					CardProductBean cpb = new CardProductBean();
    	    			BeanUtils.copyProperties(cp, cpb);
    	    			list2.add(cpb);
    				}
    				
    				pc.setSuppprtCards(list2);
    			}
    			list.add(pc);
    		}
    		
    		map.put("list", list);
    		map.put("totalElements", page.getTotalElements());
    		map.put("totalPages", page.getTotalPages());
    		
    		
    		return returnSuc("", map);
    	}catch(Exception e){
    		logger.error(e.getMessage());
    		return returnError(e.getMessage(), null);
    	}
    }
    
    
    
    /**
     * 根据product_name 查询商品分页集合
     * 默认是按商品名称搜索 匹配度降序 
     * orderBy ： 1：售价降序，2：售价升序
     * couponId 优惠券ID （查询优惠券所能使用的商品列表）
     * @param productModel
     * @return
     */
    @RequestMapping(value = "/pcsearch2", produces = "application/json")
    @ResponseBody
    public ServiceMessages searchPageByProductNamePc2(
    		@RequestParam(value="pageNum",  defaultValue="1")int pageNum,
    		@RequestParam(value="pageSize",  defaultValue="20")int pageSize,
    		String b,String k,Long c3,Double minPrice,Double maxPrice,
    		@RequestParam(value="orderBy",  defaultValue="1") int orderBy){
    	try{
    		
    		ProductModel productModel = new ProductModel();
    		if(!StringUtils.isEmpty(k)){
    			k = URLDecoder.decode(k, "UTF-8");
    			productModel.setK(k);
    		}
    		
    		//搜索多个品牌
    		if(!StringUtils.isEmpty(b)){
    			List<Long> productBrandIds = new ArrayList<Long>();
    			for(String s : b.split(",")){
    				productBrandIds.add(Long.valueOf(s));
    			}
    			productModel.setProductBrandIds(productBrandIds);
    		}
    		
    		productModel.setMinPrice(minPrice);
    		productModel.setMaxPrice(maxPrice);
    		
    		Map<String,Object> map2 = null;
    		//按分类查询
    		if(c3 != null){
    			//判断这个分类ID是第几级 
    			map2 = getViewCateThird(c3); 
    			//查询显示分类关联的三分类ID     searchTitle cateList
    			if(map2 != null && map2.containsKey("cateIdList")){
    				List<Long> catalogIds = (List<Long>)map2.get("cateIdList");
    				
    				if(catalogIds != null && catalogIds.size() > 0 ){
        				List<Long> proCatalogId = new ArrayList<Long>();
        				for(Long ca : catalogIds){
        					List<ProductCategoryViewBaseDocument> list = productCategoryViewBaseRepository.findByViewCateId(ca);
        	    			if(list != null && list.size() > 0){
        	        			for(ProductCategoryViewBaseDocument pp : list){
        	        				if(pp.getProductId() != null){
        	        					proCatalogId.add(pp.getProductId());
        	        				}
        	        			}
        	    			}
        				}
        				productModel.setProCatalogIds(proCatalogId);
        			}
    			}
    		}
    		
    		
    		Map map = new HashMap();
    		map.put("list", new ArrayList());
    		map.put("totalElements", 0);
    		map.put("totalPages", 0);
    		map.put("brandList", new ArrayList());
    		
			//搜索页面面包屑
			if(map2 != null && map2.containsKey("cateList")){
				map.put("cateList", map2.get("cateList"));
			}
			//搜索页面 title 
    		String searchTitle = productModel.getK() == null ? "" : productModel.getK();
			if(map2 != null && map2.containsKey("searchTitle")){
				searchTitle = searchTitle + (String)map2.get("searchTitle");
			}
			map.put("searchTitle", searchTitle);
			
    		if((productModel.getProCatalogIds() == null || productModel.getProCatalogIds().size() < 1) 
    				&& StringUtils.isEmpty(productModel.getK())){
    			return returnSuc("", map);
    		}
    		
    		//排序字段  1 :  productName ,2 :  ecPrice
    		int  orderNum =  2 ;
    		String orderStr = "productName";
    		if(orderNum == 2){
    			orderStr = "ecPrice";
    		}
    		
    		//排序类型  1 :ASC, 2 desc
    		Direction direction = Direction.ASC;
    		if(orderBy == 2){
    			direction = Direction.DESC;
    		}
    		Sort sort = new Sort(direction,orderStr);
    		
    		Pageable pageable = new PageRequest(pageNum - 1,pageSize,sort);
    		Page<ProductDocument> page =  productService.getPCProductPageList(productModel,pageable);
    		
    		List<PCSearchBean> list = new ArrayList<PCSearchBean>();
    		for(ProductDocument pd : page.getContent()){
    			PCSearchBean pc = new PCSearchBean();
    			BeanUtils.copyProperties(pd, pc);
    			
    			//查询商品所关联的可使用卡列表
    			List<CardProductDocument> cardPro = cardProductRepository.findByProductId(pc.getProductId());
    			if(cardPro != null && cardPro.size() > 0){
    				List<CardProductBean> list2 = new ArrayList<CardProductBean>();
    				
    				for(CardProductDocument cp : cardPro){
    					CardProductBean cpb = new CardProductBean();
    	    			BeanUtils.copyProperties(cp, cpb);
    	    			list2.add(cpb);
    				}
    				
    				pc.setSuppprtCards(list2);
    			}
    			list.add(pc);
    		}
    		
    		map.put("list", list);
    		map.put("totalElements", page.getTotalElements());
    		map.put("totalPages", page.getTotalPages());
    		
    		
    		return returnSuc("", map);
    	}catch(Exception e){
    		logger.error(e.getMessage());
    		return returnError(e.getMessage(), null);
    	}
    }
    
    
    
    /**
     * 判断传入的c3显示分类ID为第几级分类ID
     * 然后再查询其三级分类ID 
     * @param c3
     * @return
     */
    private Map<String,Object> getViewCateThird(Long c3CategoryId){
    	Map<String,Object> map = new HashMap<String,Object>();
    	List<Long> cateList = new ArrayList<Long>();
    	
    	List<PCViewCategoryModel> viewCateList = new ArrayList<PCViewCategoryModel>();
    	
    	ViewCategoryModel view1 = new ViewCategoryModel();
    	view1.setId(c3CategoryId);
    	ViewCategoryModel viewCategoryModel1 = viewCategoryModelService.queryViewCategoryById(view1);
		
    	Long categoryId = viewCategoryModel1.getId();
    	Long cateLevel = viewCategoryModel1.getCateLevel();
    	Long oldCateLevel = viewCategoryModel1.getCateLevel();
    	//cateLevel   显示分类级别(1: 一级, 2: 二级, 3: 三级)
    	if(cateLevel.longValue() == 1){
			
    		ViewCategoryModel view2 = new ViewCategoryModel();
    		view2.setParentId(categoryId);
    		List<ViewCategoryModel> list = viewCategoryModelService.queryViewCategoryByParentId(view2);
    		
        	for(ViewCategoryModel ca : list){
        		Long categoryId2 = ca.getId();
        		Long cateLevel2 = ca.getCateLevel();
        		if(cateLevel2 == 2){
        			
        			ViewCategoryModel view3 = new ViewCategoryModel();
        			view3.setParentId(categoryId2);
        			
            		List<ViewCategoryModel> list2 = viewCategoryModelService.queryViewCategoryByParentId(view3);
        			for(ViewCategoryModel ca3 : list2){
        				Long categoryId3 = ca3.getId();
        				Long cateLevel3 = ca3.getCateLevel();
                		if(cateLevel3 == 3){
                			cateList.add(categoryId3);
                		}
        			}
        			
        		}
        	}
		}
		
		if(cateLevel.longValue() == 2){
			ViewCategoryModel view2 = new ViewCategoryModel();
    		view2.setParentId(categoryId);
    		List<ViewCategoryModel> list = viewCategoryModelService.queryViewCategoryByParentId(view2);
    		
    		for(ViewCategoryModel ca : list){
        		categoryId = ca.getId();
            	cateLevel = ca.getCateLevel();
        		if(cateLevel == 3){
        			cateList.add(categoryId);
        		}
        	}
		}
		
		if(cateLevel.longValue() == 3){
			cateList.add(categoryId);
		}
		//分类ID列表
		map.put("cateIdList", cateList);
		
		String title = null;
		//查询面包屑     
		if(oldCateLevel.longValue() >= 1){
			
			PCViewCategoryModel pc1 = new PCViewCategoryModel();
			BeanUtils.copyProperties(viewCategoryModel1, pc1);
			viewCateList.add(pc1);
			
			if(oldCateLevel.longValue() >= 2){
				ViewCategoryModel view2 = new ViewCategoryModel();
				view2.setId(viewCategoryModel1.getParentId());
				ViewCategoryModel v2 = viewCategoryModelService.queryViewCategoryById(view2);
				
				PCViewCategoryModel pc2 = new PCViewCategoryModel();
				BeanUtils.copyProperties(v2, pc2);
				viewCateList.add(pc2);
				
				if(oldCateLevel.longValue() >= 3){
					
					ViewCategoryModel view3 = new ViewCategoryModel();
					view3.setId(v2.getParentId());
					ViewCategoryModel v3 = viewCategoryModelService.queryViewCategoryById(view3);
					
					PCViewCategoryModel pc3 = new PCViewCategoryModel();
					BeanUtils.copyProperties(v3, pc3);
					viewCateList.add(pc3);
					
					title = v3.getCateName();
				}
				
				if(title == null){
					title = v2.getCateName();
				}else{
					title = v2.getCateName() +"-"+ title ;
				}
			}
			if(title == null){
				title = viewCategoryModel1.getCateName();
			}else{
				title = viewCategoryModel1.getCateName() +"-"+ title ;
			}
		}
		
		map.put("searchTitle", title);
		
		ComparatorCatelevel cpu = new ComparatorCatelevel();
		Collections.sort(viewCateList, cpu);
		map.put("cateList", viewCateList);
		
		return map;
    }
    
    
    public class ComparatorCatelevel implements Comparator<PCViewCategoryModel> {
		
		public int compare(PCViewCategoryModel arg0, PCViewCategoryModel arg1) {
			try {
				 if (arg0.getCateLevel() >= arg1.getCateLevel()) {
					 return 1;
				 } else {
					 return -1;
				 }
			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0;
		}
	}
    
   
    
}
