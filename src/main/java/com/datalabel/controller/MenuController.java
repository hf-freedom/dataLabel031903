package com.datalabel.controller;

import com.datalabel.annotation.RequirePermission;
import com.datalabel.common.Result;
import com.datalabel.entity.Menu;
import com.datalabel.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    
    @Autowired
    private MenuService menuService;
    
    @RequirePermission("menu:list")
    @GetMapping("/list")
    public Result<List<Menu>> list() {
        return Result.success(menuService.findAll());
    }
    
    @RequirePermission("menu:list")
    @GetMapping("/tree")
    public Result<List<Menu>> tree() {
        return Result.success(menuService.buildMenuTree());
    }
    
    @RequirePermission("menu:list")
    @GetMapping("/{id}")
    public Result<Menu> getById(@PathVariable Long id) {
        Menu menu = menuService.findById(id);
        if (menu == null) {
            return Result.error("菜单不存在");
        }
        return Result.success(menu);
    }
    
    @RequirePermission("menu:list")
    @GetMapping("/children/{parentId}")
    public Result<List<Menu>> getByParentId(@PathVariable Long parentId) {
        return Result.success(menuService.findByParentId(parentId));
    }
    
    @RequirePermission("menu:save")
    @PostMapping("/save")
    public Result<String> save(@RequestBody Menu menu) {
        if (menuService.save(menu)) {
            return Result.success("保存成功", null);
        }
        return Result.error("保存失败");
    }
    
    @RequirePermission("menu:delete")
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        if (menuService.deleteById(id)) {
            return Result.success("删除成功", null);
        }
        return Result.error("删除失败");
    }
}
