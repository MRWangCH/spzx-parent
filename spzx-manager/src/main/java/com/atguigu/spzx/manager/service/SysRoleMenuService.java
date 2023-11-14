package com.atguigu.spzx.manager.service;

import java.util.Map;

public interface SysRoleMenuService {
    //1 查询所有菜单 和 查询角色分配过的菜单列表
    Map<String, Object> findSysRoleMenuByRoleId(Long roleId);
}
