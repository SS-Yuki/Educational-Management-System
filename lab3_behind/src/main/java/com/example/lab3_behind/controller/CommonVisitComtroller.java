package com.example.lab3_behind.controller;

import com.example.lab3_behind.common.CourseContent;
import com.example.lab3_behind.common.TimeData;
import com.example.lab3_behind.common.addCourseForm;
import com.example.lab3_behind.domain.Course;
import com.example.lab3_behind.domain.TimeTable;
import com.example.lab3_behind.domain.dto.YearSemesterPair;
import com.example.lab3_behind.domain.resp.Result;
import com.example.lab3_behind.service.CourseService;
import com.example.lab3_behind.service.TeachingAffairsService;
import com.example.lab3_behind.utils.TimeTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("common")
public class CommonVisitComtroller {

    @Autowired
    TeachingAffairsService teachingAffairsService;
    @Autowired
    CourseService courseService;
    @RequestMapping("allSemesters")
    public Result allSemesters(){
        Map<String,Object> map = new HashMap<>();
        YearSemesterPair yearSemesterPair = TimeTool.getPresentYearAndSemester();
        map.put("defaultYear",yearSemesterPair.getYear());
        map.put("defaultSemester",yearSemesterPair.getSemester());
        map.put("yearAndSemesters",teachingAffairsService.getAllYearAndSemesters());
        return Result.succ(map);
    }

    @RequestMapping("/allTime")
    public Result allTime(){
        Map<String,Object> map = new HashMap<>();
        List<TimeTable> timeTables = teachingAffairsService.findAllTimeTable();
        List<TimeData> timeDatas = TimeData.getContents(timeTables);
        map.put("times",timeDatas);
        return Result.succ(map);
    }

    //伪端口
    @RequestMapping("/findOneCourseInfo")
    public Result findOneCourseInfo(@RequestBody Integer courseId){
        try{
            Course course = courseService.getCourse(courseId);
            CourseContent courseContent = CourseContent.oneContent(course);
            return Result.succ(courseContent);
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(900,e.getMessage());
        }

    }
}
