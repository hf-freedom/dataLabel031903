package com.datalabel.mapper;

import com.datalabel.cache.LocalCache;
import com.datalabel.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MenuMapper {
    
    private final LocalCache cache = LocalCache.getInstance();
    
    public Menu findById(Long id) {
        return (Menu) cache.get(id);
    }
    
    public Menu findByCode(String code) {
        return cache.getAll(Menu.class).stream()
                .filter(m -> code.equals(m.getCode()))
                .findFirst()
                .orElse(null);
    }
    
    public List<Menu> findAll() {
        return cache.getAll(Menu.class);
    }
    
    public List<Menu> findByParentId(Long parentId) {
        return cache.getAll(Menu.class).stream()
                .filter(m -> parentId.equals(m.getParentId()))
                .sorted((m1, m2) -> {
                    int s1 = m1.getSortOrder() != null ? m1.getSortOrder() : 0;
                    int s2 = m2.getSortOrder() != null ? m2.getSortOrder() : 0;
                    return s1 - s2;
                })
                .collect(Collectors.toList());
    }
    
    public List<Menu> findByIds(List<Long> ids) {
        return cache.getAll(Menu.class).stream()
                .filter(m -> ids.contains(m.getId()))
                .sorted((m1, m2) -> {
                    int s1 = m1.getSortOrder() != null ? m1.getSortOrder() : 0;
                    int s2 = m2.getSortOrder() != null ? m2.getSortOrder() : 0;
                    return s1 - s2;
                })
                .collect(Collectors.toList());
    }
    
    public int insert(Menu menu) {
        if (menu.getId() == null) {
            menu.setId(cache.generateId());
        }
        cache.put(menu.getId(), menu);
        return 1;
    }
    
    public int update(Menu menu) {
        if (cache.containsKey(menu.getId())) {
            cache.put(menu.getId(), menu);
            return 1;
        }
        return 0;
    }
    
    public int deleteById(Long id) {
        cache.remove(id);
        return 1;
    }
}
