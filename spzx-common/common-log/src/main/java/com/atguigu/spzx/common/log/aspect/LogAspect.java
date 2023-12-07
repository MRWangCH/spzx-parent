package com.atguigu.spzx.common.log.aspect;

import com.atguigu.spzx.common.log.annotation.Log;
import com.atguigu.spzx.common.log.service.AsyncOperLogService;
import com.atguigu.spzx.common.log.utils.LogUtil;
import com.atguigu.spzx.model.entity.system.SysOperLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    @Autowired
    private AsyncOperLogService operLogService;

    @Around(value = "@annotation(sysLog)")
    //环绕通知
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint, Log sysLog){

        //业务方法之前执行封装数据
        SysOperLog sysOperLog = new SysOperLog();
        LogUtil.beforeHandleLog(sysLog, joinPoint,sysOperLog);

        //业务方法
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();

            //业务方法之后执行封装数据
            LogUtil.afterHandlLog(sysLog,proceed,sysOperLog,0,null);

        } catch (Throwable e) {
            e.printStackTrace();
            LogUtil.afterHandlLog(sysLog,proceed,sysOperLog,1,e.getMessage());
            throw new RuntimeException();
        }

        //调用service方法把日志存到库里
        operLogService.saveSysOperLog(sysOperLog);
        return proceed;
    }
}
