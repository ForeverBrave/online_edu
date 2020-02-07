package com.online.edu.vidservice.controller;

import com.aliyuncs.exceptions.ClientException;
import com.online.edu.common.R;
import com.online.edu.vidservice.service.VidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author : Brave
 * @Version : 1.0
 * @Date : 2020/2/7 14:00
 */
@RestController
@RequestMapping("/vidservice/vod")
@CrossOrigin
public class VidController {

    @Autowired
    private VidService vidService;

    /**
     * 实现上传视频到阿里云服务器的方法
     * @return
     */
    @PostMapping("upload")
    public R uploadAliyunVideo(@RequestParam("file") MultipartFile file){
        //调用方法实现视频上传，返回上传之后的视频id
        try {
            String videoId = vidService.uploadAliyunVideo(file);
            return R.ok().data("videoId",videoId);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 实现删除云端视频的方法
     * @param videoId
     * @return
     */
    @DeleteMapping("{videoId}")
    public R deleteAliyunVideoById(@PathVariable String videoId){
        try {
            vidService.deleteAliyunVideoById(videoId);
            return R.ok();
        } catch (ClientException e) {
            e.printStackTrace();
            return R.error();
        }

    }
}
