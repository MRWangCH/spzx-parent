package com.atguigu.spzx.manager.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleUserMapper {
    //1 根据userId删除用户之前分配的角色数据
    void deleteByUserId(Long userId);
    //2重新分配新的数据
    void doAssign(Long userId, Long roleId);
    //根据用户id查询用户分配过的角色id列表
    List<Long> selectRoleIdsByUserId(Long userId);
}
