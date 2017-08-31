package com.baomidou.springwind.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.springwind.entity.Order;
import com.baomidou.springwind.entity.OrderPaymentLog;
import com.baomidou.springwind.entity.WeixinPay;
import com.baomidou.springwind.mapper.OrderMapper;
import com.baomidou.springwind.service.IOrderPaymentLogService;
import com.baomidou.springwind.service.IOrderService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import com.baomidou.springwind.util.weixinutil.HexUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by fht on 2017-05-25 21:18.
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl<OrderMapper,Order> implements IOrderService {

}
