package com.example.lab3_behind.controller;

import com.example.lab3_behind.common.*;
import com.example.lab3_behind.common.forDomain.CourseApplyingType;
import com.example.lab3_behind.domain.Course;
import com.example.lab3_behind.domain.CourseApplying;
import com.example.lab3_behind.domain.Teacher;
import com.example.lab3_behind.domain.dto.CourseApplyingData;
import com.example.lab3_behind.domain.resp.Result;
import com.example.lab3_behind.service.CourseService;
import com.example.lab3_behind.service.TeacherService;
import com.example.lab3_behind.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/teacher")
public class TeacherApplyController {
    @Autowired
    CourseService courseService;
    @Autowired
    TeacherService teacherService;
    Logger logger = LoggerFactory.getLogger(getClass());

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
            courseApplyingData.setMajor(teacher.getMajor().getName());
            courseApplyingData.setSchool(teacher.getSchool().getName());
            courseService.pushCourseApplying(courseApplyingData, CourseApplyingType.Publish);
            return Result.succ(null);
        }catch (Exception e){
            e.printStackTrace();
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
            courseApplyingData.setMajor(teacher.getMajor().getName());
            courseApplyingData.setSchool(teacher.getSchool().getName());
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
            courseApplyingData.setMajor(teacher.getMajor().getName());
            courseApplyingData.setSchool(teacher.getSchool().getName());
            courseService.pushCourseApplying(courseApplyingData,CourseApplyingType.Delete);
            return Result.succ(null);
        }
        catch (Exception e){
            //e.printStackTrace();
            return Result.fail(732,e.getMessage());
        }
    }
    @PostMapping("/findApplyPage")
    public Result findApplyPage(@RequestBody PageSearchData pageSearchData,HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        try {
            String token = request.getHeader("token");
            JwtUserData jwtUserData = JwtUtil.getToken(token);
            String number = jwtUserData.getNumber().replace("\"", "");
            Page<CourseApplying> courseApplyingPage = courseService.findCourseApplyingOfTeacher(pageSearchData.getPageNum(),pageSearchData.getPageSize(), pageSearchData.getSearch(),number);
            List<ApplyContent> applyContents = ApplyContent.getContents(courseApplyingPage.getContent());
            map.put("records",applyContents);
            map.put("total",courseApplyingPage.getTotalElements());
            return Result.succ(map);
        }catch (Exception e){
            //e.printStackTrace();
            return Result.fail(842,e.getMessage());
        }
    }

    @PostMapping("/findCoursePage")
    public Result findCoursePage(@RequestBody PageSearchWithYearAndSemester pageSearchData,HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        try {
            String token = request.getHeader("token");
            JwtUserData jwtUserData = JwtUtil.getToken(token);
            String number = jwtUserData.getNumber().replace("\"", "");
            Page<Course> coursePage = courseService.findAPageCourseOfTeacher(pageSearchData.getPageNum(),pageSearchData.getPageSize(), pageSearchData.getSearch(),number);
            List<CourseContent> courseContents = CourseContent.getContent(coursePage.getContent());
            map.put("records",courseContents);
            map.put("total",coursePage.getTotalElements());
            return Result.succ(map);
        }catch (Exception e){
            //logger.trace("----findCoursePage捕获到了异常----");
            //logger.trace(e.getMessage());
            return Result.fail(843,e.getMessage());
        }
    }

}
