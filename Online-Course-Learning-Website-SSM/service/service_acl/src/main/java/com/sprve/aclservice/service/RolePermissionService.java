package com.sprve.aclservice.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sprve.aclservice.entity.Role;
import com.sprve.aclservice.entity.RolePermission;
public interface RolePermissionService extends IService<RolePermission> {
    void initSave(String roleId);
}
