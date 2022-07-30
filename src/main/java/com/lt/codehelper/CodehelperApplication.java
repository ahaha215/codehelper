package com.lt.codehelper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lt.codehelper.mapper")
public class CodehelperApplication {
    public static void main(String[] args) {
        SpringApplication.run(CodehelperApplication.class, args);
    }
}
