package com.sprve.eduservice.entity.vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel(value = "课程分类", description = "课程分类封装")
@Data
public class SubjectVo {
    @ApiModelProperty(value = "课程分类ID")
    private String id;
    @ApiModelProperty(value = "课程分类数据")
    private String data;
}