package com.atguigu.spzx.manager.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OrderStatisticsTask {

    //测试定时任务
    //每隔5秒执行一次
    @Scheduled(cron = "0/5 * * * * ? ")
    public void testHello(){
        System.out.println("定时任务" + new Date().toInstant());
    }
}
