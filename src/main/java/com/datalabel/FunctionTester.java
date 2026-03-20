package com.datalabel;

import com.datalabel.entity.*;
import com.datalabel.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Order(100)
public class FunctionTester implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private MenuService menuService;
    
    @Autowired
    private ApiPermissionService apiPermissionService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n========== 功能测试开始 ==========");

        try {
            test1_UserLogin();
            test2_GetUserMenus();
            test3_GetUserPermissions();
            test4_HasPermission();
            test5_RoleMenuBinding();
            test6_RoleOrganizationBinding();
            test7_BindMenus();
            test8_BindApis();
            System.out.println("\n========== 所有功能测试通过! ==========");
        } catch (Exception e) {
            System.out.println("\n========== 功能测试失败 ==========");
            e.printStackTrace();
        }
    }

    private void test1_UserLogin() {
        System.out.println("\n[测试1] 用户登录");
        User user = userService.login("admin", "admin123");
        if (user != null && "admin".equals(user.getUsername())) {
            System.out.println("  ✓ 登录成功: " + user.getUsername());
        } else {
            throw new RuntimeException("登录失败");
        }
    }

    private void test2_GetUserMenus() {
        System.out.println("\n[测试2] 获取用户菜单");
        User admin = userService.login("admin", "admin123");
        List<Menu> menus = userService.getUserMenus(admin.getId());
        if (menus != null && menus.size() > 0) {
            System.out.println("  ✓ 菜单数量: " + menus.size());
            menus.forEach(menu -> System.out.println("    - " + menu.getName()));
        } else {
            throw new RuntimeException("获取菜单失败");
        }
    }

    private void test3_GetUserPermissions() {
        System.out.println("\n[测试3] 获取用户权限");
        User admin = userService.login("admin", "admin123");
        List<ApiPermission> permissions = userService.getUserPermissions(admin.getId());
        if (permissions != null && permissions.size() > 0) {
            System.out.println("  ✓ 权限数量: " + permissions.size());
            permissions.forEach(p -> System.out.println("    - " + p.getName() + " (" + p.getCode() + ")"));
        } else {
            throw new RuntimeException("获取权限失败");
        }
    }

    private void test4_HasPermission() {
        System.out.println("\n[测试4] 权限验证");
        User admin = userService.login("admin", "admin123");
        if (permissionService.hasPermission(admin, "user:view")
                && permissionService.hasPermission(admin, "user:edit")
                && permissionService.hasPermission(admin, "role:view")) {
            System.out.println("  ✓ 权限验证通过");
        } else {
            throw new RuntimeException("权限验证失败");
        }
    }

    private void test5_RoleMenuBinding() {
        System.out.println("\n[测试5] 角色菜单绑定查询");
        List<Role> roles = roleService.findAll();
        if (!roles.isEmpty()) {
            Role adminRole = roles.stream().filter(r -> "ADMIN".equals(r.getCode())).findFirst().orElse(null);
            if (adminRole != null) {
                List<Menu> menus = roleService.getMenusByRoleId(adminRole.getId());
                if (menus != null && menus.size() > 0) {
                    System.out.println("  ✓ 管理员角色绑定菜单数: " + menus.size());
                } else {
                    throw new RuntimeException("角色菜单绑定查询失败");
                }
            }
        } else {
            throw new RuntimeException("没有找到角色");
        }
    }

    private void test6_RoleOrganizationBinding() {
        System.out.println("\n[测试6] 角色组织机构绑定");
        List<Role> roles = roleService.findAll();
        boolean allBound = roles.stream().allMatch(r -> r.getOrganizationId() != null);
        if (allBound) {
            System.out.println("  ✓ 所有角色都已绑定组织机构");
            roles.forEach(r -> System.out.println("    - 角色: " + r.getName() + ", 组织机构ID: " + r.getOrganizationId()));
        } else {
            throw new RuntimeException("存在角色未绑定组织机构");
        }
    }

    private void test7_BindMenus() {
        System.out.println("\n[测试7] 绑定菜单功能");
        List<Role> roles = roleService.findAll();
        if (!roles.isEmpty()) {
            Role role = roles.get(0);
            List<Menu> allMenus = menuService.findAll();
            if (allMenus.size() >= 2) {
                boolean result = roleService.bindMenus(role.getId(), Arrays.asList(allMenus.get(0).getId(), allMenus.get(1).getId()));
                if (result) {
                    List<Menu> menus = roleService.getMenusByRoleId(role.getId());
                    System.out.println("  ✓ 绑定菜单成功，当前绑定数: " + menus.size());
                } else {
                    throw new RuntimeException("绑定菜单失败");
                }
            }
        }
    }

    private void test8_BindApis() {
        System.out.println("\n[测试8] 绑定API权限功能");
        List<Role> roles = roleService.findAll();
        if (!roles.isEmpty()) {
            Role role = roles.get(0);
            List<ApiPermission> allApis = apiPermissionService.findAll();
            if (allApis.size() >= 2) {
                boolean result = permissionService.bindApis(role.getId(), Arrays.asList(allApis.get(0).getId(), allApis.get(1).getId()));
                if (result) {
                    List<ApiPermission> apis = permissionService.getApiPermissionsByRoleId(role.getId());
                    System.out.println("  ✓ 绑定API权限成功，当前绑定数: " + apis.size());
                } else {
                    throw new RuntimeException("绑定API权限失败");
                }
            }
        }
    }
}