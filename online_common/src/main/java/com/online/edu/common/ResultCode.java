package com.online.edu.common;

/**
 * @Author : Brave
 * @Version : 1.0
 * @Date : 2020/1/31 13:37
 *
 * 定义返回数据使用的状态码
 */
public interface ResultCode {

    //成功状态码
    int SUCCESS = 20000;
    //失败状态码
    int ERROR = 20001;
    //没有操作权限状态码
    int AUTH = 30000;

}
