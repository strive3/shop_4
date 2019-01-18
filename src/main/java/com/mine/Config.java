package com.mine;

import com.mine.filter.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author 杜晓鹏
 * @create 2019-01-17 11:44
 *
 * 在入口类的目录或者兄弟目录下创建一个类继承WebMvcConfigurerAdapter类并重写addInterceptors方法
 * @SpringBootConfiguration注解表明这是一个配置类
 */
@SpringBootConfiguration
public class Config extends WebMvcConfigurerAdapter {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**");
    }
}
