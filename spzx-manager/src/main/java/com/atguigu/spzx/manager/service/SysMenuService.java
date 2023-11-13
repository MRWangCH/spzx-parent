package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.system.SysMenu;

import java.util.List;

public interface SysMenuService {
    //菜单列表
    List<SysMenu> findNodes();
    //菜单添加
    void save(SysMenu sysMenu);
}
