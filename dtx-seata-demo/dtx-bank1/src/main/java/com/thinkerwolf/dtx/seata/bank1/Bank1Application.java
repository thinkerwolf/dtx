package com.thinkerwolf.dtx.seata.bank1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients
@EnableDiscoveryClient
@EnableCircuitBreaker
@MapperScan("com.thinkerwolf.dtx.seata.bank1.mapper")
public class Bank1Application {
    public static void main(String[] args) {
        SpringApplication.run(Bank1Application.class, args);
    }
}
