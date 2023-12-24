package com.atguigu.spzx.user.mapper;

import com.atguigu.spzx.model.entity.user.UserAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserAddressMapper {
    //获取用户地址列表
    List<UserAddress> findUserAddressList(Long userId);
    //根据id获取收货的地址信息
    UserAddress getUserAddress(Long id);
}
