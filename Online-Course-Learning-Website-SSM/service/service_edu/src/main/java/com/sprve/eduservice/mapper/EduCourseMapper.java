package com.sprve.eduservice.mapper;
import com.sprve.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sprve.eduservice.entity.frontVo.CourseWebVo;
import com.sprve.eduservice.entity.vo.CoursePublishVo;
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    public CoursePublishVo getPublicCourseInfo(String courseId);
    CourseWebVo getBaseCourseInfo(String courseId);
    Integer countBuy();
}