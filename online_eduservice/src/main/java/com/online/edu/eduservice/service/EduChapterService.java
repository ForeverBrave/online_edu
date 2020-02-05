package com.online.edu.eduservice.service;

import com.online.edu.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.eduservice.entity.dto.EduChapterDto;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Brave
 * @since 2020-02-05
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 根据课程id删除章节
     * @param id
     */
    void deleteChapterByCourseId(String id);

    /**
     * 根据课程id查询章节和小节
     * @param courseId
     * @return
     */
    List<EduChapterDto> getChapterVideoListByCourseId(String courseId);

    /**
     * chapterId
     * @param chapterId
     * @return
     */
    Boolean removeChapterById(String chapterId);
}
