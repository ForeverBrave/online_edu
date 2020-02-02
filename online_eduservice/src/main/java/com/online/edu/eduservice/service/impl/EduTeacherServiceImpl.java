package com.online.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.eduservice.entity.EduTeacher;
import com.online.edu.eduservice.entity.query.QueryTeacher;
import com.online.edu.eduservice.mapper.EduTeacherMapper;
import com.online.edu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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
}
