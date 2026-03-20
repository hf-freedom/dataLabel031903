package com.datalabel.service;

import com.datalabel.entity.Menu;
import com.datalabel.entity.Role;
import com.datalabel.entity.RoleMenu;
import com.datalabel.mapper.MenuMapper;
import com.datalabel.mapper.RoleMapper;
import com.datalabel.mapper.RoleMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {
    
    @Autowired
    private RoleMapper roleMapper;
    
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    
    @Autowired
    private MenuMapper menuMapper;
    
    public Role findById(Long id) {
        return roleMapper.findById(id);
    }
    
    public List<Role> findAll() {
        return roleMapper.findAll();
    }
    
    public List<Role> findByOrganizationId(Long orgId) {
        return roleMapper.findAll().stream()
                .filter(r -> orgId.equals(r.getOrganizationId()))
                .collect(Collectors.toList());
    }
    
    public boolean save(Role role) {
        if (role.getId() == null) {
            return roleMapper.insert(role) > 0;
        } else {
            return roleMapper.update(role) > 0;
        }
    }
    
    public boolean update(Role role) {
        return roleMapper.update(role) > 0;
    }
    
    public boolean deleteById(Long id) {
        return roleMapper.deleteById(id) > 0;
    }
    
    public List<Menu> getMenusByRoleId(Long roleId) {
        List<RoleMenu> roleMenus = roleMenuMapper.findByRoleId(roleId);
        return roleMenus.stream()
                .map(rm -> menuMapper.findById(rm.getMenuId()))
                .filter(m -> m != null)
                .collect(Collectors.toList());
    }
    
    public boolean bindMenus(Long roleId, List<Long> menuIds) {
        roleMenuMapper.deleteByRoleId(roleId);
        for (Long menuId : menuIds) {
            RoleMenu roleMenu = new RoleMenu(roleId, menuId);
            roleMenuMapper.insert(roleMenu);
        }
        return true;
    }
    
    public boolean bindOrganization(Long roleId, Long orgId) {
        Role role = roleMapper.findById(roleId);
        if (role != null) {
            role.setOrganizationId(orgId);
            return roleMapper.update(role) > 0;
        }
        return false;
    }
}
