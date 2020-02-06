package com.online.edu.eduservice.service;

import com.online.edu.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.eduservice.entity.dto.OneSubjectDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Brave
 * @since 2020-02-04
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 读取excel内容
     * @param file
     */
    List<String> importSubject(MultipartFile file);

    /**
     * 返回所有分类数据，返回要求的json数据格式
     * @return
     */
    List<OneSubjectDto> getSubjectList();

    /**
     * 删除分类
     * @param id
     * @return
     */
    boolean deleteSubjectById(String id);

    /**
     * 添加一级分类
     * @param eduSubject
     * @return
     */
    boolean saveOneLevel(EduSubject eduSubject);

    /**
     * 添加二级分类
     * @param eduSubject
     * @return
     */
    boolean saveTwoLevel(EduSubject eduSubject);
}
