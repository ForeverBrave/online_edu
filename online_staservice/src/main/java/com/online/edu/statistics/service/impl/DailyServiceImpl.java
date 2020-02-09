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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 返回图表显示使用的数据
     * @param type
     * @param begin
     * @param end
     * @return
     */
    @Override
    public Map<String, Object> getDataCount(String type, String begin, String end) {
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        //大于等于开始时间，小于等于结束时间
        //wrapper.ge("date_calculated",begin);
        //wrapper.le("date_calculated",end);

        //between  and  方式构建  （在开始时间和结束时间之内的条件）
        wrapper.between("date_calculated",begin,end);
        //查询指定的字段【查询时间和具体查询因子】 (type就是前端传递的字段名)
        wrapper.select("date_calculated",type);
        wrapper.orderByAsc("date_calculated");

        List<Daily> dailyList = this.baseMapper.selectList(wrapper);

        //创建集合用于存储所有的时间
        List<String> timeList = new ArrayList<>();
        //创建集合用于存储所有的数据
        List<Integer> dataList = new ArrayList<>();

        //遍历list集合，并转为map集合
        for (Daily dailys : dailyList) {
            //得到每个daily对象
            Daily daily = dailys;
            String dateCalculated = daily.getDateCalculated();
            //将每次得到的时间放入list集合中
            timeList.add(dateCalculated);

            //因为获取的哪个字段数据不一定，所以要根据type判断,并放入list集合中
            switch (type){
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        //把构建出来的两个list集合放到map集合中
        Map<String,Object> map = new HashMap<>();
        map.put("timeList",timeList);
        map.put("dataList",dataList);

        return map;
    }
}
