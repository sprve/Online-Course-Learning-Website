package com.sprve.aclservice.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sprve.aclservice.entity.User;
public interface UserService extends IService<User> {
    User selectByUsername(String username);
    int deleteUserRole(String id);
}
