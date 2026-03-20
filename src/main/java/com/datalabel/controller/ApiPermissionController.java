package com.datalabel.controller;

import com.datalabel.annotation.RequirePermission;
import com.datalabel.common.Result;
import com.datalabel.entity.ApiPermission;
import com.datalabel.service.ApiPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permission")
public class ApiPermissionController {
    
    @Autowired
    private ApiPermissionService apiPermissionService;
    
    @RequirePermission("api:list")
    @GetMapping("/list")
    public Result<List<ApiPermission>> list() {
        return Result.success(apiPermissionService.findAll());
    }
    
    @RequirePermission("api:list")
    @GetMapping("/{id}")
    public Result<ApiPermission> getById(@PathVariable Long id) {
        ApiPermission api = apiPermissionService.findById(id);
        if (api == null) {
            return Result.error("API权限不存在");
        }
        return Result.success(api);
    }
    
    @RequirePermission("api:list")
    @GetMapping("/byMenu/{menuId}")
    public Result<List<ApiPermission>> getByMenuId(@PathVariable Long menuId) {
        return Result.success(apiPermissionService.findByMenuId(menuId));
    }
    
    @RequirePermission("api:save")
    @PostMapping("/save")
    public Result<String> save(@RequestBody ApiPermission apiPermission) {
        if (apiPermissionService.save(apiPermission)) {
            return Result.success("保存成功", null);
        }
        return Result.error("保存失败");
    }
    
    @RequirePermission("api:delete")
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        if (apiPermissionService.deleteById(id)) {
            return Result.success("删除成功", null);
        }
        return Result.error("删除失败");
    }
}
