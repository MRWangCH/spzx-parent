package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSkuMapper {
    //获取商品的sku列表，保存sku信息，
    void save(ProductSku productSku);
    //根据商品id查询商品sku信息列表
    List<ProductSku> findProductSkuByProductId(Long id);
    //保存修改数据
    void updateById(ProductSku productSku);
    //根据商品id删除product_sku表
    void deleteByProductId(Long id);
}
