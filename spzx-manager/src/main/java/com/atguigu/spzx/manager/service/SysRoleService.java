package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.github.pagehelper.PageInfo;

public interface SysRoleService {
    //列表查询
    PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer limit);
    //添加角色
    void saveSysRole(SysRole sysRole);
}