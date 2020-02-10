package com.online.edu.vidservice.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.online.edu.common.R;
import com.online.edu.vidservice.service.VidService;
import com.online.edu.vidservice.utils.AliyunVODSDKUtils;
import com.online.edu.vidservice.utils.ConstantPropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    /**
     * 删除阿里云多个视频
     * @param videoList   参数为多个视频id的list集合
     * @return
     */
    @DeleteMapping("deleteMoreAliyunVideo")
    public R deleteMoreAliyunVideo(@RequestParam("videoList") List videoList){
        try {
            vidService.deleteMoreAliyunVideo(videoList);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 根据视频id获取播放凭证
     * @param vid
     * @return
     */
    @GetMapping("getPlayAuth/{vid}")
    public R getPlayAutoId(@PathVariable String vid){
        try {
            //初始化客户端、请求对象和相应对象
            DefaultAcsClient client = AliyunVODSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID,ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

            //设置请求参数
            request.setVideoId(vid);
            //获取请求响应
            response = client.getAcsResponse(request);

            //输出请求结果
            //播放凭证
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }

    }

}
