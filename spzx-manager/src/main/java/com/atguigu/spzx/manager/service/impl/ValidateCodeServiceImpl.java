package com.atguigu.spzx.manager.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.atguigu.spzx.manager.service.ValidateCodeService;
import com.atguigu.spzx.model.vo.system.ValidateCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public ValidateCodeVo generateValidateCode() {
        //1 通过工具生成图片的验证码
        //hutool
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 48, 4, 5);
        String codeValue = circleCaptcha.getCode();//4位验证码值
        String imageBase64 = circleCaptcha.getImageBase64();//图片验证码，base64编码方式

        //2 把验证码存到redis中，设置redis的key：uuid，value：验证码的值
        //设置过期时间
        String key = UUID.randomUUID().toString().replaceAll("-","");
        redisTemplate.opsForValue().set("user:validate" + key, codeValue,5, TimeUnit.MINUTES);

        //3返回 ValidateCodeVo对象
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(key);
        validateCodeVo.setCodeValue("data:image/png;base64," + imageBase64);
        return validateCodeVo;
    }
}
