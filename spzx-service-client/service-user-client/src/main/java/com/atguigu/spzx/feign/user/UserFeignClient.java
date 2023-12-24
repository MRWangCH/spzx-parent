package com.atguigu.spzx.feign.user;

import com.atguigu.spzx.model.entity.user.UserAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-user")
public interface UserFeignClient {
    //根据id获取收货的地址信息
    @GetMapping("/api/user/userAddress/getUserAddress/{id}")
    public UserAddress getUserAddress(@PathVariable("id") Long id);
}
