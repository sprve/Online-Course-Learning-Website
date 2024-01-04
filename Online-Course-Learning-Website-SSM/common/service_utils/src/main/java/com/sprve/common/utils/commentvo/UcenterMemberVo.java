package com.sprve.common.utils.commentvo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel(value = "评论封装类",description = "评论封装类")
@Data
public class UcenterMemberVo {
    @ApiModelProperty(value = "用户id")
    private String id;
    @ApiModelProperty(value = "昵称")
    private String nickname;
    @ApiModelProperty(value = "用户头像")
    private String avatar;
}
