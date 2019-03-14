package com.mine.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author 杜晓鹏
 * @create 2019-01-21 10:46
 */
@Component
@Aspect
public class LogAspect {

    @Pointcut("execution(* com.mine.service.impl.UserServiceImpl.*(..)))")
    public void pointcut(){}

    @Before("pointcut()")
    public void before(JoinPoint joinPoint){
        System.out.println(joinPoint.getSignature().getName()+":=========this is before aspect");
    }

    @After("pointcut()")
    public void after(JoinPoint joinPoint){
        System.out.println(joinPoint.getSignature().getName()+":=========this is after aspect");
    }
}
