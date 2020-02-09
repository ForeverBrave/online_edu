package com.online.edu.statistics.service;

import com.online.edu.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author Brave
 * @since 2020-02-08
 */
public interface DailyService extends IService<Daily> {

    /**
     * 获取某一天用户注册人数
     * @param day
     */
    void getCountRegisterNum(String day);

    /**
     * 返回图表显示使用的数据
     * @param type
     * @param begin
     * @param end
     * @return
     */
    Map<String, Object> getDataCount(String type, String begin, String end);
}
