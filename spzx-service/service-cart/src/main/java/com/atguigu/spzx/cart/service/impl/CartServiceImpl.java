package com.atguigu.spzx.cart.service.impl;

import com.alibaba.fastjson2.JSON;
import com.atguigu.spzx.cart.service.CartService;
import com.atguigu.spzx.feign.product.ProductFeignClient;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CartServiceImpl implements CartService {


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ProductFeignClient productFeignClient;

    private String getCartKey(Long userId) {
        //定义key user:cart:userId
        return "user:cart:" + userId;
    }

    //添加商品到购物车
    @Override
    public void addToCart(Long skuId, Integer skuNum) {
        //1 必须是登录状态，获取当前用户登录id，作为redis中hash类型的值
        //从threadlocal中获取
        Long userId = AuthContextUtil.getUserInfo().getId();
        //构建一下hash中的key的名称
        String cartKey = this.getCartKey(userId);

        //2 因为购物车放到redis里
        //从redis获取购物车数据，根据用户id+skuid获取（hash类型key + field）
        Object cartInfoObj = redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId));
        //3 如果购物车存在已添加的商品，则把数量相加
        CartInfo cartInfo = null;
        if (cartInfoObj != null) {
            //商品已经存在，将其数量加1
            cartInfo = JSON.parseObject(cartInfoObj.toString(), CartInfo.class);
            cartInfo.setSkuNum(cartInfo.getSkuNum() + skuNum);
            //设置属性,购物车商品选中
            cartInfo.setIsChecked(1);
            cartInfo.setCreateTime(new Date());
        } else {
            //4 购物车没有添加商品，直接添加商品到购物车（添加到redis）
            //远程调用实现nacos+opeFeign实现，根据skuid获取sku信息
            cartInfo = new CartInfo();

            //TODO 远程调用实现 根据skuid获取商品信息
            ProductSku productSku = productFeignClient.getBySkuId(skuId);
            cartInfo.setCartPrice(productSku.getSalePrice());
            cartInfo.setSkuNum(skuNum);
            cartInfo.setSkuId(skuId);
            cartInfo.setUserId(userId);
            cartInfo.setImgUrl(productSku.getThumbImg());
            cartInfo.setSkuName(productSku.getSkuName());
            cartInfo.setIsChecked(1);
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());
        }
        //添加到redis
        redisTemplate.opsForHash().put(cartKey, String.valueOf(skuId), JSON.toJSONString(cartInfo));


    }
}
