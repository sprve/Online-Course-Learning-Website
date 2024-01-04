package com.sprve.aclservice.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sprve.aclservice.entity.Permission;
import com.sprve.aclservice.entity.RolePermission;
import com.sprve.aclservice.entity.User;
import com.sprve.aclservice.helper.MenuHelper;
import com.sprve.aclservice.helper.PermissionHelper;
import com.sprve.aclservice.mapper.PermissionMapper;
import com.sprve.aclservice.service.PermissionService;
import com.sprve.aclservice.service.RolePermissionService;
import com.sprve.aclservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private UserService userService;
    @Override
    public List<Permission> queryAllMenu() {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permission> permissionList = baseMapper.selectList(wrapper);
        List<Permission> result = bulidPermission(permissionList);
        return result;
    }
    @Override
    public List<Permission> selectAllMenu(String roleId) {
        List<Permission> allPermissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));
        List<RolePermission> rolePermissionList = rolePermissionService.list(new QueryWrapper<RolePermission>().eq("role_id",roleId));
        for (int i = 0; i < allPermissionList.size(); i++) {
            Permission permission = allPermissionList.get(i);
            for (int m = 0; m < rolePermissionList.size(); m++) {
                RolePermission rolePermission = rolePermissionList.get(m);
                if(rolePermission.getPermissionId().equals(permission.getId())) {
                    permission.setSelect(true);
                }
            }
        }
        List<Permission> permissionList = bulid(allPermissionList);
        return permissionList;
    }
    @Override
    public void saveRolePermissionRealtionShip(String roleId, String[] permissionIds) {
        rolePermissionService.remove(new QueryWrapper<RolePermission>().eq("role_id", roleId));
        List<RolePermission> rolePermissionList = new ArrayList<>();
        for(String permissionId : permissionIds) {
            if(StringUtils.isEmpty(permissionId)) continue;
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissionList.add(rolePermission);
        }
        rolePermissionService.saveBatch(rolePermissionList);
    }
    @Override
    public void removeChildById(String id) {
        List<String> idList = new ArrayList<>();
        this.selectPermissionChildById(id, idList);
        idList.add(id);
        baseMapper.deleteBatchIds(idList);
    }
    @Override
    public List<String> selectPermissionValueByUserId(String id) {
        List<String> selectPermissionValueList = null;
        if(this.isSysAdmin(id)) {
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
        }
        return selectPermissionValueList;
    }
    @Override
    public List<JSONObject> selectPermissionByUserId(String userId) {
        List<Permission> selectPermissionList = null;
        if(this.isSysAdmin(userId)) {
            selectPermissionList = baseMapper.selectList(null);
        } else {
            selectPermissionList = baseMapper.selectPermissionByUserId(userId);
        }
        List<Permission> permissionList = PermissionHelper.bulid(selectPermissionList);
        List<JSONObject> result = MenuHelper.bulid(permissionList);
        return result;
    }
    private boolean isSysAdmin(String userId) {
        User user = userService.getById(userId);
        if(null != user && "admin".equals(user.getUsername())) {
            return true;
        }
        return false;
    }
    private void selectChildListById(String id, List<String> idList) {
        List<Permission> childList = baseMapper.selectList(new QueryWrapper<Permission>().eq("pid", id).select("id"));
        childList.stream().forEach(item -> {
            idList.add(item.getId());
            this.selectChildListById(item.getId(), idList);
        });
    }
    private static List<Permission> bulid(List<Permission> treeNodes) {
        List<Permission> trees = new ArrayList<>();
        for (Permission treeNode : treeNodes) {
            if ("0".equals(treeNode.getPid())) {
                treeNode.setLevel(1);
                trees.add(findChildren(treeNode,treeNodes));
            }
        }
        return trees;
    }
    private static Permission findChildren(Permission treeNode,List<Permission> treeNodes) {
        treeNode.setChildren(new ArrayList<Permission>());
        for (Permission it : treeNodes) {
            if(treeNode.getId().equals(it.getPid())) {
                int level = treeNode.getLevel() + 1;
                it.setLevel(level);
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return treeNode;
    }
    public static List<Permission> bulidPermission(List<Permission> permissionList) {
        List<Permission> finalNode = new ArrayList<>();
        for(Permission permissionNode : permissionList) {
            if("0".equals(permissionNode.getPid())) {
                permissionNode.setLevel(1);
                finalNode.add(selectChildren(permissionNode,permissionList));
            }
        }
        return finalNode;
    }
    private static Permission selectChildren(Permission permissionNode, List<Permission> permissionList) {
        permissionNode.setChildren(new ArrayList<Permission>());
        for(Permission it : permissionList) {
            if(permissionNode.getId().equals(it.getPid())) {
                int level = permissionNode.getLevel()+1;
                it.setLevel(level);
                if(permissionNode.getChildren() == null) {
                    permissionNode.setChildren(new ArrayList<Permission>());
                }
                permissionNode.getChildren().add(selectChildren(it,permissionList));
            }
        }
        return permissionNode;
    }
    private void selectPermissionChildById(String id, List<String> idList) {
        QueryWrapper<Permission>  wrapper = new QueryWrapper<>();
        wrapper.eq("pid",id);
        wrapper.select("id");
        List<Permission> childIdList = baseMapper.selectList(wrapper);
        childIdList.stream().forEach(item -> {
            idList.add(item.getId());
            this.selectPermissionChildById(item.getId(),idList);
        });
    }
}
