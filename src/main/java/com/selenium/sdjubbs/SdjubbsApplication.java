package com.selenium.sdjubbs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@MapperScan("com.selenium.sdjubbs.common.mapper")
public class SdjubbsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SdjubbsApplication.class, args);
    }

}
