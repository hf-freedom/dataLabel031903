package com.datalabel.controller;

import com.datalabel.annotation.RequirePermission;
import com.datalabel.common.Result;
import com.datalabel.entity.Role;
import com.datalabel.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    
    @Autowired
    private RoleService roleService;
    
    @RequirePermission("role:list")
    @GetMapping("/list")
    public Result<List<Role>> list() {
        return Result.success(roleService.findAll());
    }
    
    @RequirePermission("role:list")
    @GetMapping("/{id}")
    public Result<Role> getById(@PathVariable Long id) {
        Role role = roleService.findById(id);
        if (role == null) {
            return Result.error("角色不存在");
        }
        return Result.success(role);
    }
    
    @RequirePermission("role:save")
    @PostMapping("/save")
    public Result<String> save(@RequestBody Role role) {
        if (roleService.save(role)) {
            return Result.success("保存成功", null);
        }
        return Result.error("保存失败");
    }
    
    @RequirePermission("role:delete")
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        if (roleService.deleteById(id)) {
            return Result.success("删除成功", null);
        }
        return Result.error("删除失败");
    }
}
