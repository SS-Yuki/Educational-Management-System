package com.example.lab3_behind.controller;


import com.example.lab3_behind.common.*;
import com.example.lab3_behind.domain.Student;
import com.example.lab3_behind.domain.Teacher;
import com.example.lab3_behind.domain.dto.*;
import com.example.lab3_behind.domain.resp.Result;
import com.example.lab3_behind.domain.vo.StudentUpdatingData;
import com.example.lab3_behind.domain.vo.TeacherUpdatingData;
import com.example.lab3_behind.service.StudentService;
import com.example.lab3_behind.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;


    @RequestMapping("/register")
    public Result register(@RequestBody UserEnteringData userEnteringData){
        try{
            if(userEnteringData.getRole().equals("student")){
                studentService.insertStudent(userEnteringData);
            }else if(userEnteringData.getRole().equals("teacher")){
                teacherService.insertTeacher(userEnteringData);
            }else throw new Exception("注册身份错误");
        }catch (Exception e){
            //e.printStackTrace();
            return Result.fail(683,e.getMessage());
        }
        return Result.succ(null);
    }

    @RequestMapping("/csvRegister")
    public Result csvRegister(@RequestBody List<UserEnteringData> userEnteringDatas){
        int failNum=0;
        for(UserEnteringData userEnteringData:userEnteringDatas){
            try{
                if(userEnteringData.getRole().equals("student")){
                    studentService.insertStudent(userEnteringData);
                }else if(userEnteringData.getRole().equals("teacher")) {
                    teacherService.insertTeacher(userEnteringData);
                }else throw new Exception("注册身份错误");
            } catch (Exception e){
                //e.printStackTrace();
                failNum++;
            }
        }
        if(failNum==0) return Result.succ(null);
        else return Result.fail(880,"部分信息不符合要求");
    }

    @RequestMapping("/updateStudentInfo")
    public Result updateStudentInfo(@RequestBody StudentUpdatingData studentUpdatingData){
        Map<String,Object> map = new HashMap<>();
        try{
            String stuNumber = studentUpdatingData.getStuNumber();
            studentService.updateStudentInfo(new RevisableDataForAdmin(studentUpdatingData),stuNumber);
        }catch (Exception e){
            //e.printStackTrace();
            return Result.fail(683,e.getMessage());
        }
        return Result.succ(map);
    }

    @RequestMapping("/updateTeacherInfo")
    public Result updateTeacherInfo(@RequestBody TeacherUpdatingData teacherUpdatingData){
        Map<String,Object> map = new HashMap<>();
        try{
            String jobNumber = teacherUpdatingData.getJobNumber();
            teacherService.updateTeacherInfo(new RevisableDataForAdmin(teacherUpdatingData),jobNumber);
        }catch (Exception e){
            //e.printStackTrace();
            return Result.fail(684,e.getMessage());
        }
        return Result.succ(map);
    }

    @RequestMapping("/findStudentPage")
    public Result findStudentPage(@RequestBody PageSearchData pageSearchData){
        Map<String,Object> map = new HashMap<>();
        Page<Student> studentPage = studentService.findAPageStudent(pageSearchData.getPageNum(),pageSearchData.getPageSize(), pageSearchData.getSearch());
        map.put("records", StudentContents.getContents(studentPage.getContent()));
        map.put("total",studentPage.getTotalElements());
        return Result.succ(map);
    }

    @RequestMapping("/findTeacherPage")
    public Result findTeacherPage(@RequestBody PageSearchData pageSearchData){
        Map<String,Object> map = new HashMap<>();
        Page<Teacher> teacherPage = teacherService.findAPageTeacher(pageSearchData.getPageNum(),pageSearchData.getPageSize(), pageSearchData.getSearch());
        map.put("records", TeacherContents.getContents(teacherPage.getContent()));
        map.put("total",teacherPage.getTotalElements());
        return Result.succ(map);
    }

}
