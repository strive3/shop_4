package com.mine.json;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 杜晓鹏
 * @create 2019-01-22 20:00
 */
@Component
public class ObjectMapperApi {

    @Autowired
    ObjectMapper objectMapper;

    /**
     * 将java对象转化为字符串
     */
    public <T> String obj2String(T obj) {
        if (obj == null)
            return null;
        try {
            //将obj转化为json字符串
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public <T> String obj2StringPretty(T obj) {
        if (obj == null)
            return null;
        try {
            //将obj转化为格式化的json字符串
            return obj instanceof String ? (String) obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 将字符串转化为java对象
     */
    public <T>T string2obj(String string , Class<T> clazz){
        if (StringUtils.isEmpty(string) || clazz == null)
            return null;

        try {
            return clazz.equals(String.class)?(T)string:objectMapper.readValue(string,clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 将json数组转化为java集合
     */
    public <T>T string2obj(String string , TypeReference<T> typeReference){
        if (StringUtils.isEmpty(string) || typeReference == null)
            return null;

        try {
            return typeReference.getType().equals(String.class)?(T)string:objectMapper.readValue(string,typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
