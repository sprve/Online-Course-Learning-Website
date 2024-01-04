package com.sprve.aclservice.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sprve.aclservice.entity.User;
import com.sprve.aclservice.mapper.UserMapper;
import com.sprve.aclservice.service.UserService;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public User selectByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }
    @Override
    public int deleteUserRole(String id) {
        return baseMapper.deleteUserRole(id);
    }
}
