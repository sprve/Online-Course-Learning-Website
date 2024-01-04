package com.sprve.eduservice.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sprve.common.utils.R;
import com.sprve.eduservice.entity.EduCourse;
import com.sprve.eduservice.entity.vo.CourseInfoVo;
import com.sprve.eduservice.entity.vo.CoursePublishVo;
import com.sprve.eduservice.entity.vo.CourseQuery;
import com.sprve.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Api(description = "课程管理")
@RestController
@RequestMapping("/eduservice/course")
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;
    @ApiOperation(value = "课程列表")
    @GetMapping("getCourseList")
    public R getCourseList(){
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("list",list);
    }
    @ApiOperation(value = "分页查询课程列表")
    @PostMapping("pageListCourse/{current}/{limit}")
    public R pageListCourse(
            @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable Long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit){
        Page<EduCourse> pageCourse =new Page<>(current,limit);
        courseService.page(pageCourse,null);
        long total = pageCourse.getTotal();
        List<EduCourse> courseList = pageCourse.getRecords();
        return R.ok().data("total",total).data("rows",courseList);
    }
    @ApiOperation(value = "条件查询带分页课程列表")
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(
            @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable Long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @RequestBody(required = false) CourseQuery courseQuery){
        Page<EduCourse> pageCourse =new Page<>(current,limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title",title);
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("status",status);
        }
        courseService.page(pageCourse,wrapper);
        long total = pageCourse.getTotal();
        List<EduCourse> courseList = pageCourse.getRecords();
        return R.ok().data("total",total).data("rows",courseList);
    }
    @ApiOperation(value = "删除课程")
    @PostMapping("deleteCourse/{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        courseService.removeCourse(courseId);
        return R.ok();
    }
    @ApiOperation(value = "添加课程基本信息")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }
    @ApiOperation(value = "根据课程查询课程基本信息")
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }
    @ApiOperation(value = "根据id修改课程信息")
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }
    @ApiOperation(value = "根据课程id查询课程确认信息")
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }
    @ApiOperation(value = "课程最终发布修改课程状态")
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.ok();
    }
    @ApiOperation(value = "根据课程id增加统计次数")
    @PostMapping("{courseId}/{action}")
    public void setCourseAction(@PathVariable("courseId") String courseId,
                                @PathVariable("action") String action){
        courseService.courseAction(courseId,action);
    }
    @ApiOperation(value = "统计购买次数")
    @GetMapping("countBuy")
    public R getCountBuy(){
        Integer count = courseService.countBuy();
        return R.ok().data("countBuy",count);
    }
}
