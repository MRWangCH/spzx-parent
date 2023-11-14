package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    //根据条件值先进行查询
    List<Category> selectCategoryByParentId(Long id);
    //判断每个分类是否有下一层分类
    int selectCountByParentId(Long parentId);
}
