package com.example.lab3_behind.controller;

import com.example.lab3_behind.common.*;
import com.example.lab3_behind.domain.Classroom;
import com.example.lab3_behind.domain.TeachingBuilding;
import com.example.lab3_behind.domain.dto.BuildingAndClassroomsData;
import com.example.lab3_behind.domain.dto.ClassroomAddingData;
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
    public Result addClassroom(@RequestBody ClassroomData classrommData){
        try{
            teachingAffairsService.insertClassroom(new ClassroomAddingData(classrommData.getClassroomName(),classrommData.getBuildingName()));
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

    //伪接口
    @RequestMapping("allClassrooms")
    public Result allClassrooms(){
        List<String> classrooms = new ArrayList<>();
        classrooms.add("H3101");
        classrooms.add("H3201");
        List<BuildingAndClassroomsData> buildingAndClassroomsData = new ArrayList<>();
        buildingAndClassroomsData.add(new BuildingAndClassroomsData("第3教学楼",classrooms));
        Map<String,Object> map = new HashMap<>();
        map.put("buildings", buildingAndClassroomsData);
        return Result.succ(map);
    }

    //伪接口
    @RequestMapping("getClassroomSpareTime")
    public Result getClassroomSpareTime(@RequestBody String classroom){
        Map<String,Object> map = new HashMap<>();
        System.out.println(classroom);
        List<Boolean> oneday = new ArrayList<>();
        oneday.add(true);
        oneday.add(true);
        oneday.add(true);
        List<List<Boolean>> days= new ArrayList<>();
        days.add(oneday);
        days.add(oneday);
        days.add(oneday);
        days.add(oneday);
        days.add(oneday);
        days.add(oneday);
        days.add(oneday);
        map.put("days",days);
        return Result.succ(map);
    }


}
