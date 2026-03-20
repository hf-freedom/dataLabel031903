package com.datalabel.controller;

import com.datalabel.annotation.RequirePermission;
import com.datalabel.common.Result;
import com.datalabel.entity.ApiPermission;
import com.datalabel.entity.Menu;
import com.datalabel.entity.Organization;
import com.datalabel.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rolePermission")
public class RolePermissionController {

    @Autowired
    private RolePermissionService rolePermissionService;

    @RequirePermission("rolePermission:list")
    @GetMapping("/menus/{roleId}")
    public Result<List<Menu>> getMenusByRoleId(@PathVariable Long roleId) {
        return Result.success(rolePermissionService.findMenusByRoleId(roleId));
    }

    @RequirePermission("rolePermission:list")
    @GetMapping("/menuIds/{roleId}")
    public Result<List<Long>> getMenuIdsByRoleId(@PathVariable Long roleId) {
        return Result.success(rolePermissionService.findMenuIdsByRoleId(roleId));
    }

    @RequirePermission("rolePermission:bind")
    @PostMapping("/bindMenus")
    public Result<String> bindMenus(@RequestParam Long roleId, @RequestBody List<Long> menuIds) {
        if (rolePermissionService.bindRoleMenus(roleId, menuIds)) {
            return Result.success("绑定成功", null);
        }
        return Result.error("绑定失败");
    }

    @RequirePermission("rolePermission:bind")
    @PostMapping("/bindMenu")
    public Result<String> bindMenu(@RequestParam Long roleId, @RequestParam Long menuId) {
        if (rolePermissionService.bindRoleMenu(roleId, menuId)) {
            return Result.success("绑定成功", null);
        }
        return Result.error("绑定失败");
    }

    @RequirePermission("rolePermission:bind")
    @PostMapping("/unbindMenu")
    public Result<String> unbindMenu(@RequestParam Long roleId, @RequestParam Long menuId) {
        if (rolePermissionService.unbindRoleMenu(roleId, menuId)) {
            return Result.success("解绑成功", null);
        }
        return Result.error("解绑失败");
    }

    @RequirePermission("rolePermission:list")
    @GetMapping("/apis/{roleId}")
    public Result<List<ApiPermission>> getApisByRoleId(@PathVariable Long roleId) {
        return Result.success(rolePermissionService.findApisByRoleId(roleId));
    }

    @RequirePermission("rolePermission:list")
    @GetMapping("/apiIds/{roleId}")
    public Result<List<Long>> getApiIdsByRoleId(@PathVariable Long roleId) {
        return Result.success(rolePermissionService.findApiIdsByRoleId(roleId));
    }

    @RequirePermission("rolePermission:bind")
    @PostMapping("/bindApis")
    public Result<String> bindApis(@RequestParam Long roleId, @RequestBody List<Long> apiIds) {
        if (rolePermissionService.bindRoleApis(roleId, apiIds)) {
            return Result.success("绑定成功", null);
        }
        return Result.error("绑定失败");
    }

    @RequirePermission("rolePermission:bind")
    @PostMapping("/bindApi")
    public Result<String> bindApi(@RequestParam Long roleId, @RequestParam Long apiId) {
        if (rolePermissionService.bindRoleApi(roleId, apiId)) {
            return Result.success("绑定成功", null);
        }
        return Result.error("绑定失败");
    }

    @RequirePermission("rolePermission:bind")
    @PostMapping("/unbindApi")
    public Result<String> unbindApi(@RequestParam Long roleId, @RequestParam Long apiId) {
        if (rolePermissionService.unbindRoleApi(roleId, apiId)) {
            return Result.success("解绑成功", null);
        }
        return Result.error("解绑失败");
    }

    @RequirePermission("rolePermission:list")
    @GetMapping("/organizations/{roleId}")
    public Result<List<Organization>> getOrganizationsByRoleId(@PathVariable Long roleId) {
        return Result.success(rolePermissionService.findOrganizationsByRoleId(roleId));
    }

    @RequirePermission("rolePermission:list")
    @GetMapping("/organizationIds/{roleId}")
    public Result<List<Long>> getOrganizationIdsByRoleId(@PathVariable Long roleId) {
        return Result.success(rolePermissionService.findOrganizationIdsByRoleId(roleId));
    }

    @RequirePermission("rolePermission:bind")
    @PostMapping("/bindOrganizations")
    public Result<String> bindOrganizations(@RequestParam Long roleId, @RequestBody List<Long> organizationIds) {
        if (rolePermissionService.bindRoleOrganizations(roleId, organizationIds)) {
            return Result.success("绑定成功", null);
        }
        return Result.error("绑定失败");
    }

    @RequirePermission("rolePermission:bind")
    @PostMapping("/bindOrganization")
    public Result<String> bindOrganization(@RequestParam Long roleId, @RequestParam Long organizationId) {
        if (rolePermissionService.bindRoleOrganization(roleId, organizationId)) {
            return Result.success("绑定成功", null);
        }
        return Result.error("绑定失败");
    }

    @RequirePermission("rolePermission:bind")
    @PostMapping("/unbindOrganization")
    public Result<String> unbindOrganization(@RequestParam Long roleId, @RequestParam Long organizationId) {
        if (rolePermissionService.unbindRoleOrganization(roleId, organizationId)) {
            return Result.success("解绑成功", null);
        }
        return Result.error("解绑失败");
    }
}
