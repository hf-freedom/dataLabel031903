package com.datalabel;

import com.datalabel.cache.LocalCache;
import com.datalabel.entity.*;
import com.datalabel.mapper.*;
import com.datalabel.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PermissionTest {

    @Autowired
    private MenuService menuService;

    @Autowired
    private ApiPermissionService apiPermissionService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationService organizationService;

    @BeforeEach
    public void setup() {
        LocalCache.getInstance().clear();
    }

    @Test
    public void testMenuModule() {
        Menu menu = new Menu();
        menu.setName("测试菜单");
        menu.setCode("TEST_MENU");
        menu.setPath("/test");
        menu.setParentId(0L);
        menu.setLevel(1);
        menu.setSortOrder(1);
        menu.setStatus(1);

        boolean saved = menuService.save(menu);
        assertTrue(saved, "菜单保存失败");
        assertNotNull(menu.getId(), "菜单ID不能为空");

        Menu found = menuService.findById(menu.getId());
        assertNotNull(found, "找不到保存的菜单");
        assertEquals("测试菜单", found.getName(), "菜单名称不匹配");

        List<Menu> allMenus = menuService.findAll();
        assertFalse(allMenus.isEmpty(), "菜单列表不应为空");

        System.out.println("菜单模块测试通过");
    }

    @Test
    public void testApiPermissionModule() {
        ApiPermission api = new ApiPermission();
        api.setName("测试API");
        api.setCode("test:api");
        api.setUrl("/api/test");
        api.setMethod("GET");
        api.setStatus(1);

        boolean saved = apiPermissionService.save(api);
        assertTrue(saved, "API权限保存失败");
        assertNotNull(api.getId(), "API权限ID不能为空");

        ApiPermission found = apiPermissionService.findById(api.getId());
        assertNotNull(found, "找不到保存的API权限");
        assertEquals("test:api", found.getCode(), "API权限编码不匹配");

        System.out.println("API权限模块测试通过");
    }

    @Test
    public void testRoleMenuBinding() {
        Role role = new Role();
        role.setName("测试角色");
        role.setCode("TEST_ROLE");
        roleService.save(role);

        Menu menu1 = new Menu();
        menu1.setName("菜单1");
        menu1.setCode("MENU1");
        menu1.setParentId(0L);
        menu1.setLevel(1);
        menuService.save(menu1);

        Menu menu2 = new Menu();
        menu2.setName("菜单2");
        menu2.setCode("MENU2");
        menu2.setParentId(0L);
        menu2.setLevel(1);
        menuService.save(menu2);

        boolean bound = rolePermissionService.bindRoleMenus(role.getId(), Arrays.asList(menu1.getId(), menu2.getId()));
        assertTrue(bound, "角色菜单绑定失败");

        List<Menu> roleMenus = rolePermissionService.findMenusByRoleId(role.getId());
        assertEquals(2, roleMenus.size(), "角色菜单数量不匹配");

        System.out.println("角色菜单绑定测试通过");
    }

    @Test
    public void testRoleApiBinding() {
        Role role = new Role();
        role.setName("测试角色");
        role.setCode("TEST_ROLE");
        roleService.save(role);

        ApiPermission api1 = new ApiPermission();
        api1.setName("API1");
        api1.setCode("api:1");
        api1.setUrl("/api/1");
        api1.setMethod("GET");
        apiPermissionService.save(api1);

        ApiPermission api2 = new ApiPermission();
        api2.setName("API2");
        api2.setCode("api:2");
        api2.setUrl("/api/2");
        api2.setMethod("POST");
        apiPermissionService.save(api2);

        boolean bound = rolePermissionService.bindRoleApis(role.getId(), Arrays.asList(api1.getId(), api2.getId()));
        assertTrue(bound, "角色API绑定失败");

        List<ApiPermission> roleApis = rolePermissionService.findApisByRoleId(role.getId());
        assertEquals(2, roleApis.size(), "角色API数量不匹配");

        boolean hasPermission = rolePermissionService.hasApiPermission(role.getId(), "api:1");
        assertTrue(hasPermission, "应该拥有api:1权限");

        boolean noPermission = rolePermissionService.hasApiPermission(role.getId(), "api:3");
        assertFalse(noPermission, "不应该拥有api:3权限");

        System.out.println("角色API绑定测试通过");
    }

    @Test
    public void testRoleOrganizationBinding() {
        Role role = new Role();
        role.setName("测试角色");
        role.setCode("TEST_ROLE");
        roleService.save(role);

        Organization org1 = new Organization();
        org1.setName("组织1");
        org1.setCode("ORG1");
        org1.setParentId(0L);
        org1.setLevel(1);
        organizationService.save(org1);

        Organization org2 = new Organization();
        org2.setName("组织2");
        org2.setCode("ORG2");
        org2.setParentId(0L);
        org2.setLevel(1);
        organizationService.save(org2);

        boolean bound = rolePermissionService.bindRoleOrganizations(role.getId(), Arrays.asList(org1.getId(), org2.getId()));
        assertTrue(bound, "角色组织机构绑定失败");

        List<Organization> roleOrgs = rolePermissionService.findOrganizationsByRoleId(role.getId());
        assertEquals(2, roleOrgs.size(), "角色组织机构数量不匹配");

        System.out.println("角色组织机构绑定测试通过");
    }

    @Test
    public void testUserRoleBinding() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpass");
        user.setRealName("测试用户");
        userService.save(user);

        Role role = new Role();
        role.setName("测试角色");
        role.setCode("TEST_ROLE");
        roleService.save(role);

        boolean bound = userService.bindRole(user.getId(), role.getId());
        assertTrue(bound, "用户角色绑定失败");

        User updatedUser = userService.findById(user.getId());
        assertEquals(role.getId(), updatedUser.getRoleId(), "用户角色ID不匹配");

        List<User> usersByRole = userService.findByRoleId(role.getId());
        assertEquals(1, usersByRole.size(), "角色下的用户数量不匹配");

        System.out.println("用户角色绑定测试通过");
    }
}
