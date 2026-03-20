package com.datalabel.config;

import com.datalabel.cache.LocalCache;
import com.datalabel.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private AdminConfig adminConfig;
    
    @Override
    public void run(String... args) throws Exception {
        LocalCache cache = LocalCache.getInstance();
        
        User admin = new User(cache.generateId(), adminConfig.getUsername(), adminConfig.getPassword(), "管理员");
        admin.setUserType(1);
        cache.put(admin.getId(), admin);
        
        Role role1 = new Role();
        role1.setId(cache.generateId());
        role1.setName("管理员");
        role1.setCode("ADMIN");
        role1.setDescription("系统管理员角色");
        cache.put(role1.getId(), role1);
        
        Role role2 = new Role();
        role2.setId(cache.generateId());
        role2.setName("普通用户");
        role2.setCode("USER");
        role2.setDescription("普通用户角色");
        cache.put(role2.getId(), role2);
        
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
        
        Menu menu1 = new Menu();
        menu1.setId(cache.generateId());
        menu1.setName("系统管理");
        menu1.setCode("SYSTEM_MGMT");
        menu1.setPath("/system");
        menu1.setIcon("setting");
        menu1.setParentId(0L);
        menu1.setLevel(1);
        menu1.setSortOrder(1);
        menu1.setStatus(1);
        cache.put(menu1.getId(), menu1);
        
        Menu menu2 = new Menu();
        menu2.setId(cache.generateId());
        menu2.setName("用户管理");
        menu2.setCode("USER_MGMT");
        menu2.setPath("/system/user");
        menu2.setIcon("user");
        menu2.setParentId(menu1.getId());
        menu2.setLevel(2);
        menu2.setSortOrder(1);
        menu2.setStatus(1);
        cache.put(menu2.getId(), menu2);
        
        Menu menu3 = new Menu();
        menu3.setId(cache.generateId());
        menu3.setName("角色管理");
        menu3.setCode("ROLE_MGMT");
        menu3.setPath("/system/role");
        menu3.setIcon("role");
        menu3.setParentId(menu1.getId());
        menu3.setLevel(2);
        menu3.setSortOrder(2);
        menu3.setStatus(1);
        cache.put(menu3.getId(), menu3);
        
        Menu menu4 = new Menu();
        menu4.setId(cache.generateId());
        menu4.setName("菜单管理");
        menu4.setCode("MENU_MGMT");
        menu4.setPath("/system/menu");
        menu4.setIcon("menu");
        menu4.setParentId(menu1.getId());
        menu4.setLevel(2);
        menu4.setSortOrder(3);
        menu4.setStatus(1);
        cache.put(menu4.getId(), menu4);
        
        Menu menu5 = new Menu();
        menu5.setId(cache.generateId());
        menu5.setName("组织机构");
        menu5.setCode("ORG_MGMT");
        menu5.setPath("/system/org");
        menu5.setIcon("org");
        menu5.setParentId(menu1.getId());
        menu5.setLevel(2);
        menu5.setSortOrder(4);
        menu5.setStatus(1);
        cache.put(menu5.getId(), menu5);
        
        Menu menu6 = new Menu();
        menu6.setId(cache.generateId());
        menu6.setName("API权限");
        menu6.setCode("API_MGMT");
        menu6.setPath("/system/api");
        menu6.setIcon("api");
        menu6.setParentId(menu1.getId());
        menu6.setLevel(2);
        menu6.setSortOrder(5);
        menu6.setStatus(1);
        cache.put(menu6.getId(), menu6);
        
        ApiPermission api1 = new ApiPermission();
        api1.setId(cache.generateId());
        api1.setName("用户列表");
        api1.setCode("user:list");
        api1.setUrl("/api/user/list");
        api1.setMethod("GET");
        api1.setMenuId(menu2.getId());
        api1.setStatus(1);
        cache.put(api1.getId(), api1);
        
        ApiPermission api2 = new ApiPermission();
        api2.setId(cache.generateId());
        api2.setName("用户保存");
        api2.setCode("user:save");
        api2.setUrl("/api/user/save");
        api2.setMethod("POST");
        api2.setMenuId(menu2.getId());
        api2.setStatus(1);
        cache.put(api2.getId(), api2);
        
        ApiPermission api3 = new ApiPermission();
        api3.setId(cache.generateId());
        api3.setName("用户删除");
        api3.setCode("user:delete");
        api3.setUrl("/api/user/delete");
        api3.setMethod("DELETE");
        api3.setMenuId(menu2.getId());
        api3.setStatus(1);
        cache.put(api3.getId(), api3);
        
        ApiPermission api4 = new ApiPermission();
        api4.setId(cache.generateId());
        api4.setName("角色列表");
        api4.setCode("role:list");
        api4.setUrl("/api/role/list");
        api4.setMethod("GET");
        api4.setMenuId(menu3.getId());
        api4.setStatus(1);
        cache.put(api4.getId(), api4);
        
        ApiPermission api5 = new ApiPermission();
        api5.setId(cache.generateId());
        api5.setName("角色保存");
        api5.setCode("role:save");
        api5.setUrl("/api/role/save");
        api5.setMethod("POST");
        api5.setMenuId(menu3.getId());
        api5.setStatus(1);
        cache.put(api5.getId(), api5);
        
        ApiPermission api6 = new ApiPermission();
        api6.setId(cache.generateId());
        api6.setName("菜单列表");
        api6.setCode("menu:list");
        api6.setUrl("/api/menu/list");
        api6.setMethod("GET");
        api6.setMenuId(menu4.getId());
        api6.setStatus(1);
        cache.put(api6.getId(), api6);
        
        ApiPermission api7 = new ApiPermission();
        api7.setId(cache.generateId());
        api7.setName("组织机构列表");
        api7.setCode("org:list");
        api7.setUrl("/api/org/list");
        api7.setMethod("GET");
        api7.setMenuId(menu5.getId());
        api7.setStatus(1);
        cache.put(api7.getId(), api7);
        
        RoleMenu rm1 = new RoleMenu();
        rm1.setId(cache.generateId());
        rm1.setRoleId(role1.getId());
        rm1.setMenuId(menu1.getId());
        cache.put(rm1.getId(), rm1);
        
        RoleMenu rm2 = new RoleMenu();
        rm2.setId(cache.generateId());
        rm2.setRoleId(role1.getId());
        rm2.setMenuId(menu2.getId());
        cache.put(rm2.getId(), rm2);
        
        RoleMenu rm3 = new RoleMenu();
        rm3.setId(cache.generateId());
        rm3.setRoleId(role1.getId());
        rm3.setMenuId(menu3.getId());
        cache.put(rm3.getId(), rm3);
        
        RoleMenu rm4 = new RoleMenu();
        rm4.setId(cache.generateId());
        rm4.setRoleId(role1.getId());
        rm4.setMenuId(menu4.getId());
        cache.put(rm4.getId(), rm4);
        
        RoleMenu rm5 = new RoleMenu();
        rm5.setId(cache.generateId());
        rm5.setRoleId(role1.getId());
        rm5.setMenuId(menu5.getId());
        cache.put(rm5.getId(), rm5);
        
        RoleMenu rm6 = new RoleMenu();
        rm6.setId(cache.generateId());
        rm6.setRoleId(role1.getId());
        rm6.setMenuId(menu6.getId());
        cache.put(rm6.getId(), rm6);
        
        RoleApi ra1 = new RoleApi();
        ra1.setId(cache.generateId());
        ra1.setRoleId(role1.getId());
        ra1.setApiId(api1.getId());
        cache.put(ra1.getId(), ra1);
        
        RoleApi ra2 = new RoleApi();
        ra2.setId(cache.generateId());
        ra2.setRoleId(role1.getId());
        ra2.setApiId(api2.getId());
        cache.put(ra2.getId(), ra2);
        
        RoleApi ra3 = new RoleApi();
        ra3.setId(cache.generateId());
        ra3.setRoleId(role1.getId());
        ra3.setApiId(api3.getId());
        cache.put(ra3.getId(), ra3);
        
        RoleApi ra4 = new RoleApi();
        ra4.setId(cache.generateId());
        ra4.setRoleId(role1.getId());
        ra4.setApiId(api4.getId());
        cache.put(ra4.getId(), ra4);
        
        RoleApi ra5 = new RoleApi();
        ra5.setId(cache.generateId());
        ra5.setRoleId(role1.getId());
        ra5.setApiId(api5.getId());
        cache.put(ra5.getId(), ra5);
        
        RoleApi ra6 = new RoleApi();
        ra6.setId(cache.generateId());
        ra6.setRoleId(role1.getId());
        ra6.setApiId(api6.getId());
        cache.put(ra6.getId(), ra6);
        
        RoleApi ra7 = new RoleApi();
        ra7.setId(cache.generateId());
        ra7.setRoleId(role1.getId());
        ra7.setApiId(api7.getId());
        cache.put(ra7.getId(), ra7);
        
        RoleOrganization ro1 = new RoleOrganization();
        ro1.setId(cache.generateId());
        ro1.setRoleId(role1.getId());
        ro1.setOrganizationId(org1.getId());
        cache.put(ro1.getId(), ro1);
        
        RoleOrganization ro2 = new RoleOrganization();
        ro2.setId(cache.generateId());
        ro2.setRoleId(role1.getId());
        ro2.setOrganizationId(org2.getId());
        cache.put(ro2.getId(), ro2);
        
        RoleOrganization ro3 = new RoleOrganization();
        ro3.setId(cache.generateId());
        ro3.setRoleId(role2.getId());
        ro3.setOrganizationId(org3.getId());
        cache.put(ro3.getId(), ro3);
        
        System.out.println("初始化数据完成，管理员账号: " + adminConfig.getUsername());
    }
}
