package com.example.lab3_behind.controller;


import com.example.lab3_behind.common.MajorContents;
import com.example.lab3_behind.common.MajorDeleteData;
import com.example.lab3_behind.common.PageSearchData;
import com.example.lab3_behind.common.SchoolContents;
import com.example.lab3_behind.domain.Major;
import com.example.lab3_behind.domain.School;
import com.example.lab3_behind.domain.dto.MajorAddingData;
import com.example.lab3_behind.domain.dto.MajorUpdatingData;
import com.example.lab3_behind.domain.dto.SchoolAddingData;
import com.example.lab3_behind.domain.dto.SchoolUpdatingData;
import com.example.lab3_behind.domain.resp.Result;
import com.example.lab3_behind.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminSchoolController {
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

    @PostMapping("/allSchool")
    public Result allSchool(){
        Map<String,Object> map = new HashMap<>();
        try{
            List<String> schoolNames = schoolService.getAllSchool();
            map.put("schools",schoolNames);
            return Result.succ(map);
        }
        catch (Exception e){
            //e.printStackTrace();
            return Result.fail(691,e.getMessage());
        }
    }



    @RequestMapping("/addMajor")
    public Result addMajor(@RequestBody MajorAddingData majorAddingData){
        Map<String,Object> map = new HashMap<>();
        try{
            Major major = schoolService.insertMajor(majorAddingData);
            map.put("majorName",major.getName());
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(682,e.getMessage());
        }
        return Result.succ(map);
    }
    @RequestMapping("/updateMajorInfo")
    public Result updateMajor(@RequestBody MajorUpdatingData majorUpdatingData){
        Map<String,Object> map = new HashMap<>();
        try{
            schoolService.updateMajor(majorUpdatingData);
        }catch (Exception e){
            //e.printStackTrace();
            return Result.fail(683,e.getMessage());
        }
        return Result.succ(map);
    }
    @PostMapping("/deleteMajor")
    public Result deleteMajor(@RequestBody MajorDeleteData majorDeleteData){
        String majorName = majorDeleteData.getMajorName();
        String schoolName = majorDeleteData.getSchoolName();
        try{
            Major major = schoolService.deleteMajor(majorName,schoolName);
            return Result.succ(majorName);
        }
        catch (Exception e){
            //e.printStackTrace();
            return Result.fail(696,e.getMessage());
        }
    }
    @RequestMapping("/findMajorPage")
    public Result findMajorPage(@RequestBody PageSearchData pageSearchData){
        Map<String,Object> map = new HashMap<>();
        Page<Major> majorPage = schoolService.findAPageMajor(pageSearchData.getPageNum(),pageSearchData.getPageSize(), pageSearchData.getSearch());
        List<MajorContents> majorContents = MajorContents.getContents(majorPage.getContent());
        map.put("records",majorContents);
        map.put("total",majorPage.getTotalElements());
        return Result.succ(map);
    }
}
