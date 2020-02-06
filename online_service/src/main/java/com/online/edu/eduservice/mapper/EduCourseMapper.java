package com.online.edu.eduservice.mapper;

import com.online.edu.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.online.edu.eduservice.entity.dto.CourseInfoDto;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Brave
 * @since 2020-02-04
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    /**
     * 根据课程id查询课程详细信息
     * @param courseId
     * @return
     */
    CourseInfoDto getAllCourseInfoById(String courseId);

}
