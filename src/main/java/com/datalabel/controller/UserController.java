package com.datalabel.controller;

import com.datalabel.annotation.RequiresPermission;
import com.datalabel.common.Result;
import com.datalabel.entity.ApiPermission;
import com.datalabel.entity.Menu;
import com.datalabel.entity.User;
import com.datalabel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/list")
    @RequiresPermission("user:view")
    public Result<List<User>> list() {
        return Result.success(userService.findAll());
    }
    
    @GetMapping("/{id}")
    @RequiresPermission("user:view")
    public Result<User> getById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return Result.error("用户不存在");
        }
        return Result.success(user);
    }
    
    @PostMapping("/save")
    @RequiresPermission("user:edit")
    public Result<String> save(@RequestBody User user) {
        User existUser = userService.findByUsername(user.getUsername());
        if (existUser != null && !existUser.getId().equals(user.getId())) {
            return Result.error("用户名已存在");
        }
        if (userService.save(user)) {
            return Result.success("保存成功", null);
        }
        return Result.error("保存失败");
    }
    
    @PostMapping("/update")
    @RequiresPermission("user:edit")
    public Result<String> update(@RequestBody User user, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return Result.error(401, "未登录");
        }
        if (currentUser.getUserType() == 0) {
            User updateUser = userService.findById(currentUser.getId());
            updateUser.setRealName(user.getRealName());
            updateUser.setEmail(user.getEmail());
            updateUser.setPhone(user.getPhone());
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                updateUser.setPassword(user.getPassword());
            }
            if (userService.update(updateUser)) {
                session.setAttribute("currentUser", updateUser);
                return Result.success("修改成功", null);
            }
        } else {
            if (userService.update(user)) {
                return Result.success("修改成功", null);
            }
        }
        return Result.error("修改失败");
    }
    
    @DeleteMapping("/{id}")
    @RequiresPermission("user:delete")
    public Result<String> delete(@PathVariable Long id) {
        if (userService.deleteById(id)) {
            return Result.success("删除成功", null);
        }
        return Result.error("删除失败");
    }
    
    @PostMapping("/bindRole")
    @RequiresPermission("user:bind")
    public Result<String> bindRole(@RequestParam Long userId, @RequestParam Long roleId) {
        if (userService.bindRole(userId, roleId)) {
            return Result.success("绑定成功", null);
        }
        return Result.error("绑定失败");
    }
    
    @GetMapping("/{id}/menus")
    public Result<List<Menu>> getUserMenus(@PathVariable Long id) {
        return Result.success(userService.getUserMenus(id));
    }
    
    @GetMapping("/{id}/permissions")
    public Result<List<ApiPermission>> getUserPermissions(@PathVariable Long id) {
        return Result.success(userService.getUserPermissions(id));
    }
    
    @GetMapping("/current/menus")
    public Result<List<Menu>> getCurrentUserMenus(HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return Result.error(401, "未登录");
        }
        return Result.success(userService.getUserMenus(currentUser.getId()));
    }
    
    @GetMapping("/current/permissions")
    public Result<List<ApiPermission>> getCurrentUserPermissions(HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return Result.error(401, "未登录");
        }
        return Result.success(userService.getUserPermissions(currentUser.getId()));
    }
}
