package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.product.Brand;
import com.github.pagehelper.PageInfo;

public interface BrandService {
    //列表查询
    PageInfo<Brand> findByPage(Integer page, Integer limit);
    //品牌列表添加
    void save(Brand brand);
}