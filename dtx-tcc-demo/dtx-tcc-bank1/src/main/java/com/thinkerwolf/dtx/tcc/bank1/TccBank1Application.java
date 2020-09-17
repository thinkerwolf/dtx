package com.thinkerwolf.dtx.tcc.bank1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableAspectJAutoProxy
@EnableFeignClients
@MapperScan({"com.thinkerwolf.dtx.tcc.bank1.mapper"})
public class TccBank1Application {

    public static void main(String[] args) {
        SpringApplication.run(TccBank1Application.class, args);
    }
}
