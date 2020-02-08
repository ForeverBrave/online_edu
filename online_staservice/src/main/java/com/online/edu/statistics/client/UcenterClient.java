package com.online.edu.statistics.client;

import com.online.edu.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author : Brave
 * @Version : 1.0
 * @Date : 2020/2/8 13:51
 */
@FeignClient("online-ucservice")
@Component
public interface UcenterClient {

    /**
     * 统计某一天里注册的人数
     * @param day
     * @return
     */
    @GetMapping("/ucenter/member/countRegisterNum/{day}")
    public R countRegisterNum(@PathVariable("day") String day);

}
