package com.atguigu.spzx.product.service;

import com.atguigu.spzx.model.entity.product.Category;

import java.util.List;

public interface CategoryService {
    //1 所有的一级分类
    List<Category> selectOneCategory();
}
