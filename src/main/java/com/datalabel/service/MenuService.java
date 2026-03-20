package com.datalabel.service;

import com.datalabel.entity.Menu;
import com.datalabel.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {
    
    @Autowired
    private MenuMapper menuMapper;
    
    public Menu findById(Long id) {
        return menuMapper.findById(id);
    }
    
    public List<Menu> findAll() {
        return menuMapper.findAll();
    }
    
    public List<Menu> findByParentId(Long parentId) {
        return menuMapper.findByParentId(parentId);
    }
    
    public List<Menu> findByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        return menuMapper.findByIds(ids);
    }
    
    public List<Menu> buildMenuTree() {
        List<Menu> allMenus = menuMapper.findAll();
        return buildTree(allMenus, 0L);
    }
    
    private List<Menu> buildTree(List<Menu> menus, Long parentId) {
        List<Menu> result = new ArrayList<>();
        for (Menu menu : menus) {
            if (parentId.equals(menu.getParentId())) {
                result.add(menu);
            }
        }
        result.sort((m1, m2) -> {
            int s1 = m1.getSortOrder() != null ? m1.getSortOrder() : 0;
            int s2 = m2.getSortOrder() != null ? m2.getSortOrder() : 0;
            return s1 - s2;
        });
        return result;
    }
    
    public boolean save(Menu menu) {
        if (menu.getId() == null) {
            return menuMapper.insert(menu) > 0;
        } else {
            return menuMapper.update(menu) > 0;
        }
    }
    
    public boolean update(Menu menu) {
        return menuMapper.update(menu) > 0;
    }
    
    public boolean deleteById(Long id) {
        return menuMapper.deleteById(id) > 0;
    }
}
