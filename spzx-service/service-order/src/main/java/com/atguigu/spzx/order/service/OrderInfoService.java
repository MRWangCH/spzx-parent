package com.atguigu.spzx.order.service;

import com.atguigu.spzx.model.dto.h5.OrderInfoDto;
import com.atguigu.spzx.model.vo.h5.TradeVo;

public interface OrderInfoService {
    //确认下单
    TradeVo getTread();
    //提交订单
    Long submitOrder(OrderInfoDto orderInfoDto);
}
