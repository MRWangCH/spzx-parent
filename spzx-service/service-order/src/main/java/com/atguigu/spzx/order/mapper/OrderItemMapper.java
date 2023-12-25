package com.atguigu.spzx.order.mapper;

import com.atguigu.spzx.model.entity.order.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    //添加数据到order_item表
    void save(OrderItem orderItem);
    //订单id查询订单项数据
    List<OrderItem> findByOrderId(Long orderId);
}
