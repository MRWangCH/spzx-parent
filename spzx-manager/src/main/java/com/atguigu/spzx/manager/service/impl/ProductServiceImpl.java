package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.ProductDetailsMapper;
import com.atguigu.spzx.manager.mapper.ProductMapper;
import com.atguigu.spzx.manager.mapper.ProductSkuMapper;
import com.atguigu.spzx.manager.mapper.ProductSpecMapper;
import com.atguigu.spzx.manager.service.ProductService;
import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductDetails;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    //商品管理列表分页查询
    @Override
    public PageInfo<Product> findByPage(Integer page, Integer limit, ProductDto productDto) {
        PageHelper.startPage(page, limit);
        List<Product> list = productMapper.findByPage(productDto);
        return new PageInfo<>(list);
    }

    //添加商品信息
    @Override
    public void save(Product product) {
        product.setAuditStatus(0);
        product.setStatus(0);
        //1 保存商品基本信息到product表里面
        productMapper.save(product);

        //2 获取商品的sku列表，保存sku信息，product_sku表里
        List<ProductSku> productSkuList = product.getProductSkuList();
        for (int i = 0; i < productSkuList.size(); i++) {
            ProductSku productSku = productSkuList.get(i);
            //商品编号
            productSku.setSkuCode(product.getId() + "_" + i);
            //商品id
            productSku.setProductId(product.getId());
            //sku名称
            productSku.setSkuName(product.getName()+productSku.getSkuSpec());
            // 设置销量
            productSku.setSaleNum(0);
            productSku.setStatus(0);
            productSkuMapper.save(productSku);
        }

        //3 保存商品详情到product_detail表
        ProductDetails productDetail = new ProductDetails();
        productDetail.setProductId(product.getId());
        productDetail.setImageUrls(product.getDetailsImageUrls());
        productDetailsMapper.save(productDetail);

    }
}
