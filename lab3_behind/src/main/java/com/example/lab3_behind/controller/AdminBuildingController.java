package com.example.lab3_behind.controller;

import com.example.lab3_behind.common.*;
import com.example.lab3_behind.domain.Classroom;
import com.example.lab3_behind.domain.TeachingBuilding;
import com.example.lab3_behind.domain.dto.BuildingAndClassroomsData;
import com.example.lab3_behind.domain.dto.ClassroomAddingData;
import com.example.lab3_behind.domain.dto.ClassroomUpdatingData;
import com.example.lab3_behind.domain.resp.Result;
import com.example.lab3_behind.service.TeachingAffairsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
    @RequestMapping("/findBuildingPage")
    public Result findBuildingPage(@RequestBody PageSearchData pageSearchData){
        Map<String,Object> map = new HashMap<>();
        Page<TeachingBuilding> teachingBuildingPage = teachingAffairsService.findAPageTeachingBuilding(pageSearchData.getPageNum(),pageSearchData.getPageSize(), pageSearchData.getSearch());
        List<BuildingContents> buildingContents = BuildingContents.getContents(teachingBuildingPage.getContent());
        map.put("records",buildingContents);
        map.put("total",teachingBuildingPage.getTotalElements());
        return Result.succ(map);
    }


    @RequestMapping("/allBuildings")
    public Result allBuildings(){
        Map<String,Object> map = new HashMap<>();
        map.put("buildings",teachingAffairsService.findAllTeachingBuilding());
        return Result.succ(map);
    }

    @RequestMapping("addClassroom")
    public Result addClassroom(@RequestBody ClassroomAddingData classroomData){
        try{
            teachingAffairsService.insertClassroom(classroomData);
            return Result.succ(null);
        }catch (Exception e){
            //e.printStackTrace();
            return Result.fail(721,e.getMessage());
        }
    }
    @PostMapping("/deleteClassroom")
    public Result deleteClassroom(@RequestBody ClassroomData classroomData){
        try{
            teachingAffairsService.deleteClassroom(classroomData.getClassroomName(),classroomData.getBuildingName());
            return Result.succ(null);
        }
        catch (Exception e){
            //e.printStackTrace();
            return Result.fail(722,e.getMessage());
        }
    }
    @RequestMapping("/findClassroomPage")
    public Result findClassroomPage(@RequestBody PageSearchData pageSearchData){
        Map<String,Object> map = new HashMap<>();
        Page<Classroom> classroomPage = teachingAffairsService.findAPageClassroom(pageSearchData.getPageNum(),pageSearchData.getPageSize(), pageSearchData.getSearch());
        List<ClassroomData> classroomDatas = ClassroomData.getContents(classroomPage.getContent());
        map.put("records",classroomDatas);
        map.put("total",classroomPage.getTotalElements());
        return Result.succ(map);
    }

    @RequestMapping("updateClassroomInfo")
    public Result updateClassroomInfo(@RequestBody ClassroomUpdatingData classroomUpdatingData){
        try{
            teachingAffairsService.updateClassroom(classroomUpdatingData);
            return Result.succ(null);
        }catch (Exception e){
            return Result.fail(725,e.getMessage());
        }
    }

    @RequestMapping("allClassrooms")
    public Result allClassrooms(){
        Map<String,Object> map = new HashMap<>();
        map.put("buildings",teachingAffairsService.getAllBuildingAndClassrooms());
        return Result.succ(map);
    }

    //伪接口
    @RequestMapping("getClassroomSpareTime")
    public Result getClassroomSpareTime(@RequestBody String classroom){
        try{
            Map<String,Object> map = new HashMap<>();
            List<List<Boolean>> days= teachingAffairsService.getClassroomTime(classroom);
            map.put("days",days);
            return Result.succ(map);
        } catch (Exception e){
            e.printStackTrace();
            return Result.fail(888,e.getMessage());
        }
    }


}
