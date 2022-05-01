package com.example.lab3_behind.controller;


import com.example.lab3_behind.common.*;
import com.example.lab3_behind.domain.Course;
import com.example.lab3_behind.domain.Student;
import com.example.lab3_behind.domain.Teacher;
import com.example.lab3_behind.domain.dto.CourseApplyingData;
import com.example.lab3_behind.domain.dto.RevisableDataForUser;
import com.example.lab3_behind.domain.resp.Result;
import com.example.lab3_behind.service.CourseService;
import com.example.lab3_behind.service.StudentService;
import com.example.lab3_behind.service.TeacherService;
import com.example.lab3_behind.service.UserAccountService;
import com.example.lab3_behind.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    UserAccountService userAccountService;
    @Autowired
    CourseService courseService;

    @PostMapping("/information")
    public Result information(@RequestBody String number, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        try {
            String token = request.getHeader("token");
            JwtUserData jwtUserData = JwtUtil.getToken(token);
            if(number.equals(jwtUserData.getNumber())){
                throw new Exception("请求与账号不匹配");
            }
            String ss =  jwtUserData.getNumber();
            Teacher teacher = teacherService.getByJobNumber(number);
            map.put("role","教师");
            map.put("number",teacher.getJobNumber());
            map.put("name",teacher.getName());
            map.put("idNum",teacher.getIdNum());
            map.put("phoneNum",teacher.getPhoneNum());
            map.put("email",teacher.getEmail());
            map.put("school",teacher.getSchool().getName());
            map.put("major",teacher.getMajor().getName());
            if(teacher.getRegisterTime()!=null){
                map.put("registerTime",teacher.getRegisterTime().toString());
            }
        }catch (Exception e){
            //e.printStackTrace();
            return Result.fail(661,e.getMessage());
        }
        return Result.succ(map);
    }

    @PostMapping("/maintainInfo")
    public Result maintainInfo(@RequestBody MaintainInfoData maintainInfoData, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        String number=maintainInfoData.getNumber();
        String phoneNum = maintainInfoData.getPhoneNum();
        String email = maintainInfoData.getEmail();
        try {
            String token = request.getHeader("token");
            JwtUserData jwtUserData = JwtUtil.getToken(token);
            if(maintainInfoData.getNumber().equals(jwtUserData.getNumber())){
                throw new Exception("请求与账号不匹配");
            }
            teacherService.maintainTeacherInfo(new RevisableDataForUser(phoneNum,email),number);
            Teacher teacher = teacherService.getByJobNumber(number);
            map.put("number",teacher.getJobNumber());
            map.put("name",teacher.getName());
            map.put("idNum",teacher.getIdNum());
            map.put("phoneNum",teacher.getPhoneNum());
            map.put("email",teacher.getEmail());
            map.put("school",teacher.getSchool().getName());
            map.put("major",teacher.getMajor().getName());
        }catch (Exception e){
            //e.printStackTrace();
            return Result.fail(680,e.getMessage());
        }
        return Result.succ(map);
    }


}
