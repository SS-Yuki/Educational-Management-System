package com.example.lab3_behind.controller;


import com.example.lab3_behind.common.BuildingContents;
import com.example.lab3_behind.common.BuildingUpdatingData;
import com.example.lab3_behind.common.PageSearchData;
import com.example.lab3_behind.common.TimeData;
import com.example.lab3_behind.domain.TeachingBuilding;
import com.example.lab3_behind.domain.TimeTable;
import com.example.lab3_behind.domain.dto.ClassTimeData;
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
public class AdminTimeController {
    @Autowired
    TeachingAffairsService teachingAffairsService;

    @RequestMapping("addTime")
    public Result addTime(@RequestBody TimeData timeData){
        try{
            teachingAffairsService.addClassTime(timeData.toClassTimeData());
            return Result.succ(null);
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(710,e.getMessage());
        }
    }

    @RequestMapping("/updateTimeInfo")
    public Result updateTime(@RequestBody TimeData timeData){
        try{
            teachingAffairsService.updateClassTime(timeData.toClassTimeData());
            return Result.succ(null);
        }catch (Exception e){
            //e.printStackTrace();
            return Result.fail(711,e.getMessage());
        }
    }
    @PostMapping("/deleteTime")
    public Result deleteTime(){
        try{
            teachingAffairsService.deleteClassTime();
            return Result.succ(null);
        }
        catch (Exception e){
            //e.printStackTrace();
            return Result.fail(712,e.getMessage());
        }
    }
    @RequestMapping("/findTimePage")
    public Result findTimePage(@RequestBody PageSearchData pageSearchData){
        Map<String,Object> map = new HashMap<>();
        Page<TimeTable> timeTablePage = teachingAffairsService.findAPageTimeTable(pageSearchData.getPageNum(),pageSearchData.getPageSize(), pageSearchData.getSearch());
        List<TimeData> timeDatas = TimeData.getContents(timeTablePage.getContent());
        map.put("records",timeDatas);
        map.put("total",timeTablePage.getTotalElements());
        return Result.succ(map);
    }



}
