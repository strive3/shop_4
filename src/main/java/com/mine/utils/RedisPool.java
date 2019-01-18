package com.mine.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Jedis;

/**
 * @author 杜晓鹏
 * @create 2019-01-18 11:12
 */

public class RedisPool {
    private static JedisPool pool;
    //最大连接数
    private static Integer maxTotal=Integer.parseInt( PropertiesUtils.getValue("spring.redis.jedis.pool.max-active"));
    //最大空闲数
    private static Integer maxIdle=Integer.parseInt( PropertiesUtils.getValue("spring.redis.jedis.pool.max-idle"));
    //最小空闲数
    private static Integer minIdle=Integer.parseInt( PropertiesUtils.getValue("spring.redis.jedis.pool.min-idle"));


    private static String redisIp=PropertiesUtils.getValue("spring.redis.host");
    private  static  Integer redisPort=Integer.parseInt(PropertiesUtils.getValue("spring.redis.port"));
    static {
        initPool();
    }
    private static void initPool(){
        JedisPoolConfig config=new JedisPoolConfig();

        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);

        //在连接耗尽时，是否阻塞；false：抛出异常，true:等待连接直到超时。默认true
        config.setBlockWhenExhausted(true);

        pool=new JedisPool(config,redisIp,redisPort,1000*2);
    }

    public  static Jedis getJedis(){
        return pool.getResource();
    }

    public  static  void  returnResource(Jedis jedis){
        pool.returnResource(jedis);
    }

    public  static  void  returnBrokenResource(Jedis jedis){
        pool.returnBrokenResource(jedis);
    }

    public static void main(String[] args) {
        Jedis jedis=getJedis();
        jedis.set("username","zhangsan");

        returnResource(jedis);
        pool.destroy();
        System.out.println("=====program is end===");
    }
}
