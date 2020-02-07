package com.online.edu.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.common.R;
import com.online.edu.eduservice.entity.EduCourse;
import com.online.edu.eduservice.entity.EduTeacher;
import com.online.edu.eduservice.entity.dto.CourseInfoDto;
import com.online.edu.eduservice.entity.form.CourseInfoForm;
import com.online.edu.eduservice.entity.query.QueryCourse;
import com.online.edu.eduservice.entity.query.QueryTeacher;
import com.online.edu.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 根据id查询课程信息
     * @param id
     * @return
     */
    @GetMapping("getCourseInfo/{id}")
    public R getCourseInfo(@PathVariable String id){
        CourseInfoForm courseInfoForm = eduCourseService.getCourseId(id);
        return R.ok().data("courseInfoForm",courseInfoForm);
    }

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

    /**
     * 修改课程
     * @return
     */
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoForm courseInfoForm){
        Boolean flag = eduCourseService.updateCourse(courseInfoForm);
        if (flag) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * 多条件组合查询带分页
     * (@RequestBody,主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的))
     * (使用@RequestBody时，必须使用@PostMapping提交，否则取不到值)
     * @param page
     * @param limit
     * @param queryCourse
     * @return
     */
    @PostMapping("moreCondtionPageList/{page}/{limit}")
    public R getMoreCondtionPageList(@PathVariable Long page, @PathVariable Long limit, @RequestBody(required = false) QueryCourse queryCourse){
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        //调用service的方法实现条件查询带分页
        eduCourseService.pageListCondition(pageCourse,queryCourse);

        //从pageTeacher对象里面获取分页数据
        long total = pageCourse.getTotal();
        List<EduCourse> records = pageCourse.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("items",records);

        return R.ok().data(map);
    }

    /**
     * 删除课程方法
     * @return
     */
    @DeleteMapping("deleteCourseById/{id}")
    public R deleteCourseById(@PathVariable String id){
        Boolean flag = eduCourseService.removeCourseById(id);
        if (flag) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * 根据课程id查询课程详细信息
     * @param courseId
     * @return
     */
    @GetMapping("getAllCourseInfo/{courseId}")
    public R getAllCourseInfo(@PathVariable String courseId){
        CourseInfoDto courseInfoDto = eduCourseService.getAllCourseInfo(courseId);
        return R.ok().data("courseInfo",courseInfoDto);
    }

    /**
     * 最终发布课程的方法，修改课程状态
     * @param courseId
     * @return
     */
    @GetMapping("publishCourse/{courseId}")
    public R publishCourse(@PathVariable String courseId){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        boolean update = eduCourseService.updateById(eduCourse);
        if (update) {
            return R.ok();
        }else {
            return R.error();
        }
    }

}

