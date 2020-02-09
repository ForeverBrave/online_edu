package com.online.edu.statistics.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author : Brave
 * @Version : 1.0
 * @Date : 2020/2/8 13:30
 */
@EnableTransactionManagement
@Configuration
@MapperScan({"com.online.edu.statistics.mapper"})
public class StatisticsConfig {
}
