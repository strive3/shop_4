package com.mine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动类
 */
@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
public class Shop40Application {

    public static void main(String[] args) {
        SpringApplication.run(Shop40Application.class, args);
    }

}

