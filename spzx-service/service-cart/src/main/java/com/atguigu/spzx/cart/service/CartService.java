package com.atguigu.spzx.cart.service;

public interface CartService {
    //添加商品到购物车
    void addToCart(Long skuId, Integer skuNum);
}
