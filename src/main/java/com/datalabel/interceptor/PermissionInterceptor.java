package com.datalabel.interceptor;

import com.datalabel.annotation.RequiresPermission;
import com.datalabel.common.Result;
import com.datalabel.entity.User;
import com.datalabel.service.PermissionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class PermissionInterceptor implements HandlerInterceptor {
    
    @Autowired
    private PermissionService permissionService;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequiresPermission requiresPermission = handlerMethod.getMethodAnnotation(RequiresPermission.class);
        
        if (requiresPermission == null) {
            return true;
        }
        
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser == null) {
            writeErrorResponse(response, 401, "未登录");
            return false;
        }
        
        String permissionCode = requiresPermission.value();
        if (!permissionService.hasPermission(currentUser, permissionCode)) {
            writeErrorResponse(response, 403, "没有权限");
            return false;
        }
        
        return true;
    }
    
    private void writeErrorResponse(HttpServletResponse response, int code, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(Result.error(code, message)));
        writer.flush();
    }
}