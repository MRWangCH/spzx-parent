package com.atguigu.spzx.manager.mapper;


import com.atguigu.spzx.model.entity.product.ProductSpec;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSpecMapper {
    //商品规格管理列表分页查询
    List<ProductSpec> findByPage();
    //商品规格管理新增
    void save(ProductSpec productSpec);
    //商品规格管理修改
    void update(ProductSpec productSpec);
    //商品规格管理删除
    void delete(Long id);
    //查询所有商品规格
    List<ProductSpec> findAll();
}
