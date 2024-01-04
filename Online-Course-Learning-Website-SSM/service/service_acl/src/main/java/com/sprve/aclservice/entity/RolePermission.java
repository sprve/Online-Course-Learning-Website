package com.sprve.aclservice.entity;
import com.baomidou.mybatisplus.annotation.*;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;
@Data
@Component
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("acl_role_permission")
@ApiModel(value="RolePermission对象", description="角色权限")
public class RolePermission implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "角色id")
    private String roleId;
    @ApiModelProperty(value = "权限id")
    private String permissionId;
}
