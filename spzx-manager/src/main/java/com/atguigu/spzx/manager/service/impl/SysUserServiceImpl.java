package com.atguigu.spzx.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manager.mapper.SysUserMapper;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //用户登录
    @Override
    public LoginVo login(LoginDto loginDto) {
        //1 获取用户名
        String userName = loginDto.getUserName();
        //2 根据用户名查询数据库表
        SysUser sysUser = sysUserMapper.selectUserInfoByUserName(userName);
        //3 如果查不到，用户不存在，返回错误信息
        if (sysUser == null) {
            //throw new RuntimeException("用户名不存在!");
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }
        //4 根据用户名查到用户信息 用户存在
        //5 获取输入的密码，比较输入的密码和数据库里的密码是否一致
        String database_password = sysUser.getPassword();
        //把输入的密码先加密在与数据库比较 md5
        String input_password = DigestUtils.md5DigestAsHex( loginDto.getPassword().getBytes());
        //比较
        if (!input_password.equals(database_password)){
            //throw new RuntimeException("密码错误");
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }
        //6 密码一致 登录成功，不一致，登录失败
        //7 登录成功，生成用户唯一标识token
        String token = UUID.randomUUID().toString().replaceAll("-","");

        //8 把登录成功的用户信息放到redis中，设置过期时间
        //redis里的key就是token，value就是用户信息
        redisTemplate.opsForValue().set("user:login" + token, JSON.toJSONString(sysUser), 7, TimeUnit.DAYS);
        //9返回loginvo对象
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        return loginVo;
    }
}
