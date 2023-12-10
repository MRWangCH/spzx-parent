package com.atguigu.spzx.product.service.impl;

import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.product.mapper.CategoryMapper;
import com.atguigu.spzx.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    //查询所有一级分类
    @Override
    public List<Category> selectOneCategory() {
        return categoryMapper.selectOneCategory();
    }


    //查询所有分类，树型封装
    @Override
    public List<Category> findCategoryTree() {
        //1 查询所有的分类，返回list集合
        List<Category> allCategoryList = categoryMapper.findAll();

        //2 遍历list集合，通过parentid=0得到所有一级分类
        List<Category> oneCategoryList = allCategoryList.stream().filter(item -> item.getParentId().longValue() == 0).collect(Collectors.toList());

        //3 遍历所有一级分类list集合，条件判断id = parentid，得到一级下面二级分类
        oneCategoryList.forEach(oneCategory ->{
            List<Category> twoCategoryList = allCategoryList.stream().filter(item -> item.getParentId() == oneCategory.getId()).collect(Collectors.toList());
            //把二级分类封装到一级分类里面
            oneCategory.setChildren(twoCategoryList);

            //4 遍历所有二级分类，条件判断id=parentid，得到二级下面三级分类
            twoCategoryList.forEach(twoCategory -> {
                List<Category> threeCategoryList = allCategoryList.stream().filter(item -> item.getParentId() == twoCategory.getId()).collect(Collectors.toList());
                //三级分类封装到二级中去
                twoCategory.setChildren(threeCategoryList);
            });
        });

        return oneCategoryList;
    }
}
