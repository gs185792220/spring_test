package com.main.test.config;

import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@MapperScan(basePackages = "com.movit.test.*.dao")
public class MybatisConfig {

}
