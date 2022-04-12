package com.example.lab3_behind.controller;

import com.example.lab3_behind.common.BuildingUpdatingData;
import com.example.lab3_behind.common.MajorContents;
import com.example.lab3_behind.common.MajorDeleteData;
import com.example.lab3_behind.common.PageSearchData;
import com.example.lab3_behind.domain.Major;
import com.example.lab3_behind.domain.dto.MajorUpdatingData;
import com.example.lab3_behind.domain.resp.Result;
import com.example.lab3_behind.service.TeachingAffairsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminBuildingController {
    @Autowired
    TeachingAffairsService teachingAffairsService;

    @RequestMapping("addBuilding")
    public Result addBuilding(@RequestBody String buildingName){
        Map<String,Object> map = new HashMap<>();
        try{
            teachingAffairsService.insertTeachingBuilding(buildingName);
            map.put("buildingName",buildingName);
            return Result.succ(map);
        }catch (Exception e){
            return Result.fail(700,e.getMessage());
        }
    }

//    @RequestMapping("/updateBuildingInfo")
//    public Result updateMajor(@RequestBody BuildingUpdatingData buildingUpdatingData){
//        Map<String,Object> map = new HashMap<>();
//        try{
//            //teachingAffairsService.
//        }catch (Exception e){
//            //e.printStackTrace();
//            return Result.fail(683,e.getMessage());
//        }
//        return Result.succ(map);
//    }
//    @PostMapping("/deleteMajor")
//    public Result deleteMajor(@RequestBody MajorDeleteData majorDeleteData){
//        String majorName = majorDeleteData.getMajorName();
//        String schoolName = majorDeleteData.getSchoolName();
//        try{
//            Major major = schoolService.deleteMajor(majorName,schoolName);
//            return Result.succ(majorName);
//        }
//        catch (Exception e){
//            //e.printStackTrace();
//            return Result.fail(696,e.getMessage());
//        }
//    }
//    @RequestMapping("/findMajorPage")
//    public Result findMajorPage(@RequestBody PageSearchData pageSearchData){
//        Map<String,Object> map = new HashMap<>();
//        Page<Major> majorPage = schoolService.findAPageMajor(pageSearchData.getPageNum(),pageSearchData.getPageSize(), pageSearchData.getSearch());
//        List<MajorContents> majorContents = MajorContents.getContents(majorPage.getContent());
//        map.put("records",majorContents);
//        map.put("total",majorPage.getTotalElements());
//        return Result.succ(map);
//    }


    @RequestMapping("/allBuildings")
    public Result allMajors(){
        Map<String,Object> map = new HashMap<>();
        //map.put("buildings",teachingAffairsService.);
        return Result.succ(map);
    }
}
