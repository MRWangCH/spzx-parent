package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.github.pagehelper.PageInfo;

public interface ProductService {
    //商品管理列表分页查询
    PageInfo<Product> findByPage(Integer page, Integer limit, ProductDto productDto);
    //添加商品信息
    void save(Product product);
    //根据商品id查询商品信息
    Product getById(Long id);
    //保存修改数据
    void update(Product product);
}
