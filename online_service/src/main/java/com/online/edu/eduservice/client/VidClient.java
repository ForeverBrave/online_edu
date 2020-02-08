package com.online.edu.eduservice.client;

import com.online.edu.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author : Brave
 * @Version : 1.0
 * @Date : 2020/2/8 10:40
 */
@FeignClient("online-vidservice") //找到注册中心里的online-vidservice服务
@Component
public interface VidClient {

    //根据id删除阿里云视频
    @DeleteMapping("/vidservice/vod/{videoId}")
    public R deleteAliyunVideoById(@PathVariable("videoId") String videoId);

    /**
     * 删除多个阿里云视频
     * @param videoList
     * @return
     */
    @DeleteMapping("/vidservice/vod/deleteMoreAliyunVideo")
    public R deleteMoreAliyunVideo(@RequestParam("videoList") List videoList);

}
