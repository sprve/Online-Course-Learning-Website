package com.sprve.aclservice.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sprve.aclservice.entity.UserRole;
import com.sprve.aclservice.mapper.UserRoleMapper;
import com.sprve.aclservice.service.UserRoleService;
import org.springframework.stereotype.Service;
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
