package com.teng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

@MapperScan("com.teng.dao.mapper")
public class Miaosha01Application {

    public static void main(String[] args) {
        SpringApplication.run(Miaosha01Application.class, args);
    }

}
