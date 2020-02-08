package com.online.edu.statistics.service;

import com.online.edu.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
