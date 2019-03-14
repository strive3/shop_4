package com.mine.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mine.pojo.UserInfo;

import java.lang.reflect.Type;

/**
 * @author 杜晓鹏
 * @create 2019-03-14 16:09
 */
public class TestGson {
    public static void main(String[] args) {
        //将对象转化为json字符串
        UserInfo user = new UserInfo();
        user.setUsername("asd");
        user.setAnswer("aaa");
        String json = new Gson().toJson(user);
System.out.println(json);
        //将json字符串转化为 对象
        Type type = new TypeToken<UserInfo>() {}.getType();
        UserInfo userInfo = new Gson().fromJson(json,type);
System.out.println(userInfo);
    }
}
