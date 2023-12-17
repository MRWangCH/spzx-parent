package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSkuMapper {
    // 根据销量排序，获取前十的数据
    List<ProductSku> selectProductSkuBySale();
    //分页查询
    List<ProductSku> findByPage(ProductSkuDto productSkuDto);
    //根据skuid获取sku信息
    ProductSku getById(Long skuId);
    //根据商品id获取商品所有的sku列表
    List<ProductSku> findByProductId(Long productId);
}
