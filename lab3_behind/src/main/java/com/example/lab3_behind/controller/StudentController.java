package com.example.lab3_behind.controller;


import com.example.lab3_behind.common.ChangePasswordData;
import com.example.lab3_behind.common.JwtUserData;
import com.example.lab3_behind.common.MaintainInfoData;
import com.example.lab3_behind.domain.Student;
import com.example.lab3_behind.domain.dto.RevisableDataForUser;
import com.example.lab3_behind.domain.resp.Result;
import com.example.lab3_behind.service.StudentService;
import com.example.lab3_behind.service.UserAccountService;
import com.example.lab3_behind.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    UserAccountService userAccountService;

    @PostMapping("/information")
    public Result information(@RequestBody String number,HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        try {
            String token = request.getHeader("token");
            JwtUserData jwtUserData = JwtUtil.getToken(token);
            if(number.equals(jwtUserData.getNumber())){
                throw new Exception("请求与账号不匹配");
            }
            Student student = studentService.getByStuNumber(number);
            map.put("role","学生");
            map.put("number",student.getStuNumber());
            map.put("name",student.getName());
            map.put("idNum",student.getIdNum());
            map.put("phoneNum",student.getPhoneNum());
            map.put("email",student.getEmail());
            map.put("school",student.getSchool());
            map.put("major",student.getMajor());
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(660,e.getMessage());
        }
        return Result.succ(map);
    }

    @PostMapping("/changePassword")
    public Result changePassword(@RequestBody ChangePasswordData changePasswordData, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        String number = changePasswordData.getNumber();
        String oldPassword = changePasswordData.getOldPassword();
        String newPassword = changePasswordData.getNewPassword();
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
    public Result maintainInfo(@RequestBody MaintainInfoData maintainInfoData, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        String number=maintainInfoData.getNumber();
        String phoneNum = maintainInfoData.getPhoneNum();
        String email = maintainInfoData.getEmail();
        try {
            String token = request.getHeader("token");
            JwtUserData jwtUserData = JwtUtil.getToken(token);
            if(number.equals(jwtUserData.getNumber())){
                throw new Exception("请求与账号不匹配");
            }

            studentService.maintainStudentInfo(new RevisableDataForUser(phoneNum,email),number);
            Student student = studentService.getByStuNumber(number);

            map.put("number",student.getStuNumber());
            map.put("name",student.getName());
            map.put("idNum",student.getIdNum());
            map.put("phoneNum",student.getPhoneNum());
            map.put("enail",student.getEmail());
            map.put("school",student.getSchool());
            map.put("major",student.getMajor());
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(680,e.getMessage());
        }
        return Result.succ(map);
    }

}
