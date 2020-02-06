package com.online.edu.eduservice.controller;


import com.online.edu.common.R;
import com.online.edu.eduservice.entity.EduVideo;
import com.online.edu.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author Brave
 * @since 2020-02-05
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;


    /**
     * 添加小节
     * @param eduVideo
     * @return
     */
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        boolean save = eduVideoService.save(eduVideo);
        if (save) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * 根据id查询
     * @param videoId
     * @return
     */
    @GetMapping("{videoId}")
    public R getVideoById(@PathVariable String videoId){
        EduVideo eduVideo = eduVideoService.getById(videoId);
        return R.ok().data("eduVideo",eduVideo);
    }

    /**
     * 修改
     * @param eduVideo
     * @return
     */
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        boolean update = eduVideoService.updateById(eduVideo);
        if (update) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * 根据id删除
     * @param videoId
     * @return
     */
    @DeleteMapping("{videoId}")
    public R deleteVideoById(@PathVariable String videoId){
        boolean remove = eduVideoService.removeVideo(videoId);
        if (remove) {
            return R.ok();
        }else {
            return R.error();
        }
    }
}

