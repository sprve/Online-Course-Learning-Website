package com.sprve.eduservice.controller.front;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sprve.common.utils.R;
import com.sprve.eduservice.entity.EduCourse;
import com.sprve.eduservice.entity.EduTeacher;
import com.sprve.eduservice.service.EduCourseService;
import com.sprve.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@Api(description = "前台查询课程名师")
@RestController
@RequestMapping("/eduservice/indexfront")
public class IndexFrontController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;
    @ApiOperation(value = "查询前8条热门课程，查询前4条讲师")
    @GetMapping("index")
    public R index() {
        QueryWrapper<EduCourse> wrapperCourse = new QueryWrapper<>();
        wrapperCourse.orderByDesc("id");
        wrapperCourse.last("limit 8");
        wrapperCourse.eq("status","Normal");
        List<EduCourse> courseList = courseService.list(wrapperCourse);
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        wrapperTeacher.eq("audit_status",1);
        List<EduTeacher> teacherList = teacherService.list(wrapperTeacher);
        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }
}
