package com.online.edu.ucenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Author : Brave
 * @Version : 1.0
 * @Date : 2020/2/8 13:13
 */
@SpringBootApplication
@EnableEurekaClient
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class);
    }
}
