package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.github.pagehelper.PageInfo;

public interface SysRoleService {
    //列表查询
    PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer limit);
    //添加角色
    void saveSysRole(SysRole sysRole);
    //角色修改的方法
    void updateSysRole(SysRole sysRole);
    //角色删除的方法
    void deleteById(Long roleId);
}
