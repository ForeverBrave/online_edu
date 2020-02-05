package com.online.edu.eduservice.entity.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : Brave
 * @Version : 1.0
 * @Date : 2020/2/5 20:16
 *
 * 章节dto对象
 * dto思想：需要什么，构建什么
 */
@Data
public class EduChapterDto {

    private String id;
    private String title;

    List<EduVideoDto> children = new ArrayList<>();
}
