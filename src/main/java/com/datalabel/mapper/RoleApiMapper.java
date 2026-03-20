package com.datalabel.mapper;

import com.datalabel.cache.LocalCache;
import com.datalabel.entity.RoleApi;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RoleApiMapper {
    
    private final LocalCache cache = LocalCache.getInstance();
    
    public List<RoleApi> findByRoleId(Long roleId) {
        return cache.getAll(RoleApi.class).stream()
                .filter(ra -> roleId.equals(ra.getRoleId()))
                .collect(Collectors.toList());
    }
    
    public List<RoleApi> findByApiId(Long apiId) {
        return cache.getAll(RoleApi.class).stream()
                .filter(ra -> apiId.equals(ra.getApiId()))
                .collect(Collectors.toList());
    }
    
    public int insert(RoleApi roleApi) {
        if (roleApi.getId() == null) {
            roleApi.setId(cache.generateId());
        }
        cache.put(roleApi.getId(), roleApi);
        return 1;
    }
    
    public int deleteByRoleId(Long roleId) {
        List<RoleApi> list = findByRoleId(roleId);
        for (RoleApi ra : list) {
            cache.remove(ra.getId());
        }
        return list.size();
    }
    
    public int deleteByApiId(Long apiId) {
        List<RoleApi> list = findByApiId(apiId);
        for (RoleApi ra : list) {
            cache.remove(ra.getId());
        }
        return list.size();
    }
}