package com.atguigu.spzx.pay.controller;

import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.pay.service.AlipayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/order/alipay")
public class AlipayController {

    @Autowired
    private AlipayService alipayService;

    @Operation(summary = "支付宝下单")
    @GetMapping("/submitAlipay/{orderNo}")
    @ResponseBody
    public Result submitAlipay(@Parameter(name = "orderNo", description = "订单号", required = true) @PathVariable(value = "orderNo") String orderNo) {
        String form = alipayService.submitAlipay(orderNo);
        return Result.build(form, ResultCodeEnum.SUCCESS);
    }
}
