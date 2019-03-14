package com.mine.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author 杜晓鹏
 * @create 2019-01-18 13:31
 */
@Component
public class RedisApi {
    @Autowired
    private JedisPool jedisPool;
    /**
     * @param key
     * @param value 添加key-value
     */
    public String set(String key, String value) {

        Jedis jedis = null;
        String result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        } finally {
            jedisPool.returnResource(jedis);
        }

        return result;
    }


    /**
     * 设置过期时间的key-value
     */
    public String setex(String key, String value, int expireTime) {

        Jedis jedis = null;
        String result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.setex(key, expireTime, value);
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        } finally {
            jedisPool.returnResource(jedis);
        }

        return result;
    }

    /**
     * 根据Key获取value
     */
    public String get(String key) {

        Jedis jedis = null;
        String result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        } finally {
            jedisPool.returnResource(jedis);
        }

        return result;
    }

    /**
     * 删除
     */
    public Long del(String key) {

        Jedis jedis = null;
        Long result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        } finally {
            jedisPool.returnResource(jedis);
        }

        return result;
    }

    /**
     * 设置key的有效时间
     */
    public Long expire(String key, int expireTime) {

        Jedis jedis = null;
        Long result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.expire(key, expireTime);
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        } finally {
            jedisPool.returnResource(jedis);
        }

        return result;
    }

    public static void main(String[] args) {

        //setex("user", "lisi", 10);

    }
}
