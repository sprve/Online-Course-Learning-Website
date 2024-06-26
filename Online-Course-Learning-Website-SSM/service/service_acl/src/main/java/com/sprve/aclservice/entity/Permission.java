package com.sprve.aclservice.entity;
import com.baomidou.mybatisplus.annotation.*;
import java.util.Date;
import java.io.Serializable;
import java.util.List;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("acl_permission")
@ApiModel(value="Permission对象", description="权限")
public class Permission implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;
    @ApiModelProperty(value = "所属上级")
    private String pid;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "类型(1:菜单,2:按钮)")
    private Integer type;
    @ApiModelProperty(value = "权限值")
    private String permissionValue;
    @ApiModelProperty(value = "访问路径")
    private String path;
    @ApiModelProperty(value = "组件路径")
    private String component;
    @ApiModelProperty(value = "层级")
    @TableField(exist = false)
    private Integer level;
    @ApiModelProperty(value = "下级")
    @TableField(exist = false)
    private List<Permission> children;
    @ApiModelProperty(value = "是否选中")
    @TableField(exist = false)
    private boolean isSelect;
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
}
