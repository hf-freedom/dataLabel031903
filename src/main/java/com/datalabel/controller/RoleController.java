package com.datalabel.controller;

import com.datalabel.annotation.RequiresPermission;
import com.datalabel.common.Result;
import com.datalabel.entity.ApiPermission;
import com.datalabel.entity.Menu;
import com.datalabel.entity.Role;
import com.datalabel.service.PermissionService;
import com.datalabel.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private PermissionService permissionService;
    
    @GetMapping("/list")
    @RequiresPermission("role:view")
    public Result<List<Role>> list() {
        return Result.success(roleService.findAll());
    }
    
    @GetMapping("/org/{orgId}")
    @RequiresPermission("role:view")
    public Result<List<Role>> getByOrganizationId(@PathVariable Long orgId) {
        return Result.success(roleService.findByOrganizationId(orgId));
    }
    
    @GetMapping("/{id}")
    @RequiresPermission("role:view")
    public Result<Role> getById(@PathVariable Long id) {
        Role role = roleService.findById(id);
        if (role == null) {
            return Result.error("角色不存在");
        }
        return Result.success(role);
    }
    
    @GetMapping("/{id}/menus")
    @RequiresPermission("role:view")
    public Result<List<Menu>> getMenusByRoleId(@PathVariable Long id) {
        return Result.success(roleService.getMenusByRoleId(id));
    }
    
    @GetMapping("/{id}/apis")
    @RequiresPermission("role:view")
    public Result<List<ApiPermission>> getApisByRoleId(@PathVariable Long id) {
        return Result.success(permissionService.getApiPermissionsByRoleId(id));
    }
    
    @PostMapping("/save")
    @RequiresPermission("role:edit")
    public Result<String> save(@RequestBody Role role) {
        if (roleService.save(role)) {
            return Result.success("保存成功", null);
        }
        return Result.error("保存失败");
    }
    
    @PostMapping("/bindMenus")
    @RequiresPermission("role:bind")
    public Result<String> bindMenus(@RequestParam Long roleId, @RequestBody List<Long> menuIds) {
        if (roleService.bindMenus(roleId, menuIds)) {
            return Result.success("绑定成功", null);
        }
        return Result.error("绑定失败");
    }
    
    @PostMapping("/bindApis")
    @RequiresPermission("role:bind")
    public Result<String> bindApis(@RequestParam Long roleId, @RequestBody List<Long> apiIds) {
        if (permissionService.bindApis(roleId, apiIds)) {
            return Result.success("绑定成功", null);
        }
        return Result.error("绑定失败");
    }
    
    @PostMapping("/bindOrg")
    @RequiresPermission("role:bind")
    public Result<String> bindOrganization(@RequestParam Long roleId, @RequestParam Long orgId) {
        if (roleService.bindOrganization(roleId, orgId)) {
            return Result.success("绑定成功", null);
        }
        return Result.error("绑定失败");
    }
    
    @DeleteMapping("/{id}")
    @RequiresPermission("role:delete")
    public Result<String> delete(@PathVariable Long id) {
        if (roleService.deleteById(id)) {
            return Result.success("删除成功", null);
        }
        return Result.error("删除失败");
    }
}
