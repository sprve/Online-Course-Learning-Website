package com.sprve.aclservice.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sprve.aclservice.entity.User;
public interface UserMapper extends BaseMapper<User> {
    int deleteUserRole(String id);
}
