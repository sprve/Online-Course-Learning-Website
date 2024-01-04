package com.sprve.aclservice.service.impl;
import com.sprve.aclservice.entity.User;
import com.sprve.aclservice.service.PermissionService;
import com.sprve.aclservice.service.UserService;
import com.sprve.serurity.entity.SecurityUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.selectByUsername(username);
        if (null == user){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        com.sprve.serurity.entity.User curUser = new com.sprve.serurity.entity.User();
        BeanUtils.copyProperties(user,curUser);
        List<String> authorities = permissionService.selectPermissionValueByUserId(user.getUserId());
        SecurityUser securityUser = new SecurityUser(curUser);
        securityUser.setPermissionValueList(authorities);
        return securityUser;
    }
}
