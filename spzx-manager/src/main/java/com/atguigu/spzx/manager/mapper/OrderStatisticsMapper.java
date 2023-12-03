package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderStatisticsMapper {
    //统计之后的数据添加到统计结果表中
    void insert(OrderStatistics orderStatistics);
}
