package com.online.edu.eduservice.entity.dto;

import lombok.Data;

/**
 * @Author : Brave
 * @Version : 1.0
 * @Date : 2020/2/6 13:14
 */
@Data
public class CourseInfoDto {
    private String id;
    private String title;
    private String cover;
    private String price;
    private String description;
    private String teacherName;//讲师名称
    private String levelOne;//一级分类名称
    private String levelTwo;//二级分类名称
}
