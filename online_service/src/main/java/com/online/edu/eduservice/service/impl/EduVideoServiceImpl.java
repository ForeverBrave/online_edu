package com.online.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.edu.eduservice.client.VidClient;
import com.online.edu.eduservice.entity.EduVideo;
import com.online.edu.eduservice.mapper.EduVideoMapper;
import com.online.edu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private VidClient vidClient;

    /**
     * 根据课程id删除小节
     * @param id
     */
    @Override
    public void deleteVideoByCourseId(String id) {

        //把课程里所有视频都进行删除
        //1、获取课程里所有视频的id
        //根据课程id查询课程里所有小节的视频id
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",id);
        //只查询video_source_id字段
        wrapperVideo.select("video_source_id");
        List<EduVideo> eduVideos = this.baseMapper.selectList(wrapperVideo);

        //把查询出来集合里视频id取出来，并构建新的list集合
        List<String> videoIdList = new ArrayList<>();
        for (EduVideo eduVideo : eduVideos) {
            //获取每个小节
            EduVideo video = eduVideo;
            //获取每个小节里的视频id
            String videoSourceId = video.getVideoSourceId();
            //放到list集合中
            videoIdList.add(videoSourceId);
        }
        //2、调用方法进行删除
        vidClient.deleteMoreAliyunVideo(videoIdList);

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        this.baseMapper.delete(wrapper);
    }

    /**
     * 删除小节
     * @param sectionId
     * @return
     */
    @Override
    public boolean removeVideo(String sectionId) {

        //根据小节id查询数据库，获取视频id
        EduVideo eduVideo = this.baseMapper.selectById(sectionId);
        String videoSourceId = eduVideo.getVideoSourceId();

        //如果视频id存在，则删除
        if (StringUtils.isNotEmpty(videoSourceId)) {
            //调用方法，根据视频id删除
            vidClient.deleteAliyunVideoById(videoSourceId);
        }

        int result = this.baseMapper.deleteById(sectionId);
        return result>0;
    }
}
