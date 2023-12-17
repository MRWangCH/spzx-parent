package com.atguigu.spzx.user.service.impl;

import com.alibaba.excel.read.listener.ReadListener;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.model.dto.h5.UserRegisterDto;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.user.mapper.UserInfoMapper;
import com.atguigu.spzx.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //用户注册
    @Override
    public void register(UserRegisterDto userRegisterDto) {
        //1 从userRegister中获取相关数据
        String username = userRegisterDto.getUsername();
        String password = userRegisterDto.getPassword();
        String nickName = userRegisterDto.getNickName();
        String code = userRegisterDto.getCode();

        //2 验证码的校验
        //2.1 从redis中获取验证码
        String redisCode = redisTemplate.opsForValue().get(username);
        //2.2 获取输入的验证码，进行比对
        if (!redisCode.equals(code)){
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        //3 校验用户名不能重复
        UserInfo userInfo = userInfoMapper.selectByUsername(username);
        if (userInfo != null){
            //存在相同用户名
            throw new GuiguException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        //4 封装添加的数据，调用方法添加到数据库
        userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setNickName(nickName);
        userInfo.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userInfo.setPhone(username);
        userInfo.setStatus(1);
        userInfo.setSex(0);
        userInfo.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        userInfoMapper.save(userInfo);
        //5 从redis中删除验证码
        redisTemplate.delete(username);
    }
}
