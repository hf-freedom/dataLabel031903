package com.datalabel.service;

import com.datalabel.entity.ApiPermission;
import com.datalabel.mapper.ApiPermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiPermissionService {
    
    @Autowired
    private ApiPermissionMapper apiPermissionMapper;
    
    public ApiPermission findById(Long id) {
        return apiPermissionMapper.findById(id);
    }
    
    public List<ApiPermission> findAll() {
        return apiPermissionMapper.findAll();
    }
    
    public List<ApiPermission> findByMenuId(Long menuId) {
        return apiPermissionMapper.findByMenuId(menuId);
    }
    
    public ApiPermission findByPathAndMethod(String path, String method) {
        return apiPermissionMapper.findByPathAndMethod(path, method);
    }
    
    public boolean save(ApiPermission apiPermission) {
        if (apiPermission.getId() == null) {
            return apiPermissionMapper.insert(apiPermission) > 0;
        } else {
            return apiPermissionMapper.update(apiPermission) > 0;
        }
    }
    
    public boolean update(ApiPermission apiPermission) {
        return apiPermissionMapper.update(apiPermission) > 0;
    }
    
    public boolean deleteById(Long id) {
        return apiPermissionMapper.deleteById(id) > 0;
    }
}