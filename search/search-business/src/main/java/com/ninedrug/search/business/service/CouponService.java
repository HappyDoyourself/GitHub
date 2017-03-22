package com.ninedrug.search.business.service;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ninedrug.search.business.base.BaseService;

/**
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author Administrator
 * @date 2017年1月3日
 */
public interface CouponService extends BaseService {
	
	/**
	 * 根据优惠券ID查询优惠券信息
	 * @param productModel
	 * @return
	 */
	public Map<String,Object> selectCouponById(@Param("couponId") Long couponId);
	
	
	/**
     * 根据优惠券ID查询商品ID列表
     * @return
     */
    public Map<String,Object> queryProductListByCouponId(Long couponId);
}
