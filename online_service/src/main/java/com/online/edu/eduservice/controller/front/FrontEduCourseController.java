package com.online.edu.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.common.R;
import com.online.edu.eduservice.entity.EduCourse;
import com.online.edu.eduservice.entity.EduTeacher;
import com.online.edu.eduservice.entity.dto.CourseAllInfoDto;
import com.online.edu.eduservice.entity.dto.CourseInfoDto;
import com.online.edu.eduservice.entity.dto.EduChapterDto;
import com.online.edu.eduservice.service.EduChapterService;
import com.online.edu.eduservice.service.EduCourseService;
import com.online.edu.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author : Brave
 * @Version : 1.0
 * @Date : 2020/2/9 14:15
 *
 * 前台课程controller
 */
@RestController
@RequestMapping("/eduservice/frontCourse")
@CrossOrigin
public class FrontEduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduChapterService eduChapterService;


    /**
     * 前台查询所有课程带分页
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("{page}/{limit}")
    public R getCoursePageList(@PathVariable Long page,@PathVariable Long limit){
        //实现分页查询
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        //调用service中的方法实现分页
        Map<String,Object> map = eduCourseService.listCoursePage(pageCourse);
        return R.ok().data(map);
    }


    /**
     * 前台根据课程id查询课程详情
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public R getCourseInfoAll(@PathVariable String id){
        //1、根据课程id查询信息，包含课程基本信息、课程描述、讲师信息、分类信息
        CourseAllInfoDto courseAllInfo = eduCourseService.getCourseAllInfo(id);
        //2、根据课程id查询课程里面所有的章节，以及章节里面所有小节
        List<EduChapterDto> chapterVideoList = eduChapterService.getChapterVideoListByCourseId(id);
        return R.ok().data("courseInfo",courseAllInfo).data("chapterVideoList",chapterVideoList);
    }

}
