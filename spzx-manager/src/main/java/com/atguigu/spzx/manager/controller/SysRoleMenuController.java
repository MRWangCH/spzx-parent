package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysRoleMenuService;
import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/admin/system/sysRoleMenu")
public class SysRoleMenuController {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;


    //1 查询所有菜单 和 查询角色分配过的菜单列表
    @Operation(summary = "角色分配的菜单列表")
    @GetMapping("/findSysRoleMenuByRoleId/{roleId}")
    public Result findSysRoleMenuByRoleId(@PathVariable("roleId") Long roleId){
        Map<String, Object> map = sysRoleMenuService.findSysRoleMenuByRoleId(roleId);
        return Result.build(map, ResultCodeEnum.SUCCESS);
    }

    //2 保存角色分配的菜单数据
    @Operation(summary = "保存角色分配的菜单数据")
    @PostMapping("doAssign")
    public Result doAssign(@RequestBody AssginMenuDto assginMenuDto){
        //删除角色之前分配的菜单数据
        sysRoleMenuService.doAssign(assginMenuDto);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

}
