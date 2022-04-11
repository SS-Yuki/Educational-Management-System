package com.example.lab3_behind.controller;


import com.example.lab3_behind.service.StudentService;
import com.example.lab3_behind.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;

//    @PostMapping("/allMajors")
//    public Result allMajors(){
//        Map<String,Object> map = new HashMap<>();
//
//        map.put("schools","11");
//    }
}
