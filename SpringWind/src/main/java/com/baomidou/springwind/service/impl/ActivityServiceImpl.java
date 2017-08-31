package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.Activity;
import com.baomidou.springwind.mapper.ActivityMapper;
import com.baomidou.springwind.service.IActivityService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Created by fht on 2017-05-07 11:25.
 */
@Service
public class ActivityServiceImpl extends BaseServiceImpl<ActivityMapper,Activity> implements IActivityService{
}
