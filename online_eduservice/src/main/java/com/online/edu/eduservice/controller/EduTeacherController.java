package com.online.edu.eduservice.controller;

import com.online.edu.common.R;
import com.online.edu.eduservice.entity.EduTeacher;
import com.online.edu.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Brave
 * @since 2020-01-31
 */
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    //1、查询所有的讲师功能
    @GetMapping
    public R getAllTeacherList(){
        List<EduTeacher> teachers = eduTeacherService.list(null);
        return R.ok().data("items",teachers);
    }

    //2、逻辑删除讲师
    @DeleteMapping("{id}")
    public boolean deleteTeacherById(@PathVariable String id){
        boolean b = eduTeacherService.removeById(id);
        return b;
    }

}

