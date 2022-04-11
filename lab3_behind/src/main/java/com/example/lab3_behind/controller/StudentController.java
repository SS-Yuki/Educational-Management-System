package com.example.lab3_behind.controller;


import com.example.lab3_behind.common.JwtUserData;
import com.example.lab3_behind.domain.Student;
import com.example.lab3_behind.domain.UserAccount;
import com.example.lab3_behind.domain.dto.LoginUserData;
import com.example.lab3_behind.domain.resp.Result;
import com.example.lab3_behind.repository.UserAccountRepository;
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
    public Result information(@RequestParam("number") String number,HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        try {
            String token = request.getHeader("token");
            JwtUserData jwtUserData = JwtUtil.getToken(token);
            if(number.equals(jwtUserData.getNumber())){
                throw new Exception("请求与账号不匹配");
            }
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
            return Result.fail(660,e.getMessage());
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
            userAccountService.changePassword();
            map.put("number",number);
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(670,e.getMessage());
        }
        return Result.succ(map);
    }

}
