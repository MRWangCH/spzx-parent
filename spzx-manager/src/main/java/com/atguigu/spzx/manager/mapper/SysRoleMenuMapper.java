package com.atguigu.spzx.manager.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMenuMapper {
    //查询角色分配过的菜单列表
    List<Long> findSysRoleMenuByRoleId(Long roleId);
}
