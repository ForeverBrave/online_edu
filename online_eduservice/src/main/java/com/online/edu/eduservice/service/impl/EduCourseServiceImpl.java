package com.online.edu.eduservice.service.impl;

import com.online.edu.eduservice.entity.EduCourse;
import com.online.edu.eduservice.entity.EduCourseDescription;
import com.online.edu.eduservice.entity.form.CourseInfoForm;
import com.online.edu.eduservice.handler.EduException;
import com.online.edu.eduservice.mapper.EduCourseMapper;
import com.online.edu.eduservice.service.EduCourseDescriptionService;
import com.online.edu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Brave
 * @since 2020-02-04
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    /**
     * 添加课程信息
     * @param courseInfoForm
     * @return
     */
    @Override
    public String insertCourseInfo(CourseInfoForm courseInfoForm) {
        //1、课程基本信息到课程表
        EduCourse eduCourse = new EduCourse();
        //courseInfoForm数据赋值到EduCourse对象里面，再进行添加
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        int result = this.baseMapper.insert(eduCourse);
        //判断如果添加课程信息成功
        //如果返回结果为0，则表示失败
        if (result==0) {
            throw new EduException(20001,"添加课程信息失败");
        }

        //2、课程描述添加到课程描述表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        //获取描述信息
        eduCourseDescription.setDescription(courseInfoForm.getDescription());
        //课程id
        eduCourseDescription.setId(eduCourse.getId());

        boolean save = this.eduCourseDescriptionService.save(eduCourseDescription);

        if (save) {
            //如果正确，返回courseId
            return eduCourse.getId();
        }else {
            return null;
        }
    }
}
