package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper {
    //角色列表
    List<SysRole> findByPage(SysRoleDto sysRoleDto);
    //添加角色
    void save(SysRole sysRole);
}
