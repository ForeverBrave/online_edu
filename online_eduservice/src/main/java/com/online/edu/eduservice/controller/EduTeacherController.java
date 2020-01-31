package com.online.edu.eduservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.common.R;
import com.online.edu.eduservice.entity.EduTeacher;
import com.online.edu.eduservice.entity.query.QueryTeacher;
import com.online.edu.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 1、查询所有的讲师功能
     * @return
     */
    @GetMapping
    public R getAllTeacherList(){
        List<EduTeacher> teachers = eduTeacherService.list(null);
        return R.ok().data("items",teachers);
    }

    /**
     * 2、逻辑删除讲师
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public boolean deleteTeacherById(@PathVariable String id){
        boolean b = eduTeacherService.removeById(id);
        return b;
    }

    /**
     * 3、分页查询讲师列表的方法
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("pageList/{page}/{limit}")
    public R getPageTeacherList(@PathVariable Long page,@PathVariable Long limit){

        //创建page对象，传递两个参数   (当前页数，每页记录数)
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);

        //调用方法分页查询
        eduTeacherService.page(pageTeacher,null);

        //从pageTeacher对象里面获取分页数据
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("items",records);

        return R.ok().data(map);
    }

    /**
     * 4、多条件组合查询带分页
     * (@RequestBody,主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的))
     * (使用@RequestBody时，必须使用@PostMapping提交，否则取不到值)
     * @param page
     * @param limit
     * @param queryTeacher
     * @return
     */
    @PostMapping("moreCondtionPageList/{page}/{limit}")
    public R getMoreCondtionPageList(@PathVariable Long page, @PathVariable Long limit, @RequestBody(required = false)QueryTeacher queryTeacher){
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
        //调用service的方法实现条件查询带分页
        eduTeacherService.pageListCondition(pageTeacher,queryTeacher);

        //从pageTeacher对象里面获取分页数据
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("items",records);

        return R.ok().data(map);
    }

    /**
     * 5、添加讲师
     * @param eduTeacher
     * @return
     */
    @PostMapping("saveTeacher")
    public R saveTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * 6、根据id查询讲师
     * @param id
     * @return
     */
    @GetMapping("getTeacherInfo/{id}")
    public R getTeacherInfo(@PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("eduTeacher",eduTeacher);
    }

    /**
     * 7、根据id修改的
     * @param eduTeacher
     * @return
     */
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean b = eduTeacherService.updateById(eduTeacher);
        if(b){
            return R.ok();
        }else {
            return R.error();
        }
    }


}

