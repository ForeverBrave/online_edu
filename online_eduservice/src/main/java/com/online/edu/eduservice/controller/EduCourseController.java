package com.online.edu.eduservice.controller;


import com.online.edu.common.R;
import com.online.edu.eduservice.entity.form.CourseInfoForm;
import com.online.edu.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Brave
 * @since 2020-02-04
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    /**
     * 添加课程信息
     * @param courseInfoForm
     * @return
     */
    @PostMapping
    public R addCourseInfo(@RequestBody CourseInfoForm courseInfoForm){
        String courseId = eduCourseService.insertCourseInfo(courseInfoForm);
        return R.ok().data("courseId",courseId);

    }
}

