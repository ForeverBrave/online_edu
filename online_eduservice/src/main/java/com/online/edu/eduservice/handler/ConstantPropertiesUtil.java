package com.online.edu.eduservice.handler;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author : Brave
 * @Version : 1.0
 * @Date : 2020/2/3 14:25
 */
//在服务器启动的时候，让这个类读取配置文件内容
@Component
public class ConstantPropertiesUtil implements InitializingBean {

    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.accesskeyid}")
    private String accesskeyid;

    @Value("${aliyun.oss.file.accesskeysecret}")
    private String accesskeysecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketname;

    //定义常量，为了能够使用
    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        ACCESS_KEY_ID = accesskeyid;
        ACCESS_KEY_SECRET = accesskeysecret;
        BUCKET_NAME = bucketname;
    }
}
