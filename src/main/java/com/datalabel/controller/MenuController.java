package com.datalabel.controller;

import com.datalabel.annotation.RequiresPermission;
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
    
    @GetMapping("/list")
    @RequiresPermission("menu:view")
    public Result<List<Menu>> list() {
        return Result.success(menuService.findAll());
    }
    
    @GetMapping("/tree")
    @RequiresPermission("menu:view")
    public Result<List<Menu>> tree() {
        return Result.success(menuService.findAll());
    }
    
    @GetMapping("/{id}")
    @RequiresPermission("menu:view")
    public Result<Menu> getById(@PathVariable Long id) {
        Menu menu = menuService.findById(id);
        if (menu == null) {
            return Result.error("菜单不存在");
        }
        return Result.success(menu);
    }
    
    @PostMapping("/save")
    @RequiresPermission("menu:edit")
    public Result<String> save(@RequestBody Menu menu) {
        if (menuService.save(menu)) {
            return Result.success("保存成功", null);
        }
        return Result.error("保存失败");
    }
    
    @DeleteMapping("/{id}")
    @RequiresPermission("menu:delete")
    public Result<String> delete(@PathVariable Long id) {
        if (menuService.deleteById(id)) {
            return Result.success("删除成功", null);
        }
        return Result.error("删除失败");
    }
}