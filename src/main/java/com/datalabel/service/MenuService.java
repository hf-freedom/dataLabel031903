package com.datalabel.service;

import com.datalabel.entity.Menu;
import com.datalabel.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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