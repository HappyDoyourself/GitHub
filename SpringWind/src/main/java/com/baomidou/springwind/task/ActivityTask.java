package com.baomidou.springwind.task;

import com.baomidou.springwind.common.Constant;
import com.baomidou.springwind.entity.Activity;
import com.baomidou.springwind.service.IActivityService;
import com.baomidou.springwind.util.DateUtils;
import com.sun.tools.internal.jxc.ap.Const;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fht on 2017-07-09 06:34.
 * 修改活动状态
 */
@Component
public class ActivityTask {
    private Logger logger = LoggerFactory.getLogger(ActivityTask.class);
    @Autowired
    private IActivityService activityService;

    @Scheduled(cron = "0 */1 * * * ?")
    public final void ActivityState(){
        logger.info("查询活动状态任务开始");
        //查询出来活动结束时间大于当前时间，修改状态为 已结束
        Map<String,Object> map = new HashMap<>();
        map.put("state", Constant.ACTIVITY_ON);
        List<Activity> activitieLists = activityService.selectByMap(map);
        List<Activity> listIds = new ArrayList<>();
        long nowTime = System.currentTimeMillis();
        for(Activity activity : activitieLists){
             Long endTime = DateUtils.getLongDate(activity.getEndTime());
            if(endTime <=nowTime){
                activity.setState(Constant.ACTIVITY_OFF);
                listIds.add(activity);
            }
        }
        if(CollectionUtils.isNotEmpty(listIds)){
            activityService.updateBatchById(listIds);
        }
        logger.info("活动状态任务结束！");
    }
}
