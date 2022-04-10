package com.example.lab3_behind.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.lab3_behind.domain.resp.Result;
import com.example.lab3_behind.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
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
        try{
            if(token==null || token.isEmpty())throw new Exception("无token");
            JwtUtil.verify(token);
            String json = new ObjectMapper().writeValueAsString(Result.fail(650,"重复登录"));
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(json);
            return false;
        }catch (Exception e) {
            return true;
        }
    }
}
