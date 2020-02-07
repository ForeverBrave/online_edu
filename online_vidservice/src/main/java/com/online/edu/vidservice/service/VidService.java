package com.online.edu.vidservice.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author : Brave
 * @Version : 1.0
 * @Date : 2020/2/7 14:04
 */
public interface VidService {

    /**
     * 实现上传视频到阿里云服务器的方法
     * @param file
     * @return
     */
    String uploadAliyunVideo(MultipartFile file);

    /**
     * 实现删除云端视频的方法
     * @param videoId
     */
    void deleteAliyunVideoById(String videoId) throws ClientException;
}
