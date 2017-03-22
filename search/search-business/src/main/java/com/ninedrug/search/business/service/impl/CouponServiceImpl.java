package com.ninedrug.search.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ninedrug.search.business.base.BaseServiceImpl;
import com.ninedrug.search.business.mapper.CouponModelMapper;
import com.ninedrug.search.business.service.CouponService;

@Service
public class CouponServiceImpl extends BaseServiceImpl implements CouponService {

	
	@Autowired
	CouponModelMapper couponModelMapper;
	
	
	/**
	 * 根据优惠券ID查询优惠券信息
	 * @param productModel
	 * @return
	 */
	public Map<String,Object> selectCouponById(@Param("couponId") Long couponId){
		return couponModelMapper.selectCouponById(couponId);
	}
	
	/**
     * 根据优惠券ID查询商品ID列表
     * @return
     */
    public Map<String,Object> queryProductListByCouponId(Long couponId){
    	
    	Map<String,Object> result = new HashMap<String,Object>();
    	
    	List<Long> list = null;
    	List<Map<String,Object>> productList = null;
    	
    	Map<String,Object> map = couponModelMapper.selectCouponById(couponId);
    	if(map == null){
    		return null;
    	}
    	String applyRange = map.get("applyRange").toString();
    	//判断优惠券类型   使用范围类型(1-全部,2-商品分类,3-具体商品) 
    	if(applyRange.equals("1")){
    		productList = couponModelMapper.selectCouponAllRangeProductListDetail(couponId);
    	}else if(applyRange.equals("2")){
    		productList = couponModelMapper.selectCouponCateRangeProductListDetail(couponId);
    	}else if(applyRange.equals("3")){
    		productList = couponModelMapper.selectCouponProductRangeProductListDetail(couponId);
    	}
    	
    	if(productList != null){
    		list = new ArrayList<Long>();
    		for(Map<String,Object> pro : productList){
    			Long productId = Long.valueOf(pro.get("productId").toString());
    			list.add(productId);
    		}
//    		for(int i=0;i<1000;i++){
//    			Map<String,Object> pro = productList.get(i);
//    			Long productId = Long.valueOf(pro.get("productId").toString());
//    			list.add(productId);
//    		}
    	}
    	
    	result.put("productIds", list);
    	result.put("applyRange", applyRange);
    	
    	return result;
    } 

	
	

}
