package com.online.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.edu.eduservice.entity.EduChapter;
import com.online.edu.eduservice.entity.EduVideo;
import com.online.edu.eduservice.entity.dto.EduChapterDto;
import com.online.edu.eduservice.entity.dto.EduVideoDto;
import com.online.edu.eduservice.handler.EduException;
import com.online.edu.eduservice.mapper.EduChapterMapper;
import com.online.edu.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.edu.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Brave
 * @since 2020-02-05
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;


    /**
     * 根据课程id删除章节
     * @param id
     */
    @Override
    public void deleteChapterByCourseId(String id) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        this.baseMapper.delete(wrapper);
    }

    /**
     * 根据课程id查询章节和小节
     * @param courseId
     * @return
     */
    @Override
    public List<EduChapterDto> getChapterVideoListByCourseId(String courseId) {
        //1、根据课程id查询章节
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        List<EduChapter> eduChapters = this.baseMapper.selectList(wrapper);

        //2、根据课程id查询小节
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        List<EduVideo> eduVideos = eduVideoService.list(queryWrapper);

        //初始化一个集合，用于存储章节和小节的数据
        List<EduChapterDto> chapterDtoList = new ArrayList<>();
        //3、遍历所有的章节，赋值到dto对象中
        for (EduChapter eduChapter : eduChapters) {
            //得到每个章节
            EduChapter chapter = eduChapter;
            //赋值到dto中
            EduChapterDto eduChapterDto = new EduChapterDto();
            BeanUtils.copyProperties(chapter,eduChapterDto);
            //dto对象放到list集合中
            chapterDtoList.add(eduChapterDto);

            //初始化一个结婚，用于存储所有小节的数据
            List<EduVideoDto> videoDtoList = new ArrayList<>();
            //构建小节部分(遍历)
            for (EduVideo eduVideo : eduVideos) {
                EduVideo video = eduVideo;
                //判断小节chapterid和章节id是否一致
                if(video.getChapterId().equals(chapter.getId())){
                    //如果相同  将对象赋值到dto中
                    EduVideoDto eduVideoDto = new EduVideoDto();
                    BeanUtils.copyProperties(video,eduVideoDto);
                    videoDtoList.add(eduVideoDto);
                }
            }
            //把小节最终放到对应的每个章节里
            eduChapterDto.setChildren(videoDtoList);
        }

        return chapterDtoList;
    }

    /**
     * 删除章节方法(如果下面有小节，必须要先删除下面小节，否则不能删章节)
     * @param chapterId
     * @return
     */
    @Override
    public Boolean removeChapterById(String chapterId) {

        //判断章节里面是否有小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = eduVideoService.count(wrapper);
        //如果有小节不进行删除，
        if (count>0) {
            throw new EduException(20001,"删除失败！必须先删除小节，再删除章节");
        }
        //没有则进行章节删除
        int result = this.baseMapper.deleteById(chapterId);
        return result>0;
    }
}
