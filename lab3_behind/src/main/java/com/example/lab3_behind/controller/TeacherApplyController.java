package com.example.lab3_behind.controller;

import com.example.lab3_behind.common.CourseApplyingType;
import com.example.lab3_behind.common.JwtUserData;
import com.example.lab3_behind.common.PageSearchData;
import com.example.lab3_behind.common.TeacherCourseApplyingData;
import com.example.lab3_behind.domain.Course;
import com.example.lab3_behind.domain.Teacher;
import com.example.lab3_behind.domain.dto.CourseApplyingData;
import com.example.lab3_behind.domain.resp.Result;
import com.example.lab3_behind.service.CourseService;
import com.example.lab3_behind.service.TeacherService;
import com.example.lab3_behind.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;



@RestController
@RequestMapping("/teacher")
public class TeacherApplyController {
    @Autowired
    CourseService courseService;
    @Autowired
    TeacherService teacherService;


    @PostMapping("/addCourse")
    public Result addCourse(@RequestBody TeacherCourseApplyingData teacherCourseApplyingData,
                            HttpServletRequest request){
        try {
            String token = request.getHeader("token");
            JwtUserData jwtUserData = JwtUtil.getToken(token);
            String number = jwtUserData.getNumber().replace("\"", "");
            Teacher teacher = teacherService.getByJobNumber(number);
            CourseApplyingData courseApplyingData = teacherCourseApplyingData.getCourseApply();
            courseApplyingData.setTeacherNum(teacher.getJobNumber());
            courseApplyingData.setApplicant(teacher.getName());
            courseApplyingData.setMajor(teacher.getMajor());
            courseApplyingData.setSchool(teacher.getSchool());
            courseService.pushCourseApplying(courseApplyingData, CourseApplyingType.Publish);
            return Result.succ(null);
        }catch (Exception e){
            //e.printStackTrace();
            return Result.fail(740,e.getMessage());
        }
    }

    @RequestMapping("/updateCourseInfo")
    public Result updateCourseInfo(@RequestBody TeacherCourseApplyingData teacherCourseApplyingData,
                                   HttpServletRequest request){
        try{
            String token = request.getHeader("token");
            JwtUserData jwtUserData = JwtUtil.getToken(token);
            String number = jwtUserData.getNumber().replace("\"", "");
            Teacher teacher = teacherService.getByJobNumber(number);
            CourseApplyingData courseApplyingData = teacherCourseApplyingData.getCourseApply();
            courseApplyingData.setTeacherNum(teacher.getJobNumber());
            courseApplyingData.setApplicant(teacher.getName());
            courseApplyingData.setMajor(teacher.getMajor());
            courseApplyingData.setSchool(teacher.getSchool());
            courseService.pushCourseApplying(courseApplyingData, CourseApplyingType.Change);
            return Result.succ(null);
        }catch (Exception e){
            //e.printStackTrace();
            return Result.fail(731,e.getMessage());
        }
    }
    @PostMapping("/deleteCourse")
    public Result deleteCourse(@RequestBody TeacherCourseApplyingData teacherCourseApplyingData,
                               HttpServletRequest request){
        try{
            String token = request.getHeader("token");
            JwtUserData jwtUserData = JwtUtil.getToken(token);
            String number = jwtUserData.getNumber().replace("\"", "");
            Teacher teacher = teacherService.getByJobNumber(number);
            CourseApplyingData courseApplyingData = teacherCourseApplyingData.getCourseApply();
            courseApplyingData.setTeacherNum(teacher.getJobNumber());
            courseApplyingData.setApplicant(teacher.getName());
            courseApplyingData.setMajor(teacher.getMajor());
            courseApplyingData.setSchool(teacher.getSchool());
            courseService.pushCourseApplying(courseApplyingData,CourseApplyingType.Delete);
            return Result.succ(null);
        }
        catch (Exception e){
            //e.printStackTrace();
            return Result.fail(732,e.getMessage());
        }
    }
    @RequestMapping("/findCoursePage")
    public Result findCoursePage(@RequestBody PageSearchData pageSearchData,
                                 HttpServletRequest request){
        String token = request.getHeader("token");
        JwtUserData jwtUserData = JwtUtil.getToken(token);
        String number = jwtUserData.getNumber().replace("\"", "");
        try{
            Map<String,Object> map = new HashMap<>();
            Page<Course> coursePage = courseService.findAPageCourseOfTeacher(pageSearchData.getPageNum(),pageSearchData.getPageSize(), pageSearchData.getSearch(),number);
            map.put("records",coursePage.getContent());
            map.put("total",coursePage.getTotalElements());
            return Result.succ(map);
        }catch (Exception e){
            //e.printStackTrace();
            return Result.fail(770,e.getMessage());
        }

    }




}
