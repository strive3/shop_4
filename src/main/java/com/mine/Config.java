package com.mine;

import com.mine.filter.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 杜晓鹏
 * @create 2019-01-17 11:44
 * 拦截器   用作自动登录
 * 在入口类的目录或者兄弟目录下创建一个类继承WebMvcConfigurerAdapter类并重写addInterceptors方法
 * @SpringBootConfiguration注解表明这是一个配置类
 */
@SpringBootConfiguration
public class Config implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        List<String> excludeList = new ArrayList<>();
        excludeList.add("/protal/user/register.do");
        excludeList.add("/protal/user/login.do");
        excludeList.add("/protal/user/logout.do");
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns(excludeList);
    }
}
