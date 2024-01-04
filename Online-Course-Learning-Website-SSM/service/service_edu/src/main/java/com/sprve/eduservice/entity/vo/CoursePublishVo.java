package com.sprve.eduservice.entity.vo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
@ApiModel(value = "课程最终发布")
@Data
public class CoursePublishVo {
    private String id;
    private String title;
    private String cover;
    private String lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;
}
