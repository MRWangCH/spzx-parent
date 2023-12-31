package com.atguigu.spzx.order.service;

import com.atguigu.spzx.model.dto.h5.OrderInfoDto;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.vo.h5.TradeVo;
import com.github.pagehelper.PageInfo;

public interface OrderInfoService {
    //确认下单
    TradeVo getTread();
    //提交订单
    Long submitOrder(OrderInfoDto orderInfoDto);
    //获取订单信息
    OrderInfo getOrderInfo(Long orderId);
    //立即购买
    TradeVo buy(Long skuId);
    //获取订单分页列表
    PageInfo<OrderInfo> findOrderPage(Integer page, Integer limit, Integer orderStatus);
    //远程调用：获取订单信息
    OrderInfo getOrderInfoByOrderNo(String orderNo);
    // 远程调用：更新订单状态
    void updateOrderStatus(String orderNo);
}
