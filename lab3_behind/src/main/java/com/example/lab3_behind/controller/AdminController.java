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
    @Autowired
    SchoolService schoolService;

    @RequestMapping("/allMajors")
    public Result allMajors(){
        Map<String,Object> map = new HashMap<>();
        map.put("schools",schoolService.getAllSchoolAndMajors());
        return Result.succ(map);
    }

    @RequestMapping("/addSchool")
    public Result addSchool(@RequestBody SchoolAddingData schoolAddingData){
        Map<String,Object> map = new HashMap<>();
        try{
            School school = schoolService.insertSchool(schoolAddingData);
            map.put("schoolName",school.getName());
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(680,e.getMessage());
        }
        return Result.succ(map);
    }
    @RequestMapping("/updateSchoolInfo")
    public Result updateSchool(@RequestBody SchoolUpdatingData schoolUpdatingData){
        Map<String,Object> map = new HashMap<>();
        try{
            schoolService.updateSchool(schoolUpdatingData);
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(681,e.getMessage());
        }
        return Result.succ(map);
    }
    @RequestMapping("/addMajor")
    public Result addMajor(@RequestBody MajorAddingData majorAddingData){
        Map<String,Object> map = new HashMap<>();
        try{
            Major major = schoolService.insertMajor(majorAddingData);
            map.put("majorName",major);
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(682,e.getMessage());
        }
        return Result.succ(map);
    }

    @RequestMapping("/updateMajor")
    public Result updateMajor(@RequestBody MajorUpdatingData majorUpdatingData){
        Map<String,Object> map = new HashMap<>();
        try{
            schoolService.updateMajor(majorUpdatingData);
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(683,e.getMessage());
        }
        return Result.succ(map);
    }

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

    @RequestMapping("/findSchoolPage")
    public Result findSchoolPage(@RequestBody PageSearchData pageSearchData){
        Map<String,Object> map = new HashMap<>();
        Page<School> schoolPage = schoolService.findAPageSchool(pageSearchData.getPageNum(),pageSearchData.getPageSize(), pageSearchData.getSearch());
        List<SchoolContents> schoolContents = SchoolContents.getContents(schoolPage.getContent());
        map.put("records",schoolContents);
        map.put("total",schoolPage.getTotalElements());
        return Result.succ(map);
    }


    @PostMapping("/deleteSchool")
    public Result deleteSchool(@RequestBody String schoolName){
        try{
            School school = schoolService.deleteSchool(schoolName);
        }
        catch (Exception e){
            e.printStackTrace();
            return Result.fail(690,e.getMessage());
        }
        return Result.succ(null);
    }


}
