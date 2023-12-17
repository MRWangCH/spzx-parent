package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ProductMapper {
    //productid，获取商品信息
    Product getById(Long productId);
}
