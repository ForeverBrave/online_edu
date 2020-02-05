package com.online.edu.eduservice.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.online.edu.common.R;
import com.online.edu.eduservice.handler.ConstantPropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Author : Brave
 * @Version : 1.0
 * @Date : 2020/2/3 12:00
 */
@RestController
@RequestMapping("/eduservice/oss")
@CrossOrigin
public class FileUploadController {

    /**
     * 上传讲师头像的方法
     * @param file
     * @return
     */
    @PostMapping("upload")
    public R uploadTeacherIcon(@RequestParam("file") MultipartFile file,@RequestParam(value = "host",required = false) String host){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtil.END_POINT;
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String yourBucketName = ConstantPropertiesUtil.BUCKET_NAME;
        try {
            //1、获取到上传文件MultipartFile file
            //2、获取上传文件名称，获取上传文化输入法
            String filename = file.getOriginalFilename();
            //在文件名之前加上uuid，保证文件名称不重复（防止覆盖问题）
            String uuid = UUID.randomUUID().toString();
            filename = uuid+filename;

            //构建日期路径：2020/02/03
            String filePath = new DateTime().toString("yyyy/MM/dd");

            //拼接文件完整路径  /2020/02/03/文件名
            //filename = filePath + "/" +filename;

            String hostName = ConstantPropertiesUtil.HOST;
            //如果上传的是头像，则host里为空，如果上传封面host则有值
            if (StringUtils.isNotEmpty(host)) {
                hostName = host;
            }

            filename = filePath+"/"+hostName+"/"+filename;

            InputStream inputStream = file.getInputStream();

            //3、把上传文件存储到阿里云oss里面
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流。
            ossClient.putObject(yourBucketName, filename, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //http://.......//2020/02/03/career/01.jpg
            String path = "http://"+yourBucketName+"."+endpoint+"/"+filename;

            return R.ok().data("imgurl",path);

        } catch (IOException e) {
            e.printStackTrace();
            return R.error();
        }

    }

}
