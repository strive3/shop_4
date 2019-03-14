package com.mine.redis;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 杜晓鹏
 * @create 2019-01-18 11:12
 */
@Component
@ConfigurationProperties
@Data
public class RedisProperties {

    //最大连接数
    @Value("${redis.max.total}")
    private int maxTotal;
    //最大空闲数
    @Value("${redis.max.idle}")
    private int maxIdle;
    //最小空闲数
    @Value("${redis.min.idle}")
    private int minIdle;

    @Value("${redis.test.return}")
    private boolean testReturn;

    @Value("${redis.test.borrow}")
    private boolean testBorrow;

    @Value("${redis.ip}")
    private String redisIp;

    @Value("${redis.port}")
    private int redisPort;

    @Value("${redis.password}")
    private String password;
}
