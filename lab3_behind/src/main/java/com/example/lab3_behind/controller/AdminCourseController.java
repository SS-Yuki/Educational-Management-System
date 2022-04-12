package com.example.lab3_behind.controller;


import com.example.lab3_behind.common.BuildingContents;
import com.example.lab3_behind.common.BuildingUpdatingData;
import com.example.lab3_behind.common.PageSearchData;
import com.example.lab3_behind.domain.Course;
import com.example.lab3_behind.domain.TeachingBuilding;
import com.example.lab3_behind.domain.dto.CourseApplyingData;
import com.example.lab3_behind.domain.resp.Result;
import com.example.lab3_behind.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminCourseController {
    @Autowired
    CourseService courseService;

    @PostMapping("/addCourse")
    public Result addCourse(@RequestBody CourseApplyingData courseApplyingData){
        try {
            courseService.insertCourse(courseApplyingData);
            return Result.succ(null);
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(730,e.getMessage());
        }
    }
    @RequestMapping("/updateCourseInfo")
    public Result updateCourseInfo(@RequestBody CourseApplyingData courseApplyingData){
        try{
            courseService.updateCourse(courseApplyingData);
            return Result.succ(null);
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(731,e.getMessage());
        }
    }
    @PostMapping("/deleteCourse")
    public Result deleteCourse(@RequestBody Map<String,Object> map){
        try{
            Integer courseId = (Integer) map.get("id");
            courseService.deleteCourse(courseId);
            return Result.succ(null);
        }
        catch (Exception e){
            e.printStackTrace();
            return Result.fail(732,e.getMessage());
        }
    }
    @RequestMapping("/findCoursePage")
    public Result findCoursePage(@RequestBody PageSearchData pageSearchData){
        Map<String,Object> map = new HashMap<>();
        Page<Course> coursePage = courseService.findAPageCourse(pageSearchData.getPageNum(),pageSearchData.getPageSize(), pageSearchData.getSearch());
        map.put("records",coursePage.getContent());
        map.put("total",coursePage.getTotalElements());
        return Result.succ(map);
    }

}
