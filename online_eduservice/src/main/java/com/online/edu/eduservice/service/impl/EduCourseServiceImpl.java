package com.online.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.eduservice.entity.EduCourse;
import com.online.edu.eduservice.entity.EduCourseDescription;
import com.online.edu.eduservice.entity.EduTeacher;
import com.online.edu.eduservice.entity.form.CourseInfoForm;
import com.online.edu.eduservice.entity.query.QueryCourse;
import com.online.edu.eduservice.handler.EduException;
import com.online.edu.eduservice.mapper.EduCourseMapper;
import com.online.edu.eduservice.service.EduChapterService;
import com.online.edu.eduservice.service.EduCourseDescriptionService;
import com.online.edu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.edu.eduservice.service.EduVideoService;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private EduVideoService eduVideoService;

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

    /**
     * 根据id查询课程信息
     * @param id
     * @return
     */
    @Override
    public CourseInfoForm getCourseId(String id) {
        //查询两张表
        //1、根据id查询课程基本信息表
        EduCourse eduCourse = this.baseMapper.selectById(id);
        if (eduCourse == null) {
            //没有课程信息
            throw new EduException(20001,"没有课程信息!");
        }

        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(eduCourse,courseInfoForm);

        //2、根据id查询课程描述表
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(id);
        String description = eduCourseDescription.getDescription();
        courseInfoForm.setDescription(description);

        return courseInfoForm;
    }

    /**
     * 修改课程
     * @param courseInfoForm
     * @return
     */
    @Override
    public Boolean updateCourse(CourseInfoForm courseInfoForm) {
        
        //1、修改课程基本信息表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        int result = this.baseMapper.updateById(eduCourse);
        if (result==0) {
            throw new EduException(20001,"修改分类失败!");
        }

        //2、修改课程描述表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoForm,eduCourseDescription);
        boolean b = eduCourseDescriptionService.updateById(eduCourseDescription);
        return b;
    }

    /**
     * 条件查询带分页
     * @param pageCourse
     * @param queryCourse
     */
    @Override
    public void pageListCondition(Page<EduCourse> pageCourse, QueryCourse queryCourse) {
        //关键： queryCourse 有传递过来的条件值，判断，如果有条件值，则拼接条件
        if(pageCourse == null){
            //直接查询分页，不进行条件操作
            this.baseMapper.selectPage(pageCourse,null);
            return;
        }

        //如果queryTeacher不为空
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        wrapper.like(StringUtils.isNotEmpty(queryCourse.getTitle()),"title",queryCourse.getTitle());
        wrapper.eq(StringUtils.isNotEmpty(queryCourse.getTeacherId()),"teacher_id",queryCourse.getTeacherId());
        wrapper.eq(StringUtils.isNotEmpty(queryCourse.getSubjectParentId()),"subject_parent_id",queryCourse.getSubjectParentId());
        wrapper.eq(StringUtils.isNotEmpty(queryCourse.getSubjectId()),"subject_id",queryCourse.getSubjectId());

        this.baseMapper.selectPage(pageCourse,wrapper);
    }

    /**
     * 删除课程方法
     * @param id
     * @return
     */
    @Override
    public Boolean removeCourseById(String id) {
        //1、根据课程id删除章节
        eduChapterService.deleteChapterByCourseId(id);
        //2、根据课程id删除小节
        eduVideoService.deleteVideoByCourseId(id);
        //3、根据课程id删除课程描述
        eduCourseDescriptionService.deleteDescriptionByCourseId(id);
        //4、删除课程本身
        int result = this.baseMapper.deleteById(id);
        return result>0;
    }
}
