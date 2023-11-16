package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.BrandMapper;
import com.atguigu.spzx.manager.service.BrandService;
import com.atguigu.spzx.model.entity.product.Brand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    //列表查询
    @Override
    public PageInfo<Brand> findByPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Brand> list =  brandMapper.findByPage();
        PageInfo<Brand> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    //品牌列表添加
    @Override
    public void save(Brand brand) {
        brandMapper.save(brand);
    }


    //品牌修改
    @Override
    public void updateById(Brand brand) {
        brandMapper.updateById(brand);
    }

    //品牌删除
    @Override
    public void deleteById(Long id) {
        brandMapper.deleteById(id);
    }

    //查询所有的品牌
    @Override
    public List<Brand> findAll() {
        return brandMapper.findByPage();
    }
}
