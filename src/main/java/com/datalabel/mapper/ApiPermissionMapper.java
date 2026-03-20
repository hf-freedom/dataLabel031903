package com.datalabel.mapper;

import com.datalabel.cache.LocalCache;
import com.datalabel.entity.ApiPermission;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ApiPermissionMapper {
    
    private final LocalCache cache = LocalCache.getInstance();
    
    public ApiPermission findById(Long id) {
        return (ApiPermission) cache.get(id);
    }
    
    public List<ApiPermission> findAll() {
        return cache.getAll(ApiPermission.class);
    }
    
    public List<ApiPermission> findByMenuId(Long menuId) {
        return cache.getAll(ApiPermission.class).stream()
                .filter(a -> menuId.equals(a.getMenuId()))
                .collect(Collectors.toList());
    }
    
    public ApiPermission findByPathAndMethod(String path, String method) {
        return cache.getAll(ApiPermission.class).stream()
                .filter(a -> path.equals(a.getPath()) && method.equals(a.getMethod()))
                .findFirst()
                .orElse(null);
    }
    
    public int insert(ApiPermission apiPermission) {
        if (apiPermission.getId() == null) {
            apiPermission.setId(cache.generateId());
        }
        cache.put(apiPermission.getId(), apiPermission);
        return 1;
    }
    
    public int update(ApiPermission apiPermission) {
        if (cache.containsKey(apiPermission.getId())) {
            cache.put(apiPermission.getId(), apiPermission);
            return 1;
        }
        return 0;
    }
    
    public int deleteById(Long id) {
        cache.remove(id);
        return 1;
    }
}