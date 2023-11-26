package com.atguigu.spzx.manager.mapper;


import com.atguigu.spzx.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDetailsMapper {
    //保存商品详情到product_detail表
    void save(ProductDetails productDetail);
    //根据商品id查询商品详情数据
    ProductDetails findProductDetailsById(Long id);
    //修改product_details
    void updateById(ProductDetails productDetails);
    //根据商品id删除product_detail表
    void deleteByProductId(Long id);
}
