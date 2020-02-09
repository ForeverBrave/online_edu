package com.online.edu.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.eduservice.entity.EduCourse;
import com.online.edu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.eduservice.entity.query.QueryTeacher;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author Brave
 * @since 2020-01-31
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 条件查询带分页
     * @param pageTeacher
     * @param queryTeacher
     */
    void pageListCondition(Page<EduTeacher> pageTeacher, QueryTeacher queryTeacher);

    /**
     * 逻辑删除讲师
     * @param id
     * @return
     */
    boolean deleteTeacherById(String id);

    /**
     * 前台分页查询讲师的方法
     * @param pageTeacher
     * @return
     */
    Map<String, Object> getFrontTeacherList(Page<EduTeacher> pageTeacher);

    /**
     * 查询讲师所讲课程，返回list集合
     * @param id
     * @return
     */
    List<EduCourse> getCourseListByTeacherId(String id);
}
