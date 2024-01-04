package com.sprve.aclservice.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sprve.aclservice.entity.Role;
public interface RoleMapper extends BaseMapper<Role> {
    int deleteRolePermission(String id);
    int checkRole(String roleId);
}
