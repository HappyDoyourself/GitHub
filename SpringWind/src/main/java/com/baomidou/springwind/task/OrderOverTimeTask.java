package com.baomidou.springwind.task;

import com.baomidou.springwind.common.Constant;
import com.baomidou.springwind.entity.Order;
import com.baomidou.springwind.service.IOrderService;
import com.baomidou.springwind.util.DateUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by fht on 2017-07-16 06:02.
 * 订单超时
 */
@Component
public class OrderOverTimeTask {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IOrderService orderService;
    @Scheduled(cron = "0 */1 * * * ?")
    public  void orderOverTime(){
        //查询出来超时的订单 修改其订单状态为超时
        Map<String,Object> map = new HashedMap();
        map.put("order_state", Constant.PAY_WAITING);
        List<Order> ordersList = orderService.selectByMap(map);
        List<Order> overTimeOrderList = new ArrayList<>();
        for(Order order : ordersList){
            //如果订单创建超过20分钟则修改状态为超时
            if(DateUtils.getAfterByMinuties(order.getAddTime(),20).getTime() <= System.currentTimeMillis()){
                order.setOrderState(Constant.PAY_OVERTIME);
                overTimeOrderList.add(order);
            }
        }
        logger.info("批量更新超时订单，订单数量：" + overTimeOrderList.size());
        if(overTimeOrderList.size() > 0){
            orderService.updateBatchById(overTimeOrderList);
        }
    }
}
