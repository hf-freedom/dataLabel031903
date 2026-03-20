package com.datalabel.controller;

import com.datalabel.annotation.RequirePermission;
import com.datalabel.common.Result;
import com.datalabel.entity.Organization;
import com.datalabel.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/org")
public class OrganizationController {
    
    @Autowired
    private OrganizationService organizationService;
    
    @RequirePermission("org:list")
    @GetMapping("/list")
    public Result<List<Organization>> list() {
        return Result.success(organizationService.findAll());
    }
    
    @RequirePermission("org:list")
    @GetMapping("/tree")
    public Result<List<Organization>> tree() {
        return Result.success(organizationService.findAll());
    }
    
    @RequirePermission("org:list")
    @GetMapping("/{id}")
    public Result<Organization> getById(@PathVariable Long id) {
        Organization org = organizationService.findById(id);
        if (org == null) {
            return Result.error("组织机构不存在");
        }
        return Result.success(org);
    }
    
    @RequirePermission("org:save")
    @PostMapping("/save")
    public Result<String> save(@RequestBody Organization org) {
        if (organizationService.save(org)) {
            return Result.success("保存成功", null);
        }
        return Result.error("保存失败");
    }
    
    @RequirePermission("org:delete")
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        if (organizationService.deleteById(id)) {
            return Result.success("删除成功", null);
        }
        return Result.error("删除失败");
    }
}
