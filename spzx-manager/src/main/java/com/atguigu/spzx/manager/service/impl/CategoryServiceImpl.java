package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.CategoryMapper;
import com.atguigu.spzx.manager.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    //查询分类,每次查一层数据
    @Override
    public List<Category> findCategoryList(Long id) {

        //1 根据条件值先进行查询
        List<Category> categoryList = categoryMapper.selectCategoryByParentId(id);
        //2 遍历返回的list集合，判断每个分类是否有下一层分类，如果有设置haschildren = true

        if (!CollectionUtils.isEmpty(categoryList)) {
            categoryList.forEach(category -> {
                //判断每个分类是否有下一层分类
                int count = categoryMapper.selectCountByParentId(category.getParentId());
                if (count > 0) {
                    category.setHasChildren(true);
                } else {
                    category.setHasChildren(false);
                }
            });
        }

        return categoryList;
    }
}
