package com.online.edu.eduservice.entity.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : Brave
 * @Version : 1.0
 * @Date : 2020/2/4 13:35
 *
 * 用于表示一级分类
 */
@Data
public class OneSubjectDto {

    //一级分类id
    private String id;
    //一级分类名称
    private String title;
    //一级分类所有的二级分类
    private List<TwoSubjectDto> children = new ArrayList<>();

}
