package com.atguigu.spzx.common.log.aspect;

import com.atguigu.spzx.common.log.annotation.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    @Around(value = "@annotation(sysLog)")
    //环绕通知
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint, Log sysLog){

        //业务方法之前执行
        String title = sysLog.title();
        int bussinessType = sysLog.businessType();
        System.out.println("title:" + title + ", bussinessType = " + bussinessType );

        //业务方法
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();

            //业务方法之后执行
            System.out.println("在业务方法之后执行。。。");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return proceed;
    }
}
