package com.atguigu.spzx.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.product.mapper.CategoryMapper;
import com.atguigu.spzx.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //查询所有一级分类
    @Override
    public List<Category> selectOneCategory() {
        //1 先查询redis，是否有所有的一级分类
        String categoryOneJson = redisTemplate.opsForValue().get("category:one");
        //2 如果redis有一级分类直接返回
        if (StringUtils.hasText(categoryOneJson)) {
            //categoryOneJson字符串转成list集合
            List<Category> existCategoryList = JSON.parseArray(categoryOneJson, Category.class);
            return existCategoryList;
        }
        //3 如果没有，数据库查询一级分类写回redis，返回
        List<Category> categoryList = categoryMapper.selectOneCategory();
        //放入redis
        //先将List转成String字符串再放
        String string = JSON.toJSONString(categoryList);
        redisTemplate.opsForValue().set("category:one", string,7 , TimeUnit.DAYS);
        return categoryList;
    }


    //查询所有分类，树型封装
    @Cacheable(value = "category", key = "'all'")
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
