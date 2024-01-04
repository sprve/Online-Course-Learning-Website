package com.sprve.eduservice.service.impl;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sprve.eduservice.entity.EduSubject;
import com.sprve.eduservice.entity.excel.SubjectData;
import com.sprve.eduservice.entity.subject.OneSubject;
import com.sprve.eduservice.entity.subject.TwoSubject;
import com.sprve.eduservice.entity.vo.SubjectVo;
import com.sprve.eduservice.listener.SubjectExcelListener;
import com.sprve.eduservice.mapper.EduSubjectMapper;
import com.sprve.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in,SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id",0);
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id",0);
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);
        List<OneSubject> finalSubjectList = new ArrayList<>();
        for (int i = 0; i < oneSubjectList.size(); i++) {
            EduSubject eduSubject = oneSubjectList.get(i);
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduSubject,oneSubject);
            finalSubjectList.add(oneSubject);
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            for (int m = 0; m < twoSubjectList.size(); m++) {
                EduSubject tSubject = twoSubjectList.get(m);
                if(tSubject.getParentId().equals(eduSubject.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoFinalSubjectList);
        }
        return finalSubjectList;
    }
    @Override
    public  void addOneSubject(SubjectVo subjectVo){
        EduSubject eduSubject=new EduSubject();
        if(subjectVo.getId()==null || subjectVo.getId().length()==0){
            eduSubject.setTitle(subjectVo.getData());
            eduSubject.setParentId("0");
            baseMapper.insert(eduSubject);
        }
        else{
            eduSubject.setTitle(subjectVo.getData());
            eduSubject.setParentId(subjectVo.getId());
            baseMapper.insert(eduSubject);
        }
    }
    @Override
    public  void deleteOneSubject(SubjectVo subjectVo){
        QueryWrapper<EduSubject> eduSubject =new QueryWrapper<>();
        if(subjectVo.getData()==null || subjectVo.getData().length()==0){
            baseMapper.deleteById(subjectVo.getId());}
        else{
            eduSubject.eq("parent_id",subjectVo.getId());
            baseMapper.delete(eduSubject);
            baseMapper.deleteById(subjectVo.getId());
        }
    }
}
