package com.sprve.eduservice.entity;
import com.baomidou.mybatisplus.annotation.*;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EduTeacher对象", description="讲师")
public class EduTeacher implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "讲师ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;
    @ApiModelProperty(value = "讲师姓名")
    private String name;
    @ApiModelProperty(value = "讲师简介")
    private String intro;
    @ApiModelProperty(value = "讲师资历,一句话说明讲师")
    private String career;
    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private Integer level;
    @ApiModelProperty(value = "讲师头像")
    private String avatar;
    @ApiModelProperty(value = "手机号")
    private String phonenumber;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "审核状态（0-待审核 1-审核通过 2-审核不通过）")
    private Integer auditStatus;
    @ApiModelProperty(value = "审核意见")
    private String auditOpinion;
    @ApiModelProperty(value = "创建时间", example = "2023-01-01 8:00:00")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    @ApiModelProperty(value = "更新时间", example = "2023-01-01 8:00:00")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
}