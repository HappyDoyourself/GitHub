package com.ninedrug.search.business.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CouponModelMapper {
	
	/**
	 * 根据优惠券ID查询优惠券信息
	 * @param productModel
	 * @return
	 */
	public Map<String,Object> selectCouponById(@Param("couponId") Long couponId);
	
	
	
	/**
	 * 根据优惠券ID查询可使用优惠券的商品列表   1-全部
	 * @param productModel
	 * @return
	 */
	public List<Map<String,Object>> selectCouponAllRangeProductListDetail(@Param("couponId") Long couponId);
	
	/**
	 * 根据优惠券ID查询可使用优惠券的商品列表   2-分类
	 * @param productModel
	 * @return
	 */
	public List<Map<String,Object>> selectCouponCateRangeProductListDetail(@Param("couponId") Long couponId);
	
	/**
	 * 根据优惠券ID查询可使用优惠券的商品列表   3-商品
	 * @param productModel
	 * @return
	 */
	public List<Map<String,Object>> selectCouponProductRangeProductListDetail(@Param("couponId") Long couponId);
	
	
	  
}
