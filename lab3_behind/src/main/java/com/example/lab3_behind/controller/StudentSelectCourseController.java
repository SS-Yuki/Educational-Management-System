package com.example.lab3_behind.controller;

import com.example.lab3_behind.common.JwtUserData;
import com.example.lab3_behind.common.PageSearchData;
import com.example.lab3_behind.domain.Course;
import com.example.lab3_behind.domain.resp.Result;
import com.example.lab3_behind.service.AuthorityService;
import com.example.lab3_behind.service.CourseService;
import com.example.lab3_behind.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentSelectCourseController {
    @Autowired
    AuthorityService authorityService;
    @Autowired
    CourseService courseService;

    @RequestMapping("/findCoursePage")
    public Result findCoursePage(@RequestBody PageSearchData pageSearchData,
                                 HttpServletRequest request){
        String token = request.getHeader("token");
        JwtUserData jwtUserData = JwtUtil.getToken(token);
        String number = jwtUserData.getNumber().replace("\"", "");
        try{
            Boolean open = authorityService.checkCourseSelectingAuthority();
            if(!open){
                throw new Exception("当前选课未开放");
            }
            Map<String,Object> map = new HashMap<>();
            Page<Course> coursePage = courseService.findAPageCourseOfTeacher(pageSearchData.getPageNum(),pageSearchData.getPageSize(), pageSearchData.getSearch(),number);
            map.put("records",coursePage.getContent());
            map.put("total",coursePage.getTotalElements());
            return Result.succ(map);
        }catch (Exception e){
            //e.printStackTrace();
            return Result.fail(830,e.getMessage());
        }
    }
}
