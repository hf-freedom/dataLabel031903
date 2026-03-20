package com.datalabel.config;

import com.datalabel.annotation.RequirePermission;
import com.datalabel.common.Result;
import com.datalabel.entity.User;
import com.datalabel.service.RolePermissionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.lang.reflect.Method;

@Component
public class PermissionInterceptor implements HandlerInterceptor {
    
    @Autowired
    private RolePermissionService rolePermissionService;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        
        RequirePermission requirePermission = method.getAnnotation(RequirePermission.class);
        if (requirePermission == null) {
            requirePermission = handlerMethod.getBeanType().getAnnotation(RequirePermission.class);
        }
        
        if (requirePermission == null) {
            return true;
        }
        
        String permissionCode = requirePermission.value();
        
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser == null) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PrintWriter writer = response.getWriter();
            writer.write(objectMapper.writeValueAsString(Result.error(401, "未登录")));
            writer.flush();
            return false;
        }
        
        if (currentUser.getUserType() == 1) {
            return true;
        }
        
        Long roleId = currentUser.getRoleId();
        if (roleId == null) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            PrintWriter writer = response.getWriter();
            writer.write(objectMapper.writeValueAsString(Result.error(403, "没有权限访问该资源")));
            writer.flush();
            return false;
        }
        
        boolean hasPermission = rolePermissionService.hasApiPermission(roleId, permissionCode);
        if (!hasPermission) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            PrintWriter writer = response.getWriter();
            writer.write(objectMapper.writeValueAsString(Result.error(403, "没有权限访问该资源")));
            writer.flush();
            return false;
        }
        
        return true;
    }
}
