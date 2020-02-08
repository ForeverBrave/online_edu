package com.online.edu.ucenter.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author : Brave
 * @Version : 1.0
 * @Date : 2020/2/8 13:21
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.online.edu.ucenter.mapper")
public class UcenterConfig {
}
