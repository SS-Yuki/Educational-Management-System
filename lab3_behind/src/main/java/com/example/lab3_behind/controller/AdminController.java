package com.example.lab3_behind.controller;


import com.example.lab3_behind.common.PageSearchData;
import com.example.lab3_behind.common.SchoolContents;
import com.example.lab3_behind.domain.Major;
import com.example.lab3_behind.domain.School;
import com.example.lab3_behind.domain.dto.*;
import com.example.lab3_behind.domain.resp.Result;
import com.example.lab3_behind.domain.vo.StudentUpdatingData;
import com.example.lab3_behind.domain.vo.TeacherUpdatingData;
import com.example.lab3_behind.service.SchoolService;
import com.example.lab3_behind.service.StudentService;
import com.example.lab3_behind.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;





    @RequestMapping("/register")
    public Result register(@RequestBody UserEnteringData userEnteringData){
        Map<String,Object> map = new HashMap<>();
        try{
            if(userEnteringData.getRole().equals("student")){
                studentService.insertStudent(userEnteringData);
            }else if(userEnteringData.getRole().equals("teacher")){
                teacherService.insertTeacher(userEnteringData);
            }else throw new Exception("注册身份错误");
        }catch (Exception e){
            
            e.printStackTrace();
            return Result.fail(683,e.getMessage());
        }
        return Result.succ(map);
    }

    @RequestMapping("/updateStudentInfo")
    public Result updateStudentInfo(@RequestBody StudentUpdatingData studentUpdatingData){
        Map<String,Object> map = new HashMap<>();
        try{
            String stuNumebr = studentUpdatingData.getStuNumebr();
            studentService.updateStudentInfo(new RevisableDataForAdmin(studentUpdatingData),stuNumebr);
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(683,e.getMessage());
        }
        return Result.succ(map);
    }

    @RequestMapping("/updateTeacherInfo")
    public Result updateTeacherInfo(@RequestBody TeacherUpdatingData teacherUpdatingData){
        Map<String,Object> map = new HashMap<>();
        try{
            String jobNumber = teacherUpdatingData.getJobNumber();
            studentService.updateStudentInfo(new RevisableDataForAdmin(teacherUpdatingData),jobNumber);
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(684,e.getMessage());
        }
        return Result.succ(map);
    }




}
