package com.atguigu.spzx.manager.mapper;


import com.atguigu.spzx.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDetailsMapper {
    //保存商品详情到product_detail表
    void save(ProductDetails productDetail);
}
