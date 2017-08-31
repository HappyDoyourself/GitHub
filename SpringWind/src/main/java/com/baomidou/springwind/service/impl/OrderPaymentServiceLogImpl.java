package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.OrderPayment;
import com.baomidou.springwind.entity.OrderPaymentLog;
import com.baomidou.springwind.mapper.OrderPaymentLogMapper;
import com.baomidou.springwind.service.IOrderPaymentLogService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by fht on 2017-05-27 06:20.
 */
@Service
public class OrderPaymentServiceLogImpl extends BaseServiceImpl<OrderPaymentLogMapper,OrderPaymentLog> implements IOrderPaymentLogService{

}
