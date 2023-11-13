package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manager.mapper.SysRoleUserMapper;
import com.atguigu.spzx.manager.mapper.SysUserMapper;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    //用户登录
    @Override
    public LoginVo login(LoginDto loginDto) {
        //首先校验验证码，再去校验账号以及密码
        //获取输入的验证码和redis中里面的key的名称，loginDto都可以获取到
        String captcha = loginDto.getCaptcha();
        String key = loginDto.getCodeKey();

        //根据获取redis里面的key，查询redis里面的存储的验证码
        String redisCode = redisTemplate.opsForValue().get("user:validate" + key);
        //比较输入的验证码和redis里面的是否一致
        //不一致，校验失败
        if (StrUtil.isEmpty(redisCode) || !StrUtil.equalsIgnoreCase(redisCode, captcha)) {
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        //一致，删除redis中的验证码
        redisTemplate.delete("user:validate" + key);

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
        String input_password = DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes());
        //比较
        if (!input_password.equals(database_password)) {
            //throw new RuntimeException("密码错误");
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }
        //6 密码一致 登录成功，不一致，登录失败
        //7 登录成功，生成用户唯一标识token
        String token = UUID.randomUUID().toString().replaceAll("-", "");

        //8 把登录成功的用户信息放到redis中，设置过期时间
        //redis里的key就是token，value就是用户信息
        redisTemplate.opsForValue().set("user:login" + token, JSON.toJSONString(sysUser), 7, TimeUnit.DAYS);
        //9返回loginvo对象
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        return loginVo;
    }

    //获取当前登录用户信息
    @Override
    public SysUser getUserInfo(String token) {
        String userJson = redisTemplate.opsForValue().get("user:login" + token);
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }

    //退出方法
    @Override
    public void logout(String token) {
        //删除redis中存储的用户token
        redisTemplate.delete("user:login" + token);
    }


    //1 用户的条件查询接口
    @Override
    public PageInfo<SysUser> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> list = sysUserMapper.findByPage(sysUserDto);
        PageInfo<SysUser> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    //2 用户添加接口
    @Override
    public void saveSysUser(SysUser sysUser) {
        //1 判断用户名不能重复
        String userName = sysUser.getUserName();
        SysUser dbSysUser = sysUserMapper.selectUserInfoByUserName(userName);
        if (dbSysUser != null) {
            throw new GuiguException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        //2 输入的密码进行加密处理
        String md5_password = DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes());
        sysUser.setPassword(md5_password);
        //设置status的值 1 代表可用
        sysUser.setStatus(1);
        sysUserMapper.save(sysUser);
    }

    //3 用户的修改接口
    @Override
    public void updateSysUser(SysUser sysUser) {
        sysUserMapper.update(sysUser);
    }

    //4 用户的删除接口
    @Override
    public void deleteById(Long userId) {
        sysUserMapper.delete(userId);
    }

    //用户分配角色
    @Override
    public void doAssign(AssginRoleDto assginRoleDto) {
        //1 根据userId删除用户之前分配的角色数据
        sysRoleUserMapper.deleteByUserId(assginRoleDto.getUserId());
        //2重新分配新的数据
        List<Long> roleIdList = assginRoleDto.getRoleIdList();
        //遍历得到每个角色id
        for (Long roleId : roleIdList) {
            sysRoleUserMapper.doAssign(assginRoleDto.getUserId(),roleId);
        }
    }
}
