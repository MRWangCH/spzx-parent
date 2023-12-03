package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.order.OrderStatisticsDto;
import com.atguigu.spzx.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderStatisticsMapper {
    //统计之后的数据添加到统计结果表中
    void insert(OrderStatistics orderStatistics);
    //根据dto条件查询统计结果数据，返回list集合
    List<OrderStatistics> selectList(OrderStatisticsDto orderStatisticsDto);
}
