package com.example.lab3_behind.controller;


import com.example.lab3_behind.domain.Student;
import com.example.lab3_behind.domain.Teacher;
import com.example.lab3_behind.domain.UserAccount;
import com.example.lab3_behind.domain.dto.UserEnteringData;
import com.example.lab3_behind.service.StudentService;
import com.example.lab3_behind.service.TeacherService;
import com.example.lab3_behind.service.UserAccountService;
import com.example.lab3_behind.utils.JwtUtil;
import com.example.lab3_behind.utils.RedisUtil;
import com.example.lab3_behind.common.JwtUserData;
import com.example.lab3_behind.domain.dto.LoginUserData;
import com.example.lab3_behind.domain.resp.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserAccountController {
    @Autowired
    UserAccountService userAccountService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;


    @PostMapping("/login")
    public Result login(@RequestBody LoginUserData user, HttpServletRequest request){



        Map<String,Object> map = new HashMap<>();
        try {
            UserAccount userAccount = userAccountService.login(user);
            JwtUserData jwtUserData = JwtUserData.accountToJwt(userAccount);
            map.put("number",user.getNumber());
            map.put("token",JwtUtil.createToken(jwtUserData));
            map.put("initLogin",userAccount.isFirstLogin());
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(620,e.getMessage());
        }
        return Result.succ(map);
    }
    @PostMapping("/logout")
    public Result logout(@RequestBody String token){
        Map<String,String> map = new HashMap<>();
        try {
            JwtUtil.verify(token);
            JwtUserData jwtUserData = JwtUtil.getToken(token);
            RedisUtil.delete(token);
            map.put("number",jwtUserData.getNumber());
        }catch (Exception e){
            return Result.fail(621,e.getMessage());
        }
        return Result.succ(map);
    }
    @PostMapping("/register")
    public Result register(@RequestBody UserEnteringData userEnteringData){

        Map<String,Object> map = new HashMap<>();
        try {
            if(userEnteringData.getRole().equals("学生")){
                userEnteringData.setRole("student");
                Student student = studentService.insertStudent(userEnteringData);
                map.put("number",student.getStuNumber());
                map.put("name",student.getName());
                map.put("role","student");
            }
            else if(userEnteringData.getRole().equals("教师")){
                userEnteringData.setRole("teacher");
                Teacher teacher = teacherService.insertTeacher(userEnteringData);
                map.put("number",teacher.getJobNumber());
                map.put("name",teacher.getName());
                map.put("role","teacher");
            }
            else throw new Exception("注册身份错误");
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(620,e.getMessage());
        }
        return Result.succ(map);
    }
}
