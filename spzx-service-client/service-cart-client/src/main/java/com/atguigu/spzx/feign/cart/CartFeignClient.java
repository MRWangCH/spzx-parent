package com.atguigu.spzx.feign.cart;


import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.vo.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "service-cart")
public interface CartFeignClient {

    @Operation(summary="选中的购物车")
    @GetMapping(value = "/api/order/cart/auth/getAllCkecked")
    public List<CartInfo> getAllChecked();


    //远程调用：删除生成订单的购物车商品
    @GetMapping("/api/order/cart/auth/deleteChecked")
    public Result deleteChecked();
}
