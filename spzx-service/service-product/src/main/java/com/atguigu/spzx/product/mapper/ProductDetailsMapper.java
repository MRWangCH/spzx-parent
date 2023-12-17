package com.atguigu.spzx.product.mapper;


import com.atguigu.spzx.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDetailsMapper {
    //productid,获取商品详情
    ProductDetails getByProductId(Long productId);
}
