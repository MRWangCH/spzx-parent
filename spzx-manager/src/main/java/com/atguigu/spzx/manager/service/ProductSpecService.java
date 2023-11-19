package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.github.pagehelper.PageInfo;

public interface ProductSpecService {
    //商品规格管理列表分页查询
    PageInfo<ProductSpec> findByPage(Integer page, Integer limit);
    //商品规格管理新增
    void save(ProductSpec productSpec);
    //商品规格管理修改
    void updateById(ProductSpec productSpec);
    //商品规格管理删除
    void deleteById(Long id);
}
