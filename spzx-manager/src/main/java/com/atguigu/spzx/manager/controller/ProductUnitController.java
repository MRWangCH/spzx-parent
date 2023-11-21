package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.ProductUnitService;
import com.atguigu.spzx.model.entity.base.ProductUnit;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/product/productUnit")
public class ProductUnitController {


    @Autowired
    private ProductUnitService productUnitService;

    //查询所有计量单位
    @Operation(summary = "查询所有计量单位")
    @GetMapping("/findAll")
    public Result findAll() {
        List<ProductUnit> list = productUnitService.findAll();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
}
