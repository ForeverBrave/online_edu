package com.online.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.eduservice.entity.EduCourse;
import com.online.edu.eduservice.entity.EduTeacher;
import com.online.edu.eduservice.entity.query.QueryTeacher;
import com.online.edu.eduservice.mapper.EduTeacherMapper;
import com.online.edu.eduservice.service.EduCourseService;
import com.online.edu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author Brave
 * @since 2020-01-31
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Autowired
    private EduCourseService eduCourseService;

    /**
     * 条件查询带分页
     * @param pageTeacher
     * @param queryTeacher
     */
    @Override
    public void pageListCondition(Page<EduTeacher> pageTeacher, QueryTeacher queryTeacher) {

        //关键： queryTeacher 有传递过来的条件值，判断，如果有条件值，则拼接条件
        if(queryTeacher == null){
            //直接查询分页，不进行条件操作
            this.baseMapper.selectPage(pageTeacher,null);
            return;
        }

        //如果queryTeacher不为空
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        wrapper.like(StringUtils.isNotEmpty(queryTeacher.getName()),"name",queryTeacher.getName());
        wrapper.like(StringUtils.isNotEmpty(queryTeacher.getLevel()),"level",queryTeacher.getLevel());
        wrapper.ge(StringUtils.isNotEmpty(queryTeacher.getBegin()),"gmt_create",queryTeacher.getBegin());
        wrapper.le(StringUtils.isNotEmpty(queryTeacher.getEnd()),"gmt_create",queryTeacher.getEnd());

        this.baseMapper.selectPage(pageTeacher,wrapper);

    }

    /**
     * 根据id删除讲师
     * @param id
     * @return
     */
    @Override
    public boolean deleteTeacherById(String id) {
        int result = this.baseMapper.deleteById(id);
        return result>0;
    }

    /**
     * 前台分页查询讲师的方法
     * @param pageTeacher
     * @return
     */
    @Override
    public Map<String, Object> getFrontTeacherList(Page<EduTeacher> pageTeacher) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        this.baseMapper.selectPage(pageTeacher,wrapper);

        //每页数据
        List<EduTeacher> records = pageTeacher.getRecords();
        //总记录数
        long total = pageTeacher.getTotal();
        //每页显示记录数
        long pages = pageTeacher.getPages();
        //总页数
        long size = pageTeacher.getSize();
        //当前页
        long current = pageTeacher.getCurrent();
        //是否有下一页
        boolean hasNext = pageTeacher.hasNext();
        //是否有上一页
        boolean hasPrevious = pageTeacher.hasPrevious();

        Map<String,Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    /**
     * 查询讲师所讲课程，返回list集合
     * @param id
     * @return
     */
    @Override
    public List<EduCourse> getCourseListByTeacherId(String id) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotEmpty(id),"teacher_id",id);
        wrapper.orderByDesc("gmt_modified");
        List<EduCourse> courseList = eduCourseService.list(wrapper);
        return courseList;
    }
}
