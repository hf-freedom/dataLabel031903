package com.datalabel.mapper;

import com.datalabel.cache.LocalCache;
import com.datalabel.entity.RoleOrganization;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RoleOrganizationMapper {
    
    private final LocalCache cache = LocalCache.getInstance();
    
    public RoleOrganization findById(Long id) {
        return (RoleOrganization) cache.get(id);
    }
    
    public List<RoleOrganization> findAll() {
        return cache.getAll(RoleOrganization.class);
    }
    
    public List<RoleOrganization> findByRoleId(Long roleId) {
        return cache.getAll(RoleOrganization.class).stream()
                .filter(ro -> roleId.equals(ro.getRoleId()))
                .collect(Collectors.toList());
    }
    
    public List<RoleOrganization> findByOrganizationId(Long organizationId) {
        return cache.getAll(RoleOrganization.class).stream()
                .filter(ro -> organizationId.equals(ro.getOrganizationId()))
                .collect(Collectors.toList());
    }
    
    public List<Long> findOrganizationIdsByRoleId(Long roleId) {
        return findByRoleId(roleId).stream()
                .map(RoleOrganization::getOrganizationId)
                .collect(Collectors.toList());
    }
    
    public int insert(RoleOrganization roleOrganization) {
        if (roleOrganization.getId() == null) {
            roleOrganization.setId(cache.generateId());
        }
        cache.put(roleOrganization.getId(), roleOrganization);
        return 1;
    }
    
    public int deleteById(Long id) {
        cache.remove(id);
        return 1;
    }
    
    public int deleteByRoleId(Long roleId) {
        List<RoleOrganization> list = findByRoleId(roleId);
        for (RoleOrganization ro : list) {
            cache.remove(ro.getId());
        }
        return list.size();
    }
    
    public int deleteByOrganizationId(Long organizationId) {
        List<RoleOrganization> list = findByOrganizationId(organizationId);
        for (RoleOrganization ro : list) {
            cache.remove(ro.getId());
        }
        return list.size();
    }
    
    public int deleteByRoleIdAndOrganizationId(Long roleId, Long organizationId) {
        List<RoleOrganization> list = cache.getAll(RoleOrganization.class).stream()
                .filter(ro -> roleId.equals(ro.getRoleId()) && organizationId.equals(ro.getOrganizationId()))
                .collect(Collectors.toList());
        for (RoleOrganization ro : list) {
            cache.remove(ro.getId());
        }
        return list.size();
    }
}
