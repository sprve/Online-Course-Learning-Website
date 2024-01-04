package com.sprve.eduservice.entity.vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel(value = "Course查询对象", description = "课程查询对象封装")
@Data
public class CourseQuery {
    @ApiModelProperty(value = "课程标题")
    private String title;
    @ApiModelProperty(value = "课程状态 Draft未发布  Normal已发布")
    private String status;
}