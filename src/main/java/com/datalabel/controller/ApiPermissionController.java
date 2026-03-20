package com.datalabel.controller;

import com.datalabel.common.Result;
import com.datalabel.entity.ApiPermission;
import com.datalabel.service.ApiPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apiPermission")
public class ApiPermissionController {
    
    @Autowired
    private ApiPermissionService apiPermissionService;
    
    @GetMapping("/list")
    public Result<List<ApiPermission>> list() {
        return Result.success(apiPermissionService.findAll());
    }
    
    @GetMapping("/{id}")
    public Result<ApiPermission> getById(@PathVariable Long id) {
        ApiPermission apiPermission = apiPermissionService.findById(id);
        if (apiPermission == null) {
            return Result.error("API权限不存在");
        }
        return Result.success(apiPermission);
    }
    
    @PostMapping("/save")
    public Result<String> save(@RequestBody ApiPermission apiPermission) {
        if (apiPermissionService.save(apiPermission)) {
            return Result.success("保存成功", null);
        }
        return Result.error("保存失败");
    }
    
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        if (apiPermissionService.deleteById(id)) {
            return Result.success("删除成功", null);
        }
        return Result.error("删除失败");
    }
}