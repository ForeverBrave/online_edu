package com.online.edu.vidservice.controller;

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
        String videoId = vidService.uploadAliyunVideo(file);
        return R.ok().data("videoId",videoId);
    }
}
