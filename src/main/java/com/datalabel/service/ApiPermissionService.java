package com.datalabel.service;

import com.datalabel.entity.ApiPermission;
import com.datalabel.mapper.ApiPermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApiPermissionService {
    
    @Autowired
    private ApiPermissionMapper apiPermissionMapper;
    
    public ApiPermission findById(Long id) {
        return apiPermissionMapper.findById(id);
    }
    
    public ApiPermission findByCode(String code) {
        return apiPermissionMapper.findByCode(code);
    }
    
    public List<ApiPermission> findAll() {
        return apiPermissionMapper.findAll();
    }
    
    public List<ApiPermission> findByMenuId(Long menuId) {
        return apiPermissionMapper.findByMenuId(menuId);
    }
    
    public List<ApiPermission> findByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        return apiPermissionMapper.findByIds(ids);
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
    
    public boolean deleteByMenuId(Long menuId) {
        return apiPermissionMapper.deleteByMenuId(menuId) > 0;
    }
}
