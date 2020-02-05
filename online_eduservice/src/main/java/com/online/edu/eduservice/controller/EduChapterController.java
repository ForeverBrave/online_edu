package com.online.edu.eduservice.controller;


import com.online.edu.common.R;
import com.online.edu.eduservice.entity.EduChapter;
import com.online.edu.eduservice.entity.dto.EduChapterDto;
import com.online.edu.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Brave
 * @since 2020-02-05
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    /**
     * 根据课程id查询章节和小节
     * @param courseId
     * @return
     */
    @GetMapping("getChapterVideoList/{courseId}")
    public R getChapterVideoListByCourseId(@PathVariable String courseId){
        List<EduChapterDto> list = eduChapterService.getChapterVideoListByCourseId(courseId);
        return R.ok().data("items",list);
    }

    /**
     * 添加章节方法
     * @param eduChapter
     * @return
     */
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        boolean result = eduChapterService.save(eduChapter);
        if (result) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * 根据id查询章节
     * @param chapterId
     * @return
     */
    @GetMapping("getChapterById/{chapterId}")
    public R getChapterById(String chapterId){
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return R.ok().data("eduChapter",eduChapter);
    }

    /**
     * 修改章节
     * @param eduChapter
     * @return
     */
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        boolean result = eduChapterService.updateById(eduChapter);
        if (result) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * 删除章节方法
     * @param chapterId
     * @return
     */
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        Boolean result = eduChapterService.removeChapterById(chapterId);
        if (result) {
            return R.ok();
        }else {
            return R.error();
        }
    }

}

