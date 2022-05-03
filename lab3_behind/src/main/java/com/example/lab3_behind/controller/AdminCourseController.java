package com.example.lab3_behind.controller;


import com.example.lab3_behind.common.*;
import com.example.lab3_behind.domain.Course;
import com.example.lab3_behind.domain.TeachingBuilding;
import com.example.lab3_behind.domain.dto.CourseApplyingData;
import com.example.lab3_behind.domain.resp.Result;
import com.example.lab3_behind.service.AuthorityService;
import com.example.lab3_behind.service.CourseService;
import com.example.lab3_behind.utils.EnumTool;
import com.example.lab3_behind.utils.TimeTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminCourseController {
    @Autowired
    CourseService courseService;


    @PostMapping("/addCourse")
    public Result addCourse(@RequestBody CourseApplyingData courseApplyingData){
        try {
            courseService.insertCourse(courseApplyingData);
            return Result.succ(null);
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(730,e.getMessage());
        }
    }

    @PostMapping("/csvAddCourse")
    public Result csvAddCourse(@RequestBody List<CourseApplyingData> courseApplyingDatas){
        int failNum=0;
        for(CourseApplyingData courseApplyingData:courseApplyingDatas){
            try {
                courseService.insertCourse(courseApplyingData);
            }catch (Exception e){
                e.printStackTrace();
                failNum++;
            }
        }
        if(failNum==0) return Result.succ(null);
        else return Result.fail(880,"部分信息不符合要求");
    }


    @RequestMapping("/updateCourseInfo")
    public Result updateCourseInfo(@RequestBody CourseApplyingData courseApplyingData){
        try{
            courseService.updateCourse(courseApplyingData);
            return Result.succ(null);
        }catch (Exception e){
            //e.printStackTrace();
            return Result.fail(731,e.getMessage());
        }
    }
    @PostMapping("/deleteCourse")
    public Result deleteCourse(@RequestBody Integer courseId){
        try{
            courseService.deleteCourse(courseId);
            return Result.succ(null);
        }
        catch (Exception e){
            e.printStackTrace();
            return Result.fail(732,e.getMessage());
        }
    }
    @RequestMapping("/findCoursePage")
    public Result findCoursePage(@RequestBody FullCoursePageSearch pageSearchData){
        try{
            Map<String,Object> map = new HashMap<>();
            MyPage<Course> coursePage = courseService.findAPageCourse(
                    pageSearchData.getPageNum(),
                    pageSearchData.getPageSize(),
                    pageSearchData.getSearch(),
                    EnumTool.transSchoolYear(pageSearchData.getYear()),
                    EnumTool.transSemester(pageSearchData.getSemester()),
                    pageSearchData.getClassroom(),
                    pageSearchData.getSelectTime()
                    );
            List<CourseContent> courseContents = CourseContent.getContent(coursePage.getRecords());
            map.put("records",courseContents);
            map.put("total",coursePage.getTotal());
            return Result.succ(map);
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(889,e.getMessage());
        }

    }


}
