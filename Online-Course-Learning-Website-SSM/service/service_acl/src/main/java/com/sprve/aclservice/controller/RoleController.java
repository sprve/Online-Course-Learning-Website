package com.sprve.aclservice.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sprve.aclservice.entity.Role;
import com.sprve.aclservice.service.RolePermissionService;
import com.sprve.aclservice.service.RoleService;
import com.sprve.common.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Api(description = "权限管理")
@RestController
@RequestMapping("/admin/acl/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @ApiOperation(value = "获取角色分页列表")
    @GetMapping("/{page}/{limit}")
    public R index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            Role role) {
        Page<Role> pageParam = new Page<>(page, limit);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(role.getRoleName())) {
            wrapper.like("role_name",role.getRoleName());
        }
        roleService.page(pageParam,wrapper);
        return R.ok().data("items", pageParam.getRecords()).data("total", pageParam.getTotal());
    }
    @ApiOperation(value = "获取角色")
    @GetMapping("/get/{id}")
    public R get(@PathVariable String id) {
        Role role = roleService.getById(id);
        return R.ok().data("item", role);
    }
    @ApiOperation(value = "新增角色")
    @PostMapping("/save")
    public R save(@RequestBody Role role) {
        roleService.save(role);
        rolePermissionService.initSave(role.getRoleId());
        return R.ok();
    }
    @ApiOperation(value = "修改角色")
    @PutMapping("/update")
    public R updateById(@RequestBody Role role) {
        roleService.updateById(role);
        return R.ok();
    }
    @ApiOperation(value = "检查角色是否被分配出去")
    @GetMapping("/getCheckRole/{roleId}")
    public R getCheckRole(@PathVariable String roleId) {
        boolean flag = roleService.checkRole(roleId);
        if (flag) {
            return R.error().message("该角色已经分配给其他用户，无法删除！");
        }
        return R.ok();
    }
    @ApiOperation(value = "删除角色")
    @DeleteMapping("/remove/{id}")
    public R remove(@PathVariable String id) {
        roleService.deleteRolePermission(id);
        roleService.removeById(id);
        return R.ok();
    }
    @ApiOperation(value = "根据id列表删除角色")
    @DeleteMapping("/batchRemove")
    public R batchRemove(@RequestBody List<String> idList) {
        roleService.removeByIds(idList);
        return R.ok();
    }
}
