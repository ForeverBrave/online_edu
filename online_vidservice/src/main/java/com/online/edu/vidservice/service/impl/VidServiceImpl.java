package com.online.edu.vidservice.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.online.edu.vidservice.service.VidService;
import com.online.edu.vidservice.utils.AliyunVODSDKUtils;
import com.online.edu.vidservice.utils.ConstantPropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author : Brave
 * @Version : 1.0
 * @Date : 2020/2/7 14:05
 */
@Service
public class VidServiceImpl implements VidService {

    /**
     * 实现上传视频到阿里云服务器的方法
     * @param file
     * @return
     */
    @Override
    public String uploadAliyunVideo(MultipartFile file) {

        try {
            String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
            String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
            String fileName = file.getOriginalFilename();
            String title = fileName.substring(0,fileName.lastIndexOf("."));
            InputStream inputStream = file.getInputStream();

            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 实现删除云端视频的方法
     * @param videoId
     */
    @Override
    public void deleteAliyunVideoById(String videoId){

        try {
            //初始化操作
            String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
            String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
            DefaultAcsClient client = AliyunVODSDKUtils.initVodClient(accessKeyId, accessKeySecret);

            //创建删除视频的请求对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //设置删除视频id
            request.setVideoIds(videoId);
            //调用方法,实现删除
            DeleteVideoResponse response = client.getAcsResponse(request);


        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除阿里云多个视频
     * @param videoList
     */
    @Override
    public void deleteMoreAliyunVideo(List videoList) {

        try {
            //初始化操作
            String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
            String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
            DefaultAcsClient client = AliyunVODSDKUtils.initVodClient(accessKeyId, accessKeySecret);

            //创建删除视频的请求对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //设置删除多个视频id
            //StringUtils.join(value1,value2);
            //value1: 数组   value2: 根据什么内容进行分割
            String videoIds = StringUtils.join(videoList.toArray(), ",");
            request.setVideoIds(videoIds);
            //调用方法,实现删除
            DeleteVideoResponse response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }

}
