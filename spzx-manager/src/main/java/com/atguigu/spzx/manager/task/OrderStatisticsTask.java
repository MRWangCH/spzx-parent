package com.atguigu.spzx.manager.task;

import cn.hutool.core.date.DateUtil;
import com.atguigu.spzx.manager.mapper.OrderInfoMapper;
import com.atguigu.spzx.manager.mapper.OrderStatisticsMapper;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.entity.order.OrderStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OrderStatisticsTask {

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    //每天凌晨两点查询前一天统计数据，把统计之后数据添加统计的结果表中
    @Scheduled(cron = "0 0 2 * * ? ")
    public void orderTotalAmountStatistics(){
        //1 获取前一天的日期
        String creatDate = DateUtil.offsetDay(new Date(), -1).toString("yyyy-MM-dd");
        //2 根据前一天日期进行统计分组功能，统计前一天的金额
        OrderStatistics orderStatistics = orderInfoMapper.selectStatisticsByDate(creatDate);
        //3 统计之后的数据添加到统计结果表中
        if (orderStatistics != null){
            orderStatisticsMapper.insert(orderStatistics);
        }
    }

}