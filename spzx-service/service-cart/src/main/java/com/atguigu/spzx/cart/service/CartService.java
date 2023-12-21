package com.atguigu.spzx.cart.service;

import com.atguigu.spzx.model.entity.h5.CartInfo;

import java.util.List;

public interface CartService {
    //添加商品到购物车
    void addToCart(Long skuId, Integer skuNum);
    //查询购物车
    List<CartInfo> getCartList();
}
