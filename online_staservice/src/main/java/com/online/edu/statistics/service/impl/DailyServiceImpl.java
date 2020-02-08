package com.online.edu.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.edu.common.R;
import com.online.edu.statistics.client.UcenterClient;
import com.online.edu.statistics.entity.Daily;
import com.online.edu.statistics.mapper.DailyMapper;
import com.online.edu.statistics.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author Brave
 * @since 2020-02-08
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    private UcenterClient ucenterClient;

    /**
     * 获取某一天用户注册人数
     * @param day
     */
    @Override
    public void getCountRegisterNum(String day) {

        //删除 添加统计表里面是存在的天数的记录，
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        this.baseMapper.delete(wrapper);

        R r = ucenterClient.countRegisterNum(day);
        Integer registerCount = (Integer) r.getData().get("registerCount");

        //TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);
        Integer videoViewNum = RandomUtils.nextInt(100, 200);
        Integer loginNum = RandomUtils.nextInt(100, 200);

        Daily daily = new Daily();
        daily.setDateCalculated(day);
        daily.setRegisterNum(registerCount);
        daily.setCourseNum(courseNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);

        this.baseMapper.insert(daily);

    }
}
