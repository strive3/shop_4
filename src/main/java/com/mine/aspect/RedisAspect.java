package com.mine.aspect;

import com.mine.common.ServerResponse;
import com.mine.json.ObjectMapperApi;
import com.mine.redis.RedisApi;
import com.mine.utils.MD5Utils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 杜晓鹏
 * @create 2019-01-23 9:31
 * <p>
 * redis缓存切面类
 */
@Component
@Aspect
public class RedisAspect {

    @Pointcut("execution(* com.mine.service.impl.ProductServiceImpl.findAll(..))")
    public void pointcut() {
    }

    @Autowired
    RedisApi redisApi;

    @Autowired
    ObjectMapperApi objectMapperApi;

    /**
     * 这个方法中   少个逻辑   当数据库发生增删改的时候   需要修改缓存
     */
    @Around("pointcut()")
    public Object arround(ProceedingJoinPoint joinPoint) {

        Object o = null;
        try {
            //给redis 中添加key（全类名+方法名+参数）
            StringBuffer keyBuffer = new StringBuffer();
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            keyBuffer.append(className).append(methodName);
            Object[] args = joinPoint.getArgs();
            if (args != null){
                for (Object obj : args){
                    keyBuffer.append(obj);
                }
            }
            String md5Key = MD5Utils.getMD5(keyBuffer.toString());
            String json = redisApi.get(md5Key);
            //判断redis缓存中是否有这个缓存    有的话 直接返回
            if (json != null && !json.equals("")){
                System.out.println("======读取到了缓存======");
                return objectMapperApi.string2obj(json, ServerResponse.class);

            }

            //执行目标方法
            o = joinPoint.proceed();

            //如果没有
            if (o != null) {
                String jsoncache = objectMapperApi.obj2String(o);
                redisApi.set(md5Key,jsoncache);
                System.out.println("======数据库中的内容写入到了缓存======");
            }

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return o;
    }

}
