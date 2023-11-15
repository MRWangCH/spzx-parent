package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/product/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //导入
    @Operation(summary = "分类的导入")
    @PostMapping("/importData")
    public Result importData(MultipartFile file){
        //获取上传文件
        categoryService.importData(file);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }



    //导出 文件的下载需要用到HttpServletResponse对象
    @Operation(summary = "分类的导出")
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response){
        categoryService.exportData(response);
    }


    //分类列表，每次查询一层的数据
    //Select * from category where parent_id = id
    @Operation(summary = "查询分类")
    @GetMapping("/findCategoryList/{id}")
    public Result findCategoryList (@PathVariable("id") Long id){
        //查询分类，每次查一层数据
        List<Category> list = categoryService.findCategoryList(id);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
}
