package com.online.edu.eduservice.service;

import com.online.edu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author Brave
 * @since 2020-02-05
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     * 根据课程id删除小节
     * @param id
     */
    void deleteVideoByCourseId(String id);

    /**
     * 删除小节
     * @param videoId
     * @return
     */
    boolean removeVideo(String videoId);
}
