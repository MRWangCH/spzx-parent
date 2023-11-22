package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    //商品管理列表分页查询
    List<Product> findByPage(ProductDto productDto);
    //保存商品基本信息到product表里面
    void save(Product product);
}
