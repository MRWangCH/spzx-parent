package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    //菜单列表
    @Operation(summary = "菜单列表展示")
    @GetMapping("/findNodes")
    public Result findNodes(){
        List<SysMenu> list = sysMenuService.findNodes();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    //菜单添加
    @Operation(summary = "菜单添加")
    @PostMapping("/save")
    public Result save(@RequestBody SysMenu sysMenu){
        sysMenuService.save(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }



}
