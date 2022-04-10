package com.example.lab3_behind.interceptor;

import com.example.lab3_behind.domain.resp.Result;
import com.example.lab3_behind.utils.JwtUtil;
import com.example.lab3_behind.common.JwtUserData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminInterceptor implements HandlerInterceptor {
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
        Result result;
        JwtUserData jwtUserData = JwtUtil.getToken(token);
        boolean permission = false;
        if(jwtUserData.getRole().equals("admin")){
            permission = true;
        } else {
            result = Result.fail(630,"无访问权限");
            String json = new ObjectMapper().writeValueAsString(result);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(json);
        }
        return permission;
    }
}
