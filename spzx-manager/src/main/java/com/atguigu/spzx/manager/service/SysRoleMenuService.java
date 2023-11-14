package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.AssginMenuDto;

import java.util.Map;

public interface SysRoleMenuService {
    //1 查询所有菜单 和 查询角色分配过的菜单列表
    Map<String, Object> findSysRoleMenuByRoleId(Long roleId);
    //删除角色之前分配的菜单数据
    //分配菜单
    void doAssign(AssginMenuDto assginMenuDto);
}
