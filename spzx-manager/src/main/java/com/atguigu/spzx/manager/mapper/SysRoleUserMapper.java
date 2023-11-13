package com.atguigu.spzx.manager.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysRoleUserMapper {
    //1 根据userId删除用户之前分配的角色数据
    void deleteByUserId(Long userId);
    //2重新分配新的数据
    void doAssign(Long userId, Long roleId);
}
