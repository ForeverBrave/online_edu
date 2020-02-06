package com.online.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.edu.eduservice.entity.EduVideo;
import com.online.edu.eduservice.mapper.EduVideoMapper;
import com.online.edu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author Brave
 * @since 2020-02-05
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    /**
     * 根据课程id删除小节
     * @param id
     */
    @Override
    public void deleteVideoByCourseId(String id) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        this.baseMapper.delete(wrapper);
    }

    /**
     * 删除小节
     * @param videoId
     * @return
     */
    @Override
    public boolean removeVideo(String videoId) {

        //TODO 删除小节时候，还要删除阿里云视频（预留）

        int result = this.baseMapper.deleteById(videoId);
        return result>0;
    }
}
