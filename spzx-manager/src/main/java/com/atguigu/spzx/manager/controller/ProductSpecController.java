package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.ProductSpecService;
import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin/product/productSpec")
public class ProductSpecController {

    @Autowired
    private ProductSpecService productSpecService;

    //商品规格管理列表分页查询
    @Operation(summary = "商品规格管理查询")
    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit){
        PageInfo<ProductSpec> pageInfo = productSpecService.findByPage(page, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }


    //商品规格管理新增
    @Operation(summary = "商品规格管理新增")
    @PostMapping("/save")
    public Result save(@RequestBody ProductSpec productSpec){
        productSpecService.save(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    //商品规格管理修改
    @Operation(summary = "商品规格管理修改")
    @PutMapping("/updateById")
    public Result update(@RequestBody ProductSpec productSpec){
        productSpecService.updateById(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    //商品规格管理删除
    @Operation(summary = "商品规格管理删除")
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable("id") Long id){
        productSpecService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
