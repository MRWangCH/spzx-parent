package com.atguigu.spzx.user.mapper;

import com.atguigu.spzx.model.entity.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper {
    //校验用户是否存在
   UserInfo selectByUsername(String username);
    //添加
    void save(UserInfo userInfo);
}
