package com.online.edu.eduservice.entity.dto;

import lombok.Data;

/**
 * @Author : Brave
 * @Version : 1.0
 * @Date : 2020/2/4 13:35
 *
 * 用于表示二级分类
 */
@Data
public class TwoSubjectDto {
    //二级分类id
    private String id;
    //二级分类名称
    private String title;
}
