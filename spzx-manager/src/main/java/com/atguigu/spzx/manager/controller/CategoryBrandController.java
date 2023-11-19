package com.atguigu.spzx.manager.controller;


import com.atguigu.spzx.manager.service.CategoryBrandService;
import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin/product/categoryBrand")
public class CategoryBrandController {

    @Autowired
    private CategoryBrandService categoryBrandService;

    //分类品牌的添加
    @Operation(summary = "分类品牌添加")
    @PostMapping("/save")
    public Result save(@RequestBody CategoryBrand categoryBrand){
        categoryBrandService.save(categoryBrand);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }


    //分类品牌条件分页查询
    @Operation(summary = "分类品牌条件分页查询")
    @GetMapping("/{page}/{limit}")
    public Result findByPage(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit, CategoryBrandDto categoryBrandDto){
        PageInfo<CategoryBrand> pageInfo = categoryBrandService.findByPage(page, limit, categoryBrandDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //分类品牌删除
    @Operation(summary = "分类品牌的删除")
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable("id") Long id){
        categoryBrandService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //分类品牌的修改
    @Operation(summary = "分类品牌的修改")
    @PutMapping("/updateById")
    public Result updateById(@RequestBody CategoryBrand categoryBrand){
        categoryBrandService.updateById(categoryBrand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
