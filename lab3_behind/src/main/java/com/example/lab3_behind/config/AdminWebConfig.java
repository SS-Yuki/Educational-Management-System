package com.example.lab3_behind.config;

import com.example.lab3_behind.interceptor.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class AdminWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/**");

//        registry.addInterceptor(new LoginInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/login");
//
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/user/admin/**", "/user/admin");

        registry.addInterceptor(new StudentInterceptor())
                .addPathPatterns("/user/student/**", "/user/student");

        registry.addInterceptor(new TeacherInterceptor())
                .addPathPatterns("/user/teacher/**", "/user/teacher");
    }

}
