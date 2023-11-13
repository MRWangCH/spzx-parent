package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.github.pagehelper.PageInfo;

public interface SysUserService {
    //登录方法
    LoginVo login(LoginDto loginDto);
    //获取当前登录用户信息
    SysUser getUserInfo(String token);
    //退出方法
    void logout(String token);


    //1 用户的条件查询接口
    PageInfo<SysUser> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto);

    //2 用户添加接口
    void saveSysUser(SysUser sysUser);

    //3 用户的修改接口
    void updateSysUser(SysUser sysUser);

    //4 用户的删除接口
    void deleteById(Long userId);

    //用户分配角色
    void doAssign(AssginRoleDto assginRoleDto);

}
