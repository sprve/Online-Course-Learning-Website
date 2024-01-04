package com.sprve.eduservice.entity.vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel(value = "评论查询对象", description = "评论查询对象封装")
@Data
public class CommentQuery {
    @ApiModelProperty(value = "课程ID")
    private String courseId;
    @ApiModelProperty(value = "昵称")
    private String nickName;
}