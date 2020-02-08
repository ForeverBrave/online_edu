package com.online.edu.vidservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Author : Brave
 * @Version : 1.0
 * @Date : 2020/2/7 13:54
 */
@SpringBootApplication
@EnableEurekaClient
public class VidApplication {
    public static void main(String[] args) {
        SpringApplication.run(VidApplication.class);
    }
}
