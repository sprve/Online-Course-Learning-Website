package com.sprve.eduservice.listener;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sprve.eduservice.entity.EduSubject;
import com.sprve.eduservice.entity.excel.SubjectData;
import com.sprve.eduservice.service.EduSubjectService;
import com.sprve.common.base.ExceptionHandler.SprveException;
import java.util.Map;
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    private EduSubjectService subjectService;
    public SubjectExcelListener() {}
    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null) {
            throw new SprveException(40000,"文件数据为空");
        }
        EduSubject existOneSubject = this.existOneSubject(subjectService,subjectData.getOneSujectName());
        if(existOneSubject == null) {
            existOneSubject = new EduSubject();
            existOneSubject.setTitle(subjectData.getOneSujectName());
            existOneSubject.setParentId("0");
            subjectService.save(existOneSubject);
        }
        String pid = existOneSubject.getId();
        EduSubject existTwoSubject = this.existTwoSubject(subjectService,subjectData.getTwoSujectName(), pid);
        if(existTwoSubject == null) {
            existTwoSubject = new EduSubject();
            existTwoSubject.setTitle(subjectData.getTwoSujectName());
            existTwoSubject.setParentId(pid);
            subjectService.save(existTwoSubject);
        }
    }
    private EduSubject existOneSubject(EduSubjectService subjectService,String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject OneSubject = subjectService.getOne(wrapper);
        return OneSubject;
    }
    private EduSubject existTwoSubject(EduSubjectService subjectService,String name,String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject TwoSubject = subjectService.getOne(wrapper);
        return TwoSubject;
    }
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+headMap);
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {}
}
