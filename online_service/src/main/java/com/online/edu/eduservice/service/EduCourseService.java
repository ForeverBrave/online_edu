package com.online.edu.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.eduservice.entity.dto.CourseAllInfoDto;
import com.online.edu.eduservice.entity.dto.CourseInfoDto;
import com.online.edu.eduservice.entity.form.CourseInfoForm;
import com.online.edu.eduservice.entity.query.QueryCourse;

import java.util.Map;

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

    /**
     * 根据id查询课程信息
     * @param id
     * @return
     */
    CourseInfoForm getCourseId(String id);

    /**
     * 修改课程
     * @param courseInfoForm
     * @return
     */
    Boolean updateCourse(CourseInfoForm courseInfoForm);

    /**
     * 条件查询带分页
     * @param pageCourse
     * @param queryCourse
     */
    void pageListCondition(Page<EduCourse> pageCourse, QueryCourse queryCourse);

    /**
     * 删除课程方法
     * @param id
     * @return
     */
    Boolean removeCourseById(String id);

    /**
     * 根据课程id查询课程详细信息
     * @param courseId
     * @return
     */
    CourseInfoDto getAllCourseInfo(String courseId);

    /**
     * 前台查询所有课程带分页
     * @param pageCourse
     * @return
     */
    Map<String, Object> listCoursePage(Page<EduCourse> pageCourse);

    /**
     * 前台根据课程id查询课程详情
     * @param id
     * @return
     */
    CourseAllInfoDto getCourseAllInfo(String id);
}
