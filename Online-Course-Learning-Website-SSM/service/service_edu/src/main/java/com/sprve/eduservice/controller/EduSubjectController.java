package com.sprve.eduservice.controller;
import com.sprve.common.utils.R;
import com.sprve.eduservice.entity.subject.OneSubject;
import com.sprve.eduservice.entity.vo.SubjectVo;
import com.sprve.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.security.auth.Subject;
import java.util.List;
@Api(description = "课程分类")
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;
    @ApiOperation(value = "添加课程分类")
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }
    @ApiOperation(value = "添加一个课程分类")
    @PostMapping("addOneSubject")
    public R addOneSubject(@RequestBody SubjectVo subjectVo) {
        subjectService.addOneSubject(subjectVo);
        return R.ok();
    }
    @ApiOperation(value = "删除一个课程分类")
    @PostMapping("deleteOneSubject")
    public R deleteOneSubject(@RequestBody SubjectVo subjectVo) {
        subjectService.deleteOneSubject(subjectVo);
        return R.ok();
    }
    @ApiOperation(value = "课程分类列表")
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }
}