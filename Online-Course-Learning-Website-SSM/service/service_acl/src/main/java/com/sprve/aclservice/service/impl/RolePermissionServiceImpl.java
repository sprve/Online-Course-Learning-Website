package com.sprve.aclservice.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sprve.aclservice.entity.RolePermission;
import com.sprve.aclservice.mapper.RolePermissionMapper;
import com.sprve.aclservice.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {
    @Autowired
    RolePermission rolePermission;
    @Override
    public void initSave(String roleId){
    }
}
