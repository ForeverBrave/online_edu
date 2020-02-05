package com.online.edu.eduservice.entity.query;

import lombok.Data;

/**
 * @Author : Brave
 * @Version : 1.0
 * @Date : 2020/2/5 15:15
 *
 * 用于封装条件值
 */
@Data
public class QueryCourse {

    private String title;

    private String teacherId;

    private String subjectParentId;

    private String subjectId;

}
