package com.example.lab3_behind.controller;


import com.example.lab3_behind.common.JwtUserData;
import com.example.lab3_behind.domain.Student;
import com.example.lab3_behind.domain.Teacher;
import com.example.lab3_behind.domain.dto.RevisableDataForUser;
import com.example.lab3_behind.domain.resp.Result;
import com.example.lab3_behind.service.StudentService;
import com.example.lab3_behind.service.TeacherService;
import com.example.lab3_behind.service.UserAccountService;
import com.example.lab3_behind.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/information")
    public Result information(@RequestParam("number") String number, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        try {
            String token = request.getHeader("token");
            JwtUserData jwtUserData = JwtUtil.getToken(token);
            if(number.equals(jwtUserData.getNumber())){
                throw new Exception("请求与账号不匹配");
            }
            Teacher teacher = teacherService.getByJobNumber(number);
            map.put("number",teacher.getJobNumber());
            map.put("name",teacher.getName());
            map.put("idNum",teacher.getIdNum());
            map.put("phoneNum",teacher.getPhoneNum());
            map.put("enail",teacher.getEmail());
            map.put("school",teacher.getSchool());
            map.put("major",teacher.getMajor());
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(661,e.getMessage());
        }
        return Result.succ(map);
    }

    @PostMapping("/changePassword")
    public Result changePassword(@RequestParam("number") String number,
                                 @RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        try {
            String token = request.getHeader("token");
            JwtUserData jwtUserData = JwtUtil.getToken(token);
            if(number.equals(jwtUserData.getNumber())){
                throw new Exception("请求与账号不匹配");
            }
            userAccountService.changePassword(number,oldPassword,newPassword);
            map.put("number",number);
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(670,e.getMessage());
        }
        return Result.succ(map);
    }

    @PostMapping("/maintainInfo")
    public Result maintainInfo(@RequestParam("number") String number,
                               @RequestParam("phoneNum") String phoneNum,
                               @RequestParam("email") String email,
                               HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        try {
            String token = request.getHeader("token");
            JwtUserData jwtUserData = JwtUtil.getToken(token);
            if(number.equals(jwtUserData.getNumber())){
                throw new Exception("请求与账号不匹配");
            }
            teacherService.maintainTeacherInfo(new RevisableDataForUser(phoneNum,email),number);
            Teacher teacher = teacherService.getByJobNumber(number);
            map.put("number",teacher.getJobNumber());
            map.put("name",teacher.getName());
            map.put("idNum",teacher.getIdNum());
            map.put("phoneNum",teacher.getPhoneNum());
            map.put("enail",teacher.getEmail());
            map.put("school",teacher.getSchool());
            map.put("major",teacher.getMajor());
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(680,e.getMessage());
        }
        return Result.succ(map);
    }

}
