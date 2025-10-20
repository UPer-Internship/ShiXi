package com.ShiXi.common.utils;

import com.ShiXi.user.common.domin.dto.UserDTO;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IdentificationInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取当前用户
        UserDTO user = UserHolder.getUser();
        if (user == null) {
            // 如果没有用户信息，直接放行，由登录拦截器处理
            return true;
        }
        
        // 获取用户身份标识
        Integer identification = user.getIdentification();
        if (identification == null) {
            // 如果没有身份标识，拒绝访问
            response.setStatus(403);
            return false;
        }
        
        // 获取请求路径
        String requestURI = request.getRequestURI();
        
        // 根据用户身份和请求路径判断是否允许访问
        if (requestURI.startsWith("/user/identification/admin")) {
            // 管理员专用接口，只有管理员可以访问
            if (identification != 6) {
                response.setStatus(403);
                return false;
            }
        }
        
        // 游客身份限制：无法投递简历
        if (identification   == 0) {
            // 限制游客访问投递简历相关接口
            if (requestURI.startsWith("/position/application/apply")) {
                response.setStatus(403);
                return false;
            }
        }
        
        return true;
    }
}
