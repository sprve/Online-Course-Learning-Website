package com.sprve.aclservice.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sprve.aclservice.entity.Role;
import java.util.List;
import java.util.Map;
public interface RoleService extends IService<Role> {
    Map<String, Object> findRoleByUserId(String userId);
    void saveUserRoleRealtionShip(String userId, String[] roleId);
    List<Role> selectRoleByUserId(String id);
    int deleteRolePermission(String id);
    boolean checkRole(String roleId);
}
