package com.example.lab3_behind.interceptor;

import com.example.lab3_behind.utils.JwtUtil;
import com.example.lab3_behind.vo.JwtUserData;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TeacherInterceptor implements HandlerInterceptor {
    /**
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        JwtUserData jwtUserData = JwtUtil.getToken(token);
        boolean permission = false;
        if(jwtUserData.getRole().equals("teacher")){
            permission = true;
        } else if(jwtUserData.getRole().equals("student")){
            response.sendRedirect("/user/student");
        } else if(jwtUserData.getRole().equals("admin")){
            response.sendRedirect("/user/admin");
        } else {
            response.sendRedirect("/login");
        }
        return permission;
    }
}
