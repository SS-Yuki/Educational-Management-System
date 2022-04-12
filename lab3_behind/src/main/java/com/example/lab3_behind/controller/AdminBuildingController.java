package com.example.lab3_behind.controller;

import com.example.lab3_behind.common.*;
import com.example.lab3_behind.domain.Major;
import com.example.lab3_behind.domain.Teacher;
import com.example.lab3_behind.domain.TeachingBuilding;
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

    @RequestMapping("/updateBuildingInfo")
    public Result updateBuilding(@RequestBody BuildingUpdatingData buildingUpdatingData){
        try{
            teachingAffairsService.updateTeachingBuilding(buildingUpdatingData.getOldBuildingName(),buildingUpdatingData.getNewBuildingName());
            return Result.succ(null);
        }catch (Exception e){
            //e.printStackTrace();
            return Result.fail(701,e.getMessage());
        }
    }
    @PostMapping("/deleteBuilding")
    public Result deleteBuilding(@RequestBody String buildingName){
        try{
            teachingAffairsService.deleteTeachingBuilding(buildingName);
            return Result.succ(null);
        }
        catch (Exception e){
            //e.printStackTrace();
            return Result.fail(702,e.getMessage());
        }
    }
    @RequestMapping("/findMajorPage")
    public Result findBuildingPage(@RequestBody PageSearchData pageSearchData){
        Map<String,Object> map = new HashMap<>();
        Page<TeachingBuilding> teachingBuildingPage = teachingAffairsService.findAPageTeachingBuilding(pageSearchData.getPageNum(),pageSearchData.getPageSize(), pageSearchData.getSearch());
        List<BuildingContents> buildingContents = BuildingContents.getContents(teachingBuildingPage.getContent());
        map.put("records",buildingContents);
        map.put("total",teachingBuildingPage.getTotalElements());
        return Result.succ(map);
    }


    @RequestMapping("/allBuildings")
    public Result allMajors(){
        Map<String,Object> map = new HashMap<>();
        //map.put("buildings",teachingAffairsService.);
        return Result.succ(map);
    }
}
