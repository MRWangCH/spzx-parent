package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMenuMapper {
    //查询角色分配过的菜单列表
    List<Long> findSysRoleMenuByRoleId(Long roleId);
    //删除角色分配过的菜单数据
    void deleteByRoleId(Long roleId);
    //保存分配的数据
    void doAssign(AssginMenuDto assginMenuDto);
    //把父菜单的is_half改变成半开状态 1
    void updateSysRoleMenuIsHalf(Long menuId);
}
