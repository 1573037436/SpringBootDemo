package com.zzj.springboot;

import lombok.extern.slf4j.Slf4j;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement  //开启事务
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@MapperScan({"com.zzj.springboot.mapper","com.zzj.springboot.library.mapper","com.zzj.springboot.hr.mapper"}) //@MapperScan会自动把这个包下的接口都加上@Mapper注解
@Slf4j
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        log.info("SpringBoot Demo 启动成功");
    }

}
