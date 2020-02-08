package com.online.edu.statistics.controller;


import com.online.edu.common.R;
import com.online.edu.statistics.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author Brave
 * @since 2020-02-08
 */
@RestController
@RequestMapping("/statistics/daily")
public class DailyController {

    @Autowired
    private DailyService dailyService;

    /**
     * 获取某一天用户注册人数
     * @param day
     * @return
     */
    @GetMapping("getStatisticsDay/{day}")
    public R getStatisticsDay(@PathVariable String day){
        dailyService.getCountRegisterNum(day);
        return R.ok();
    }

}

