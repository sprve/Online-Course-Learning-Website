package com.sprve.aclservice.service;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sprve.aclservice.entity.Permission;
import java.util.List;
public interface PermissionService extends IService<Permission> {
    List<Permission> queryAllMenu();
    List<Permission> selectAllMenu(String roleId);
    void saveRolePermissionRealtionShip(String roleId, String[] permissionId);
    void removeChildById(String id);
    List<String> selectPermissionValueByUserId(String id);
    List<JSONObject> selectPermissionByUserId(String id);
}
