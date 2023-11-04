package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.system.LoginVo;

public interface SysUserService {
    //登录方法
    LoginVo login(LoginDto loginDto);
    //获取当前登录用户信息
    SysUser getUserInfo(String token);
}
