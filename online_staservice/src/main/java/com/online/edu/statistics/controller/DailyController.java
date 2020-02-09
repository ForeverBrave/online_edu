package com.online.edu.statistics.controller;


import com.online.edu.common.R;
import com.online.edu.statistics.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
@CrossOrigin
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

    /**
     * 返回图表显示使用的数据
     * 第一部分，时间['2020-01-01','2020-02-01']
     * 第二部分，数量[5,3]
     * @param type   查询因子(查询什么数据，如注册人数等)
     * @param begin  开始时间
     * @param end    结束时间
     * @return
     */
    @GetMapping("getDataCount/{type}/{begin}/{end}")
    public R getDataCount(@PathVariable String type,
                          @PathVariable String begin,
                          @PathVariable String end){
        Map<String,Object> map = dailyService.getDataCount(type,begin,end);
        return R.ok().data("mapData",map);
    }

}

