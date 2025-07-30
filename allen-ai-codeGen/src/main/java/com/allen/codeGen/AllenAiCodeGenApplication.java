package com.allen.codeGen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.allen.codeGen.mapper")
public class AllenAiCodeGenApplication {

    public static void main(String[] args) {
        SpringApplication.run(AllenAiCodeGenApplication.class, args);
    }

} 