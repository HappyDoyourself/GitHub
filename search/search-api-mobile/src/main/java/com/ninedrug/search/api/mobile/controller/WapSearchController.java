package com.ninedrug.search.api.mobile.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ninedrug.search.api.web.bean.CardProductBean;
import com.ninedrug.search.api.web.bean.WapSearchBean;
import com.ninedrug.search.business.base.ServiceMessages;
import com.ninedrug.search.business.model.document.CardProductDocument;
import com.ninedrug.search.business.model.document.ProductCategoryViewBaseDocument;
import com.ninedrug.search.business.model.document.ProductDocument;
import com.ninedrug.search.business.model.extend.ProductModel;
import com.ninedrug.search.business.repositories.CardProductRepository;
import com.ninedrug.search.business.repositories.ProductCategoryViewBaseRepository;
import com.ninedrug.search.business.repositories.ProductRepository;
import com.ninedrug.search.business.service.CouponService;
import com.ninedrug.search.business.service.ProductService;

/**
 * Created by yemengying on 16/1/10.
 */
@Controller
@RequestMapping("/search")
public class WapSearchController extends BaseController {
	
    Logger logger = LoggerFactory.getLogger(WapSearchController.class);

    @Autowired
    private ProductRepository productRepository; 
    
    @Autowired
    private ProductCategoryViewBaseRepository productCategoryViewBaseRepository;
    
    @Autowired
    private ProductService productService; 
    
    @Autowired
    private CouponService couponService; 
    
    @Autowired
    private CardProductRepository cardProductRepository;
    
    /**
     * 根据product_name 查询商品分页集合
     * 默认是按商品名称搜索 匹配度降序 
     * orderBy ： 1：售价降序，2：售价升序
     * 搜索关键词 : k  1：售价降序，2：售价升序，3：销量降序，4：综合
     * 品牌ID搜索 : b  123,124
     * 三级扩展分类ID : c3
     * 优惠券可使用的商品列表 : couponId 
     * @param productModel
     * @return
     */
    @RequestMapping(value = "/wapsearch", produces = "application/json")
    @ResponseBody
    public ServiceMessages  wapSearch(
    		@RequestParam(value="pageNum",  defaultValue="1")int pageNum,
    		@RequestParam(value="pageSize",  defaultValue="20")int pageSize,
    		String b,String k,Long c3,Double minPrice,Double maxPrice,
    		Long couponId,
    		@RequestParam(value="orderBy",  defaultValue="1")int orderBy){
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
    		productModel.setProCatalogId(c3);
    		
    	    //  商品关联多个分类 
    		if(c3 != null){
    			List<ProductCategoryViewBaseDocument> list = productCategoryViewBaseRepository.findByViewCateId(c3);
    			if(list != null && list.size() > 0){
    				List<Long> proCatalogId = new ArrayList<Long>();
        			for(ProductCategoryViewBaseDocument pp : list){
        				if(pp.getProductId() != null){
        					proCatalogId.add(pp.getProductId());
        				}
        			}
        			productModel.setProCatalogIds(proCatalogId);
    			}
    		}
    		
    		
    		//优惠券 可用的商品列表  
    		if(couponId != null && couponId > 0){
    			Map<String,Object> map = couponService.queryProductListByCouponId(couponId);
    			 
    			List<Long> couponProList = (List<Long>)map.get("productIds");
    			String applyRange = (String)map.get("applyRange");
    			
    			productModel.setCouponProductIds(couponProList);
    			productModel.setApplyRange(applyRange);
    		}
    		
    		Map map = new HashMap();
    		map.put("list", new ArrayList());
    		map.put("totalElements", 0);
    		map.put("totalPages", 0);
    		map.put("brandList", new ArrayList());
    		
    		if((productModel.getProCatalogIds() == null || productModel.getProCatalogIds().size() < 1) 
    				&& StringUtils.isEmpty(productModel.getK())
    				&& StringUtils.isEmpty(productModel.getApplyRange())){
    			
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
    		
    		Page<ProductDocument> page =  productService.getWapProductPageList(productModel,pageable);
    		
    		List<WapSearchBean> list = new ArrayList<WapSearchBean>();
    		for(ProductDocument pd : page.getContent()){
    			WapSearchBean pc = new WapSearchBean();
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
    		
    		//查询关键词对应的品牌列表
    		List<Map<String,Object>> blist = productService.getBrandList(productModel);
    		map.put("brandList", blist);
    		
    		return returnSuc("", map);
    	}catch(Exception e){
    		logger.error(e.getMessage());
    		return returnError(e.getMessage(), null);
    	}
    }
    
    
    
    /**
     * 根据product_name 查询商品分页集合
     * 
     * 默认是按商品名称搜索 匹配度降序 
     * 
     * @param productModel
     * @return
     */
    @RequestMapping(value = "/wap/{orderNum}/{direction}", produces = "application/json")
    @ResponseBody
    public ServiceMessages searchPageByProductNameWap(int page,int size,ProductModel productModel,
    		@PathVariable("orderNum") int orderNum,@PathVariable("direction") int direc){
    	try{
    		
    		//排序字段  1 : ecPrice ,2 : productName
    		String orderStr = "productName";
    		if(orderNum == 2){
    			orderStr = "ecPrice";
    		}
    		
    		//排序类型  1 :DESC, 2 ASC
    		Direction direction = Direction.DESC;
    		if(direc == 2){
    			direction = Direction.ASC;
    		}
    		
    		Sort sort = new Sort(direction,orderStr);
    		Pageable pageable = new PageRequest(page - 1,size,sort);
    		Page<ProductDocument> pageList = productRepository.findByProductName(productModel.getProductName(), pageable);
    		return returnSuc("", pageList);
    	}catch(Exception e){
    		logger.error(e.getMessage());
    		return returnError(e.getMessage(), null);
    	}
    	
    }

    
}
