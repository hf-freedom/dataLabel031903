package com.datalabel.service;

import com.datalabel.entity.ApiPermission;
import com.datalabel.entity.RoleApi;
import com.datalabel.entity.User;
import com.datalabel.mapper.ApiPermissionMapper;
import com.datalabel.mapper.RoleApiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionService {
    
    @Autowired
    private RoleApiMapper roleApiMapper;
    
    @Autowired
    private ApiPermissionMapper apiPermissionMapper;
    
    public boolean hasPermission(User user, String permissionCode) {
        if (user == null) {
            return false;
        }
        if (user.getUserType() != null && user.getUserType() == 1) {
            return true;
        }
        if (user.getRoleId() == null) {
            return false;
        }
        List<RoleApi> roleApis = roleApiMapper.findByRoleId(user.getRoleId());
        for (RoleApi ra : roleApis) {
            ApiPermission api = apiPermissionMapper.findById(ra.getApiId());
            if (api != null && permissionCode.equals(api.getCode())) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasPermission(User user, String path, String method) {
        if (user == null) {
            return false;
        }
        if (user.getUserType() != null && user.getUserType() == 1) {
            return true;
        }
        if (user.getRoleId() == null) {
            return false;
        }
        ApiPermission api = apiPermissionMapper.findByPathAndMethod(path, method);
        if (api == null) {
            return true;
        }
        List<RoleApi> roleApis = roleApiMapper.findByRoleId(user.getRoleId());
        for (RoleApi ra : roleApis) {
            if (api.getId().equals(ra.getApiId())) {
                return true;
            }
        }
        return false;
    }
    
    public List<ApiPermission> getApiPermissionsByRoleId(Long roleId) {
        List<RoleApi> roleApis = roleApiMapper.findByRoleId(roleId);
        return roleApis.stream()
                .map(ra -> apiPermissionMapper.findById(ra.getApiId()))
                .filter(a -> a != null)
                .collect(Collectors.toList());
    }
    
    public boolean bindApis(Long roleId, List<Long> apiIds) {
        roleApiMapper.deleteByRoleId(roleId);
        for (Long apiId : apiIds) {
            RoleApi roleApi = new RoleApi(roleId, apiId);
            roleApiMapper.insert(roleApi);
        }
        return true;
    }
}