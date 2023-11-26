package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.ProductService;
import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    //添加商品信息
    @Operation(summary = "添加商品信息")
    @PostMapping("/save")
    public Result save(@RequestBody Product product){
        productService.save(product);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //根据商品id查询商品信息
    @Operation(summary = "根据商品id查询商品信息")
    @GetMapping("/getById/{id}")
    public Result getById(@PathVariable Long id){
        Product product = productService.getById(id);
        return Result.build(product, ResultCodeEnum.SUCCESS);
    }

    //保存修改数据
    @Operation(summary = "保存修改数据")
    @PutMapping("/updateById")
    public Result updateById(@RequestBody Product product){
        productService.update(product);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    //删除商品
    @Operation(summary = "删除商品")
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@Parameter(name = "id", description = "商品id", required = true) @PathVariable Long id){
        productService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //商品审核接口
//    @Operation(summary = "商品审核接口")
//    @GetMapping("/updateAuditStatus/{id}/{auditStatus}")
//    public Result updateAuditStatus(@PathVariable Long id, @PathVariable Integer auditStatus) {
//        productService.updateAuditStatus(id, auditStatus);
//        return Result.build(null , ResultCodeEnum.SUCCESS) ;
//    }


}
