package com.mine.controller;

import com.mine.dao.UserInfoMapper;
import com.mine.json.ObjectMapperApi;
import com.mine.pojo.UserInfo;
import com.mine.redis.RedisApi;
import com.mine.redis.RedisProperties;
import com.mine.service.UserService;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;


/**
 * @author 杜晓鹏
 * @create 2019-01-04 15:51
 */
@RestController
@RequestMapping("/ppppp")
public class TestController {
    @Autowired
    UserInfoMapper userInfoMapper;


    @Autowired
    RedisApi redisApi;

    @Autowired
    RedisProperties redisProperties;
    @RequestMapping("user/{userid}")
    public UserInfo test(@PathVariable Integer userid){
        System.out.println(111111);
        System.out.println(redisProperties.getMaxIdle());
        return userInfoMapper.selectByPrimaryKey(userid);
    }
    @RequestMapping("/redis/{key}/{value}")
    public String set(@PathVariable("key") String key,@PathVariable("value")String value){
        String set = redisApi.set(key, value);
        return set;
    }

    @RequestMapping("/redis/{key}")
    public String getKey(@PathVariable("key") String key){
        String set = redisApi.get(key);
        return set;
    }
    @Autowired
    private JedisPool jedisPool;
    @RequestMapping("/redis")
    public String getJedis(){
        Jedis resource = jedisPool.getResource();
        String set = resource.set("root", "root1");
        jedisPool.returnResource(resource);
        return set;
    }

    @Autowired
    UserService userService;
    @Autowired
    ObjectMapperApi objectMapperApi;
    @RequestMapping("/json/{userid}")
    public String getJson(@PathVariable Integer userid){
        UserInfo userInfo = userService.selectById(userid);
        String s = objectMapperApi.obj2String(userInfo);
        String s1 = objectMapperApi.obj2StringPretty(userInfo);
        UserInfo userInfo1 = objectMapperApi.string2obj(s,UserInfo.class);
        List<UserInfo> list = new ArrayList<>();
        list.add(userInfo);
        String s2 = objectMapperApi.obj2String(list);
        List<UserInfo> list1 = objectMapperApi.string2obj(s2, new TypeReference<List<UserInfo>>() {
        });
        System.out.println("list1:"+list1);
        System.out.println("userinfo"+userInfo1);
        System.out.println("1:"+s);
        System.out.println("2:"+s1);

        return "ok";
    }
}
