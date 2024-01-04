package com.sprve.eduservice.service;
import com.sprve.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sprve.eduservice.entity.subject.OneSubject;
import com.sprve.eduservice.entity.vo.SubjectVo;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
public interface EduSubjectService extends IService<EduSubject> {
    void saveSubject(MultipartFile file,EduSubjectService subjectService);
    List<OneSubject> getAllOneTwoSubject();
    void addOneSubject(SubjectVo subjectVo);
    void deleteOneSubject(SubjectVo subjectVo);
}
