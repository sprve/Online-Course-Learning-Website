package com.sprve.eduservice.service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sprve.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sprve.eduservice.entity.frontVo.CourseFrontVo;
import com.sprve.eduservice.entity.frontVo.CourseWebVo;
import com.sprve.eduservice.entity.vo.CourseInfoVo;
import com.sprve.eduservice.entity.vo.CoursePublishVo;
import java.util.Map;
public interface EduCourseService extends IService<EduCourse> {
    String saveCourseInfo(CourseInfoVo courseInfoVo);
    CourseInfoVo getCourseInfo(String courseId);
    void updateCourseInfo(CourseInfoVo courseInfoVo);
    CoursePublishVo publishCourseInfo(String id);
    void removeCourse(String courseId);
    Map<String,Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);
    CourseWebVo getBaseCourseInfo(String courseId);
    void courseAction(String courseId,String action);
    Integer countBuy();
}
