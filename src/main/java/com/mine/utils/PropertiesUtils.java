package com.mine.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author 杜晓鹏
 * @create 2019-01-08 15:02
 *
 * 用来加载Properties文件中的内容
 */
public class PropertiesUtils {
    private static Properties properties = new Properties();

    static {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key){
        return properties.getProperty(key);
    }
}
