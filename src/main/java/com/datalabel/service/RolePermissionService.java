package com.datalabel.service;

import com.datalabel.entity.*;
import com.datalabel.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RolePermissionService {
    
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    
    @Autowired
    private RoleApiMapper roleApiMapper;
    
    @Autowired
    private RoleOrganizationMapper roleOrganizationMapper;
    
    @Autowired
    private MenuMapper menuMapper;
    
    @Autowired
    private ApiPermissionMapper apiPermissionMapper;
    
    @Autowired
    private OrganizationMapper organizationMapper;
    
    public boolean bindRoleMenu(Long roleId, Long menuId) {
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setRoleId(roleId);
        roleMenu.setMenuId(menuId);
        return roleMenuMapper.insert(roleMenu) > 0;
    }
    
    public boolean unbindRoleMenu(Long roleId, Long menuId) {
        return roleMenuMapper.deleteByRoleIdAndMenuId(roleId, menuId) > 0;
    }
    
    public boolean bindRoleMenus(Long roleId, List<Long> menuIds) {
        roleMenuMapper.deleteByRoleId(roleId);
        if (menuIds != null && !menuIds.isEmpty()) {
            for (Long menuId : menuIds) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menuId);
                roleMenuMapper.insert(roleMenu);
            }
        }
        return true;
    }
    
    public List<Menu> findMenusByRoleId(Long roleId) {
        List<Long> menuIds = roleMenuMapper.findMenuIdsByRoleId(roleId);
        if (menuIds.isEmpty()) {
            return new ArrayList<>();
        }
        return menuMapper.findByIds(menuIds);
    }
    
    public List<Long> findMenuIdsByRoleId(Long roleId) {
        return roleMenuMapper.findMenuIdsByRoleId(roleId);
    }
    
    public boolean bindRoleApi(Long roleId, Long apiId) {
        RoleApi roleApi = new RoleApi();
        roleApi.setRoleId(roleId);
        roleApi.setApiId(apiId);
        return roleApiMapper.insert(roleApi) > 0;
    }
    
    public boolean unbindRoleApi(Long roleId, Long apiId) {
        return roleApiMapper.deleteByRoleIdAndApiId(roleId, apiId) > 0;
    }
    
    public boolean bindRoleApis(Long roleId, List<Long> apiIds) {
        roleApiMapper.deleteByRoleId(roleId);
        if (apiIds != null && !apiIds.isEmpty()) {
            for (Long apiId : apiIds) {
                RoleApi roleApi = new RoleApi();
                roleApi.setRoleId(roleId);
                roleApi.setApiId(apiId);
                roleApiMapper.insert(roleApi);
            }
        }
        return true;
    }
    
    public List<ApiPermission> findApisByRoleId(Long roleId) {
        List<Long> apiIds = roleApiMapper.findApiIdsByRoleId(roleId);
        if (apiIds.isEmpty()) {
            return new ArrayList<>();
        }
        return apiPermissionMapper.findByIds(apiIds);
    }
    
    public List<Long> findApiIdsByRoleId(Long roleId) {
        return roleApiMapper.findApiIdsByRoleId(roleId);
    }
    
    public boolean hasApiPermission(Long roleId, String apiCode) {
        ApiPermission api = apiPermissionMapper.findByCode(apiCode);
        if (api == null) {
            return false;
        }
        List<Long> apiIds = roleApiMapper.findApiIdsByRoleId(roleId);
        return apiIds.contains(api.getId());
    }
    
    public boolean bindRoleOrganization(Long roleId, Long organizationId) {
        RoleOrganization roleOrganization = new RoleOrganization();
        roleOrganization.setRoleId(roleId);
        roleOrganization.setOrganizationId(organizationId);
        return roleOrganizationMapper.insert(roleOrganization) > 0;
    }
    
    public boolean unbindRoleOrganization(Long roleId, Long organizationId) {
        return roleOrganizationMapper.deleteByRoleIdAndOrganizationId(roleId, organizationId) > 0;
    }
    
    public boolean bindRoleOrganizations(Long roleId, List<Long> organizationIds) {
        roleOrganizationMapper.deleteByRoleId(roleId);
        if (organizationIds != null && !organizationIds.isEmpty()) {
            for (Long organizationId : organizationIds) {
                RoleOrganization roleOrganization = new RoleOrganization();
                roleOrganization.setRoleId(roleId);
                roleOrganization.setOrganizationId(organizationId);
                roleOrganizationMapper.insert(roleOrganization);
            }
        }
        return true;
    }
    
    public List<Organization> findOrganizationsByRoleId(Long roleId) {
        List<Long> orgIds = roleOrganizationMapper.findOrganizationIdsByRoleId(roleId);
        if (orgIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<Organization> result = new ArrayList<>();
        for (Long orgId : orgIds) {
            Organization org = organizationMapper.findById(orgId);
            if (org != null) {
                result.add(org);
            }
        }
        return result;
    }
    
    public List<Long> findOrganizationIdsByRoleId(Long roleId) {
        return roleOrganizationMapper.findOrganizationIdsByRoleId(roleId);
    }
}
