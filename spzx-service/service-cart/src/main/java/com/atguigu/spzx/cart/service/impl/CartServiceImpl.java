package com.atguigu.spzx.cart.service.impl;

import com.alibaba.fastjson2.JSON;
import com.atguigu.spzx.cart.service.CartService;
import com.atguigu.spzx.feign.product.ProductFeignClient;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

            //远程调用实现 根据skuid获取商品信息
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



    //查询购物车
    @Override
    public List<CartInfo> getCartList() {
        //构建redis里面的key的值，根据当前登录用户的id取得
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);
        //根据key从redis里面hash类型的所有value值
        List<Object> valueList = redisTemplate.opsForHash().values(cartKey);
        //返回
        if (!CollectionUtils.isEmpty(valueList)){
            List<CartInfo> cartInfoList = valueList.stream().map(cartInfoObj -> JSON.parseObject(cartInfoObj.toString(), CartInfo.class))
                    .sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()))
                    .collect(Collectors.toList());
            return cartInfoList;
        }
        return new ArrayList<>();
    }


    //删除购物车商品
    @Override
    public void deleteCart(Long skuId) {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);
        redisTemplate.opsForHash().delete(cartKey, String.valueOf(skuId));
    }


    //更新购物车商品选中状态
    @Override
    public void checkCart(Long skuId, Integer isChecked) {
        //1 构建redis里面的key的值，根据当前登录用户的id取得
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);
        //2 判断key是否包含field
        Boolean hasKey = redisTemplate.opsForHash().hasKey(cartKey, String.valueOf(skuId));
        if (hasKey){
            //3 根据key + field把value获取出来
            String cartInfoString = redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId)).toString();
            //4 更新value里面选中状态
            CartInfo cartInfo = JSON.parseObject(cartInfoString, CartInfo.class);
            cartInfo.setIsChecked(isChecked);
            //5 放回redis中
            redisTemplate.opsForHash().put(cartKey, String.valueOf(skuId), JSON.toJSONString(cartInfo));
        }
    }


    //更新购物车商品全部选中状态
    @Override
    public void allCheckCart(Integer isChecked) {
        //1 构建redis里面的key的值，根据当前登录用户的id取得
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);
        //2 根据key获取购物车中所有value值
        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);
        //类型转换 List<objectList> -->  List<CartInfo>
        if (!CollectionUtils.isEmpty(objectList)) {
            List<CartInfo> cartInfoList = objectList.stream().map(object -> JSON.parseObject(object.toString(), CartInfo.class)).collect(Collectors.toList());
            //3 把每个商品ischecked进行更新
            cartInfoList.forEach(cartInfo -> {
                cartInfo.setIsChecked(isChecked);
                redisTemplate.opsForHash().put(cartKey, String.valueOf(cartInfo.getSkuId()), JSON.toJSONString(cartInfo));
            });
        }

    }


    //清空购物车
    @Override
    public void clearCart() {
        //1 构建redis里面的key的值，根据当前登录用户的id取得
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);
        //2 根据key删除redis中的值
        redisTemplate.delete(cartKey);
    }


    //远程调用：订单结算时，获取购物车选中商品列表
    @Override
    public List<CartInfo> getAllCkecked() {
        //1 获取userId，构建key
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);
        //2 根据key获取对应的购物车所有商品
        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);
        //objectList转成cartInfo
        if(!CollectionUtils.isEmpty(objectList)){
            List<CartInfo> cartInfoList = objectList.stream().map(object -> JSON.parseObject(object.toString(), CartInfo.class))
                    .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                    .collect(Collectors.toList());
            return cartInfoList;
        }
        return new ArrayList<>();
    }

    //远程调用：删除生成订单的购物车商品
    @Override
    public void deleteChecked() {
        //1 获取userId，构建key
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);
        //2 根据key获取redis中所有value的值
        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);
        if (!CollectionUtils.isEmpty(objectList)){
            //删除选中的
            objectList.stream().map(object -> JSON.parseObject(object.toString(), CartInfo.class))
                    .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                    .forEach(cartInfo -> redisTemplate.opsForHash().delete(cartKey, String.valueOf(cartInfo.getSkuId())));
        }
    }

}
