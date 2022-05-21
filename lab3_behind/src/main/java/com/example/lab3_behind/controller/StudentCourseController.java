package com.example.lab3_behind.controller;


import com.example.lab3_behind.common.*;
import com.example.lab3_behind.domain.Course;
import com.example.lab3_behind.domain.dto.YearSemesterPair;
import com.example.lab3_behind.domain.resp.Result;
import com.example.lab3_behind.service.CourseService;
import com.example.lab3_behind.service.StudentService;
import com.example.lab3_behind.utils.EnumTool;
import com.example.lab3_behind.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentCourseController {
    @Autowired
    StudentService studentService;
    @Autowired
    CourseService courseService;


    @RequestMapping("getMyCourseInSemester")
    public Result getMyCourseInSemester(@RequestBody YearSemesterPair yearSemesterPair, HttpServletRequest request){
        String token = request.getHeader("token");
        JwtUserData jwtUserData = JwtUtil.getToken(token);
        String number = jwtUserData.getNumber().replace("\"", "");
        try {
            List<Course> courses = studentService.findCourseInSemester(number,
                    EnumTool.transSchoolYear(yearSemesterPair.getYear()),
                    EnumTool.transSemester(yearSemesterPair.getSemester()));
            List<CourseContent> courseContents = CourseContent.getContent(courses);
            return Result.succ(courseContents);
        } catch (Exception e){
            //e.printStackTrace();
            return Result.fail(833,e.getMessage());
        }
    }

    @RequestMapping("getMyCourseTableInSemester")
    public Result getMyCourseTableInSemester(@RequestBody YearSemesterPair yearSemesterPair,HttpServletRequest request){
        String token = request.getHeader("token");
        JwtUserData jwtUserData = JwtUtil.getToken(token);
        String number = jwtUserData.getNumber().replace("\"", "");
        try {
            List<List<CourseNameString>> courses = studentService.getClassScheduleInSemester(number,
                    EnumTool.transSchoolYear(yearSemesterPair.getYear()),
                    EnumTool.transSemester(yearSemesterPair.getSemester()));
            return Result.succ(courses);
        } catch (Exception e){
            //e.printStackTrace();
            return Result.fail(833,e.getMessage());
        }
    }

    @RequestMapping("allFinishedCourse")
    public Result allFinishedCourse(HttpServletRequest request){
        String token = request.getHeader("token");
        JwtUserData jwtUserData = JwtUtil.getToken(token);
        String number = jwtUserData.getNumber().replace("\"", "");
        try {
            List<Course> courses = courseService.findALLStudiedCourseOfStudent(number);
            List<CourseContent> courseContents = CourseContent.getContent(courses);
            return Result.succ(courseContents);
        } catch (Exception e){
            //e.printStackTrace();
            return Result.fail(833,e.getMessage());
        }
    }
}
