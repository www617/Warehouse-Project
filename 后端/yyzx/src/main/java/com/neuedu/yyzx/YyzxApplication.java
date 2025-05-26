package com.neuedu.yyzx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@MapperScan(basePackages = "com.neuedu.yyzx.mapper") // MyBatis Mapper扫描
@EntityScan(basePackages = "com.neuedu.yyzx.pojo") // JPA实体类扫描
//@EnableJpaAuditing // 启用JPA审计功能（可选，如果你使用了@CreatedDate和@LastModifiedDate注解）
public class YyzxApplication {

    public static void main(String[] args) {
        SpringApplication.run(YyzxApplication.class, args);
    }
}