package com.sprve.aclservice.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.sprve.aclservice.entity.Role;
import com.sprve.aclservice.entity.User;
import com.sprve.aclservice.service.IndexService;
import com.sprve.aclservice.service.PermissionService;
import com.sprve.aclservice.service.RoleService;
import com.sprve.aclservice.service.UserService;
import com.sprve.common.base.ExceptionHandler.SprveException;
import com.sprve.common.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public Map<String, Object> getUserInfo(String username) {
        Map<String, Object> result = new HashMap<>();
        User user = userService.selectByUsername(username);
        if (null == user) {
            throw new SprveException(ResultCodeEnum.FETCH_USERINFO_ERROR);
        }
        List<Role> roleList = roleService.selectRoleByUserId(user.getUserId());
        List<String> roleNameList = roleList.stream().map(item -> item.getRoleName()).collect(Collectors.toList());
        if(roleNameList.size() == 0) {
            roleNameList.add("");
        }
        List<String> permissionValueList = permissionService.selectPermissionValueByUserId(user.getUserId());
        redisTemplate.opsForValue().set(username, permissionValueList);
        result.put("name", user.getUsername());
        result.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        result.put("roles", roleNameList);
        result.put("permissionValueList", permissionValueList);
        return result;
    }
    @Override
    public List<JSONObject> getMenu(String username) {
        User user = userService.selectByUsername(username);
        List<JSONObject> permissionList = permissionService.selectPermissionByUserId(user.getUserId());
        return permissionList;
    }
}
