package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.Sms;
import com.baomidou.springwind.mapper.SmsMapper;
import com.baomidou.springwind.service.ISmsService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Created by fht on 2017-05-25 20:47.
 */
@Service
public class SmsServiceImpl extends BaseServiceImpl<SmsMapper,Sms> implements ISmsService {
}
