package com.atguigu.spzx.product.service;

import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.h5.ProductItemVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductService {
    //根据销量排序，获取前十的数据
    List<ProductSku> selectProductSkuBySale();
    //分页查询
    PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto);
    //商品详情
    ProductItemVo item(Long skuId);
    //用于远程调用，根据skuid返回sku信息
    ProductSku getBySkuId(Long skuId);
}
