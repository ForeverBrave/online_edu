package com.online.edu.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @Author : Brave
 * @Version : 1.0
 * @Date : 2020/1/31 10:49
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class EduServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduServiceApplication.class,args);
    }
}
