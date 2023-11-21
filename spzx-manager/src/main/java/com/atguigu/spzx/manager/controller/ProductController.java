package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.ProductService;
import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin/product/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    //商品管理列表分页查询
    @Operation(summary = "商品管理列表查询")
    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable("page")  Integer page, @PathVariable("limit") Integer limit, ProductDto productDto){
        PageInfo<Product> pageInfo = productService.findByPage(page, limit, productDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
}
