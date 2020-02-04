package com.online.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.edu.eduservice.entity.EduSubject;
import com.online.edu.eduservice.entity.dto.OneSubjectDto;
import com.online.edu.eduservice.entity.dto.TwoSubjectDto;
import com.online.edu.eduservice.handler.EduException;
import com.online.edu.eduservice.mapper.EduSubjectMapper;
import com.online.edu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Brave
 * @since 2020-02-04
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 读取excel内容，添加分类表中
     * @param file
     */
    @Override
    public List<String> importSubject(MultipartFile file) {
        try {
            //1、获取文件输入流
            InputStream in = file.getInputStream();
            //2、创建workbook
            Workbook workbook = new HSSFWorkbook(in);
            //3、worobook获取sheet
            Sheet sheet = workbook.getSheetAt(0);

            //为了存储错误信息
            List<String> msg = new ArrayList<>();

            //4、sheet获取row
            int lastRowNum = sheet.getLastRowNum();
            //（循环遍历获取行，从第二行开始取数据，第一行为表头）
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                //如果行为空，提示错误信息
                if(row == null){
                    String str = "表格数据为空，请输入数据!";
                    msg.add(str);
                    continue;
                }
 /*===================================一级分类开始===========================================*/
                //如果行不为空
                //5.1、row获取第一列
                Cell cellOne = row.getCell(0);
                //如果列为空，提示错误信息
                if (cellOne == null){
                    String str = "第"+i+"行数据为空，请输入数据";
                    msg.add(str);
                    //跳出这一行，往下继续执行
                    continue;
                }

                //如果列不为空,获取一级分类的数据
                String cellOneValue = cellOne.getStringCellValue();
                //添加一级分类
                //因为excel里，有许多重复的一级分类（要去重）
                //在添加一级分类之前判断：要添加的一级分类在数据库中是否存在
                EduSubject existOneSubject = this.existOneSubject(cellOneValue);

                //存储一级分类id
                String parent_id = null;

                //如果不存在，则添加
                if(existOneSubject == null){
                    EduSubject eduSubject = new EduSubject();
                    eduSubject.setTitle(cellOneValue);
                    //一级分类，parentId为0
                    eduSubject.setParentId("0");
                    eduSubject.setSort(0);
                    this.baseMapper.insert(eduSubject);
                    //把新添加的一级分类id，获取并进行赋值给parent_id
                    parent_id = eduSubject.getId();
                }else {
                    //存在，不添加
                    //把一级分类id赋值给parent_id
                    parent_id = existOneSubject.getId();
                }
/*===================================一级分类结束===========================================*/


/*===================================二级分类开始===========================================*/
                //5.2、row获取第二列
                Cell cellTwo = row.getCell(1);
                //如果列为空，提示错误信息
                if (cellTwo == null){
                    String str = "第"+i+"行数据为空，请输入数据";
                    msg.add(str);
                    //跳出这一行，往下继续执行
                    continue;
                }
                //如果列不为空,获取二级分类的数据
                String cellTwoValue = cellTwo.getStringCellValue();
                //添加二级分类
                //判断数据库表里是否已经存储二级分类
                EduSubject existTwoSubject = this.existTwoSubject(cellTwoValue, parent_id);
                //如果不存在，则添加
                if(existTwoSubject == null){
                    EduSubject eduSubject = new EduSubject();
                    eduSubject.setTitle(cellTwoValue);
                    eduSubject.setParentId(parent_id);
                    eduSubject.setSort(0);
                    this.baseMapper.insert(eduSubject);
                }
            }
            //8、关闭流
            in.close();

            return msg;
        }catch (Exception e){
            e.printStackTrace();
            throw new EduException(20001,"导入失败，出现异常！");
        }

    }

    /**
     * 返回所有分类数据，返回要求的json数据格式
     * @return
     */
    @Override
    public List<OneSubjectDto> getSubjectList() {
        //1、查询所有一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> allOneSubjects = this.baseMapper.selectList(wrapperOne);

        //2、查询所有二级分类
        QueryWrapper<EduSubject> SubjectTwo = new QueryWrapper<>();
        //ne：  parent_id 不等于 0
        SubjectTwo.ne("parent_id","0");
        List<EduSubject> allTwoSubjects = baseMapper.selectList(SubjectTwo);

        //创建list集合，存储所有一级分类
        List<OneSubjectDto> oneSubjectDtoList = new ArrayList<>();
        //3、首先构建一级分类
        //遍历所有的一级分类，得到每个EduSubject对象，把每个EduSubject对象转换OneSubjectDto
        for (int i = 0; i < allOneSubjects.size(); i++) {
            //获取每个EduSubject对象
            EduSubject eduOneSubject = allOneSubjects.get(i);
            //创建OneSubjectDto对象
            OneSubjectDto oneSubjectDto = new OneSubjectDto();
            //把每个EduSubject对象转换OneSubjectDto
            BeanUtils.copyProperties(eduOneSubject,oneSubjectDto);
            //把dto对象放到list集合
            oneSubjectDtoList.add(oneSubjectDto);

            //获取一级分类所有二级分类，List<TwoSubjectDto>
            //把所有的二级分类添加到每个一级分类对象中oneSubjectDto.setChildren(list);
            //创建list集合，用于存储二级分类
            List<TwoSubjectDto> twoSubjectDtoList = new ArrayList<>();
            //遍历所有的二级分类，得到每个二级分类
            for (int n = 0; n < allTwoSubjects.size(); n++) {
                //得到每个二级分类
                EduSubject eduTwoSubject = allTwoSubjects.get(n);
                //判断一级分类id和二级分类parentid是否一样
                if(eduTwoSubject.getParentId().equals(eduOneSubject.getId())){
                    //创建TwoSubjectDto对象
                    TwoSubjectDto twoSubjectDto = new TwoSubjectDto();
                    //二级分类转换TwoSubjectDto
                    //内省  反射
                    BeanUtils.copyProperties(eduTwoSubject,twoSubjectDto);
                    //放到list集合
                    twoSubjectDtoList.add(twoSubjectDto);
                }
            }
            //把二级分类放到每一个一级分类中
            oneSubjectDto.setChildren(twoSubjectDtoList);
        }
        return oneSubjectDtoList;
    }

    /**
     * 判断数据库表里是否存在一级分类
     * @param name
     * @return
     */
    private EduSubject existOneSubject(String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject eduSubject = this.baseMapper.selectOne(wrapper);
        return eduSubject;
    }

    /**
     * 判断数据库表里是否存在二级分类
     * @param name
     * @return
     */
    private EduSubject existTwoSubject(String name,String parentid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",parentid);
        EduSubject eduSubject = this.baseMapper.selectOne(wrapper);
        return eduSubject;
    }
}
