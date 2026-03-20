package com.datalabel.config;

import com.datalabel.cache.LocalCache;
import com.datalabel.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private AdminConfig adminConfig;
    
    @Override
    public void run(String... args) throws Exception {
        LocalCache cache = LocalCache.getInstance();
        
        Long adminUserId = cache.generateId();
        User admin = new User(adminUserId, adminConfig.getUsername(), adminConfig.getPassword(), "管理员");
        admin.setUserType(1);
        cache.put(admin.getId(), admin);
        
        Organization org1 = new Organization();
        org1.setId(cache.generateId());
        org1.setName("总公司");
        org1.setCode("HQ");
        org1.setParentId(0L);
        org1.setLevel(1);
        cache.put(org1.getId(), org1);
        
        Organization org2 = new Organization();
        org2.setId(cache.generateId());
        org2.setName("技术部");
        org2.setCode("TECH");
        org2.setParentId(org1.getId());
        org2.setLevel(2);
        cache.put(org2.getId(), org2);
        
        Organization org3 = new Organization();
        org3.setId(cache.generateId());
        org3.setName("市场部");
        org3.setCode("MARKET");
        org3.setParentId(org1.getId());
        org3.setLevel(2);
        cache.put(org3.getId(), org3);
        
        Long adminRoleId = cache.generateId();
        Role role1 = new Role();
        role1.setId(adminRoleId);
        role1.setName("管理员");
        role1.setCode("ADMIN");
        role1.setDescription("系统管理员角色");
        role1.setOrganizationId(org1.getId());
        cache.put(role1.getId(), role1);
        
        admin.setRoleId(adminRoleId);
        cache.put(admin.getId(), admin);
        
        Role role2 = new Role();
        role2.setId(cache.generateId());
        role2.setName("普通用户");
        role2.setCode("USER");
        role2.setDescription("普通用户角色");
        role2.setOrganizationId(org2.getId());
        cache.put(role2.getId(), role2);
        
        Menu menu1 = new Menu();
        menu1.setId(cache.generateId());
        menu1.setName("系统管理");
        menu1.setCode("SYSTEM");
        menu1.setPath("/system");
        menu1.setIcon("system");
        menu1.setParentId(0L);
        menu1.setSort(1);
        menu1.setType(1);
        cache.put(menu1.getId(), menu1);
        
        Menu menu2 = new Menu();
        menu2.setId(cache.generateId());
        menu2.setName("用户管理");
        menu2.setCode("USER_MANAGE");
        menu2.setPath("/system/user");
        menu2.setIcon("user");
        menu2.setParentId(menu1.getId());
        menu2.setSort(1);
        menu2.setType(2);
        cache.put(menu2.getId(), menu2);
        
        Menu menu3 = new Menu();
        menu3.setId(cache.generateId());
        menu3.setName("角色管理");
        menu3.setCode("ROLE_MANAGE");
        menu3.setPath("/system/role");
        menu3.setIcon("role");
        menu3.setParentId(menu1.getId());
        menu3.setSort(2);
        menu3.setType(2);
        cache.put(menu3.getId(), menu3);
        
        Menu menu4 = new Menu();
        menu4.setId(cache.generateId());
        menu4.setName("组织机构");
        menu4.setCode("ORG_MANAGE");
        menu4.setPath("/system/org");
        menu4.setIcon("org");
        menu4.setParentId(menu1.getId());
        menu4.setSort(3);
        menu4.setType(2);
        cache.put(menu4.getId(), menu4);
        
        Menu menu5 = new Menu();
        menu5.setId(cache.generateId());
        menu5.setName("菜单管理");
        menu5.setCode("MENU_MANAGE");
        menu5.setPath("/system/menu");
        menu5.setIcon("menu");
        menu5.setParentId(menu1.getId());
        menu5.setSort(4);
        menu5.setType(2);
        cache.put(menu5.getId(), menu5);
        
        ApiPermission api1 = new ApiPermission();
        api1.setId(cache.generateId());
        api1.setName("查看用户列表");
        api1.setCode("user:view");
        api1.setPath("/api/user/list");
        api1.setMethod("GET");
        api1.setMenuId(menu2.getId());
        cache.put(api1.getId(), api1);
        
        ApiPermission api2 = new ApiPermission();
        api2.setId(cache.generateId());
        api2.setName("保存用户");
        api2.setCode("user:edit");
        api2.setPath("/api/user/save");
        api2.setMethod("POST");
        api2.setMenuId(menu2.getId());
        cache.put(api2.getId(), api2);
        
        ApiPermission api3 = new ApiPermission();
        api3.setId(cache.generateId());
        api3.setName("删除用户");
        api3.setCode("user:delete");
        api3.setPath("/api/user/");
        api3.setMethod("DELETE");
        api3.setMenuId(menu2.getId());
        cache.put(api3.getId(), api3);
        
        ApiPermission api4 = new ApiPermission();
        api4.setId(cache.generateId());
        api4.setName("绑定用户角色");
        api4.setCode("user:bind");
        api4.setPath("/api/user/bindRole");
        api4.setMethod("POST");
        api4.setMenuId(menu2.getId());
        cache.put(api4.getId(), api4);
        
        ApiPermission api5 = new ApiPermission();
        api5.setId(cache.generateId());
        api5.setName("查看角色列表");
        api5.setCode("role:view");
        api5.setPath("/api/role/list");
        api5.setMethod("GET");
        api5.setMenuId(menu3.getId());
        cache.put(api5.getId(), api5);
        
        ApiPermission api6 = new ApiPermission();
        api6.setId(cache.generateId());
        api6.setName("保存角色");
        api6.setCode("role:edit");
        api6.setPath("/api/role/save");
        api6.setMethod("POST");
        api6.setMenuId(menu3.getId());
        cache.put(api6.getId(), api6);
        
        ApiPermission api7 = new ApiPermission();
        api7.setId(cache.generateId());
        api7.setName("删除角色");
        api7.setCode("role:delete");
        api7.setPath("/api/role/");
        api7.setMethod("DELETE");
        api7.setMenuId(menu3.getId());
        cache.put(api7.getId(), api7);
        
        ApiPermission api8 = new ApiPermission();
        api8.setId(cache.generateId());
        api8.setName("绑定角色菜单/API/组织机构");
        api8.setCode("role:bind");
        api8.setPath("/api/role/bind");
        api8.setMethod("POST");
        api8.setMenuId(menu3.getId());
        cache.put(api8.getId(), api8);
        
        ApiPermission api9 = new ApiPermission();
        api9.setId(cache.generateId());
        api9.setName("查看组织机构列表");
        api9.setCode("org:view");
        api9.setPath("/api/org/list");
        api9.setMethod("GET");
        api9.setMenuId(menu4.getId());
        cache.put(api9.getId(), api9);
        
        ApiPermission api10 = new ApiPermission();
        api10.setId(cache.generateId());
        api10.setName("保存组织机构");
        api10.setCode("org:edit");
        api10.setPath("/api/org/save");
        api10.setMethod("POST");
        api10.setMenuId(menu4.getId());
        cache.put(api10.getId(), api10);
        
        ApiPermission api11 = new ApiPermission();
        api11.setId(cache.generateId());
        api11.setName("删除组织机构");
        api11.setCode("org:delete");
        api11.setPath("/api/org/");
        api11.setMethod("DELETE");
        api11.setMenuId(menu4.getId());
        cache.put(api11.getId(), api11);
        
        ApiPermission api12 = new ApiPermission();
        api12.setId(cache.generateId());
        api12.setName("查看菜单列表");
        api12.setCode("menu:view");
        api12.setPath("/api/menu/list");
        api12.setMethod("GET");
        api12.setMenuId(menu5.getId());
        cache.put(api12.getId(), api12);
        
        ApiPermission api13 = new ApiPermission();
        api13.setId(cache.generateId());
        api13.setName("保存菜单");
        api13.setCode("menu:edit");
        api13.setPath("/api/menu/save");
        api13.setMethod("POST");
        api13.setMenuId(menu5.getId());
        cache.put(api13.getId(), api13);
        
        ApiPermission api14 = new ApiPermission();
        api14.setId(cache.generateId());
        api14.setName("删除菜单");
        api14.setCode("menu:delete");
        api14.setPath("/api/menu/");
        api14.setMethod("DELETE");
        api14.setMenuId(menu5.getId());
        cache.put(api14.getId(), api14);
        
        RoleMenu rm1 = new RoleMenu(role1.getId(), menu1.getId());
        rm1.setId(cache.generateId());
        cache.put(rm1.getId(), rm1);
        
        RoleMenu rm2 = new RoleMenu(role1.getId(), menu2.getId());
        rm2.setId(cache.generateId());
        cache.put(rm2.getId(), rm2);
        
        RoleMenu rm3 = new RoleMenu(role1.getId(), menu3.getId());
        rm3.setId(cache.generateId());
        cache.put(rm3.getId(), rm3);
        
        RoleMenu rm4 = new RoleMenu(role1.getId(), menu4.getId());
        rm4.setId(cache.generateId());
        cache.put(rm4.getId(), rm4);
        
        RoleMenu rm5 = new RoleMenu(role1.getId(), menu5.getId());
        rm5.setId(cache.generateId());
        cache.put(rm5.getId(), rm5);
        
        RoleApi ra1 = new RoleApi(role1.getId(), api1.getId());
        ra1.setId(cache.generateId());
        cache.put(ra1.getId(), ra1);
        
        RoleApi ra2 = new RoleApi(role1.getId(), api2.getId());
        ra2.setId(cache.generateId());
        cache.put(ra2.getId(), ra2);
        
        RoleApi ra3 = new RoleApi(role1.getId(), api3.getId());
        ra3.setId(cache.generateId());
        cache.put(ra3.getId(), ra3);
        
        RoleApi ra4 = new RoleApi(role1.getId(), api4.getId());
        ra4.setId(cache.generateId());
        cache.put(ra4.getId(), ra4);
        
        RoleApi ra5 = new RoleApi(role1.getId(), api5.getId());
        ra5.setId(cache.generateId());
        cache.put(ra5.getId(), ra5);
        
        RoleApi ra6 = new RoleApi(role1.getId(), api6.getId());
        ra6.setId(cache.generateId());
        cache.put(ra6.getId(), ra6);
        
        RoleApi ra7 = new RoleApi(role1.getId(), api7.getId());
        ra7.setId(cache.generateId());
        cache.put(ra7.getId(), ra7);
        
        RoleApi ra8 = new RoleApi(role1.getId(), api8.getId());
        ra8.setId(cache.generateId());
        cache.put(ra8.getId(), ra8);
        
        RoleApi ra9 = new RoleApi(role1.getId(), api9.getId());
        ra9.setId(cache.generateId());
        cache.put(ra9.getId(), ra9);
        
        RoleApi ra10 = new RoleApi(role1.getId(), api10.getId());
        ra10.setId(cache.generateId());
        cache.put(ra10.getId(), ra10);
        
        RoleApi ra11 = new RoleApi(role1.getId(), api11.getId());
        ra11.setId(cache.generateId());
        cache.put(ra11.getId(), ra11);
        
        RoleApi ra12 = new RoleApi(role1.getId(), api12.getId());
        ra12.setId(cache.generateId());
        cache.put(ra12.getId(), ra12);
        
        RoleApi ra13 = new RoleApi(role1.getId(), api13.getId());
        ra13.setId(cache.generateId());
        cache.put(ra13.getId(), ra13);
        
        RoleApi ra14 = new RoleApi(role1.getId(), api14.getId());
        ra14.setId(cache.generateId());
        cache.put(ra14.getId(), ra14);
        
        System.out.println("初始化数据完成，管理员账号: " + adminConfig.getUsername());
    }
}
