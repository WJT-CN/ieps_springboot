package com.wjt.ieps;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.wjt.ieps.mapper")
public class IepsApplication {

    public static void main(String[] args) {
        SpringApplication.run(IepsApplication.class, args);
    }

}
