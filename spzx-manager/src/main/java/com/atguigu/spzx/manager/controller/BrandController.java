package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.BrandService;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin/product/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    //品牌列表查询
    @Operation(summary = "品牌的查询")
    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit){
        PageInfo<Brand> pageInfo = brandService.findByPage(page, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //品牌列表添加
    @Operation(summary = "品牌添加")
    @PostMapping("/save")
    public Result save(@RequestBody Brand brand){
        brandService.save(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //品牌修改
    @Operation(summary = "品牌添加")
    @PutMapping("/updateById")
    public Result updateById(@RequestBody Brand brand){
        brandService.updateById(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //删除品牌
    @Operation(summary = "品牌删除")
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable("id") Long id){
        brandService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
