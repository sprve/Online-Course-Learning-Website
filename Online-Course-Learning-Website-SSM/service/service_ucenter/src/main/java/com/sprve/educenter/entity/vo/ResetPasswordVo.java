package com.sprve.educenter.entity.vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel(value = "重置密码封装类",description = "重置密码封装类")
@Data
public class ResetPasswordVo {
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "旧密码")
    private String oldpassword;
    @ApiModelProperty(value = "新密码")
    private String newpassword;
}