package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.ProductSpecMapper;
import com.atguigu.spzx.manager.service.ProductSpecService;
import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductSpecServiceImpl implements ProductSpecService {

    @Autowired
    private ProductSpecMapper productSpecMapper;

    //商品规格管理列表分页查询
    @Override
    public PageInfo<ProductSpec> findByPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<ProductSpec> list =  productSpecMapper.findByPage();
        PageInfo<ProductSpec> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    //商品规格管理新增
    @Override
    public void save(ProductSpec productSpec) {
        productSpecMapper.save(productSpec);
    }


    //商品规格管理修改
    @Override
    public void updateById(ProductSpec productSpec) {
        productSpecMapper.update(productSpec);
    }


    //商品规格管理删除
    @Override
    public void deleteById(Long id) {
        productSpecMapper.delete(id);
    }
}
