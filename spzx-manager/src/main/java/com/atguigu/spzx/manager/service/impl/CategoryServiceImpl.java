package com.atguigu.spzx.manager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manager.mapper.CategoryMapper;
import com.atguigu.spzx.manager.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
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

    //文件导出
    @Override
    public void exportData(HttpServletResponse response) {
        try {
            //1 设置响应头信息和其他信息
            // 设置响应结果类型
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("分类数据", "UTF-8");

            //设置响应头信息 设置文件以下载的方式打开
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");

            //2 调用mapper中方法查询所有的分类，返回list集合
            List<Category> categoryList = categoryMapper.findAll();

            //List<Category> 转换成 List<CategoryExcelVo>
            List<CategoryExcelVo> categoryExcelVoList = new ArrayList<>();
            for (Category category : categoryList) {
                CategoryExcelVo categoryExcelVo = new CategoryExcelVo();
                //获取category的值设置到categoryExcelVo中去
                BeanUtils.copyProperties(category, categoryExcelVo);
                categoryExcelVoList.add(categoryExcelVo);
            }
            //3 调用EasyExcel的写write方法完成写操作
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class)
                    .sheet("分类数据").doWrite(categoryExcelVoList);

        } catch (Exception e) {
            e.printStackTrace();
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
    }
}
