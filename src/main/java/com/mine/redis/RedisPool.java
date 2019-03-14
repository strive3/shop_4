package com.mine.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author 杜晓鹏
 * @create 2019-01-21 16:09
 */
@Component
@Configuration
public class RedisPool {

    @Autowired
    RedisProperties redisProperties;

    @Bean
    public JedisPool jedisPool(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(redisProperties.getMaxTotal());
        jedisPoolConfig.setMaxIdle(redisProperties.getMaxIdle());
        jedisPoolConfig.setMinIdle(redisProperties.getMinIdle());

        jedisPoolConfig.setTestOnReturn(redisProperties.isTestReturn());
        jedisPoolConfig.setTestOnBorrow(redisProperties.isTestBorrow());
        //当连接池中的连接消耗完毕，true：等待连接，false：抛出异常
        jedisPoolConfig.setBlockWhenExhausted(true);

        return new JedisPool(jedisPoolConfig,redisProperties.getRedisIp(),redisProperties.getRedisPort(),2000,redisProperties.getPassword(),0);
    }

}
