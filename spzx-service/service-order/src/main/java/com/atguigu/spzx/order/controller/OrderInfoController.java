package com.atguigu.spzx.order.controller;

import com.atguigu.spzx.model.dto.h5.OrderInfoDto;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.TradeVo;
import com.atguigu.spzx.order.service.OrderInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "订单管理")
@RequestMapping(value="/api/order/orderInfo")
@SuppressWarnings({"unchecked", "rawtypes"})
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;


    @Operation(summary = "提交订单")
    @PostMapping("/auth/submitOrder")
    public Result submitOrder(@RequestBody OrderInfoDto orderInfoDto){
        Long orderId =  orderInfoService.submitOrder(orderInfoDto);
        return Result.build(orderId, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "确认下单")
    @GetMapping("/auth/trade")
    public Result trade() {
        TradeVo tradeVo = orderInfoService.getTread();
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }
}
