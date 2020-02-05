package com.online.edu.eduservice.service;

import com.online.edu.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.eduservice.entity.form.CourseInfoForm;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Brave
 * @since 2020-02-04
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程信息
     * @param courseInfoForm
     * @return
     */
    String insertCourseInfo(CourseInfoForm courseInfoForm);
}
