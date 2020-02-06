package com.online.edu.eduservice.entity.query;

import lombok.Data;

/**
 * @Author : Brave
 * @Version : 1.0
 * @Date : 2020/1/31 15:08
 *
 * 用于封装条件值
 */
@Data
public class QueryTeacher {

    private String name;
    private String level;
    //开始时间
    private String begin;
    //结束时间
    private String end;
}
