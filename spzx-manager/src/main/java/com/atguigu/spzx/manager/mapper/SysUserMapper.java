package com.atguigu.spzx.manager.mapper;


import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper {
    SysUser selectUserInfoByUserName(String userName);

    //1 用户的条件查询接口
    List<SysUser> findByPage(SysUserDto sysUserDto);

    //2 用户添加接口
    void save(SysUser sysUser);

    //3 用户的修改接口
    void update(SysUser sysUser);

    //4 用户的删除接口
    void delete(Long userId);
}
