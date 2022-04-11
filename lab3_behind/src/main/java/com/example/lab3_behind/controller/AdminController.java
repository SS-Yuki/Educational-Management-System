package com.example.lab3_behind.controller;


import com.example.lab3_behind.domain.dto.SchoolAddingData;
import com.example.lab3_behind.domain.dto.SchoolUpdatingData;
import com.example.lab3_behind.domain.resp.Result;
import com.example.lab3_behind.service.SchoolService;
import com.example.lab3_behind.service.StudentService;
import com.example.lab3_behind.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    SchoolService schoolService;

    @PostMapping("/allMajors")
    public Result allMajors(){
        Map<String,Object> map = new HashMap<>();
        map.put("schools",schoolService.getAllSchoolAndMajors());
        return Result.succ(map);
    }

    @PostMapping("/addSchool")
    public Result addSchool(@RequestParam("schoolName") String schoolName,
                            @RequestParam("introduction") String introduction){
        Map<String,Object> map = new HashMap<>();
        try{
            schoolService.insertSchool(new SchoolAddingData(schoolName,introduction));
        }catch (Exception e){
            return Result.fail(680,e.getMessage());
        }
        return Result.succ(map);
    }
    @PostMapping("/updateSchool")
    public Result updateSchool(@RequestParam("oldName") String oldName,
                               @RequestParam("newName") String newName,
                               @RequestParam("introduction") String introduction){
        Map<String,Object> map = new HashMap<>();
        try{
            schoolService.updateSchool(new SchoolUpdatingData(oldName,newName,introduction));
        }catch (Exception e){
            return Result.fail(681,e.getMessage());
        }
        return Result.succ(map);
    }
}
