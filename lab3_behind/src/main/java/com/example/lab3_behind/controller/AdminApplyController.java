package com.example.lab3_behind.controller;

import com.example.lab3_behind.common.BuildingUpdatingData;
import com.example.lab3_behind.common.JwtUserData;
import com.example.lab3_behind.common.PageSearchData;
import com.example.lab3_behind.domain.Course;
import com.example.lab3_behind.domain.CourseApplying;
import com.example.lab3_behind.domain.resp.Result;
import com.example.lab3_behind.service.CourseService;
import com.example.lab3_behind.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminApplyController {
    @Autowired
    CourseService courseService;

    @RequestMapping("/acceptApply")
    public Result acceptApply(@RequestBody Map<String,Object> map){
        Integer applyId = (Integer) map.get("applyId");
        try{
            courseService.approveCourseApplying(applyId);
            return Result.succ(null);
        }catch (Exception e){
            return Result.fail(800,e.getMessage());
        }
    }

    @RequestMapping("/rejectApply")
    public Result rejectApply(@RequestBody Map<String,Object> map){
        Integer applyId = (Integer) map.get("applyId");
        try{
            courseService.rejectCourseApplying(applyId);
            return Result.succ(null);
        }catch (Exception e){
            return Result.fail(801,e.getMessage());
        }
    }

    @RequestMapping("/findApplyPage")
    public Result findCoursePage(@RequestBody PageSearchData pageSearchData,
                                 HttpServletRequest request){
        try{
            Map<String,Object> map = new HashMap<>();

            Page<CourseApplying> courseApplyingPage = courseService.findAPageCourseApplying(pageSearchData.getPageNum(),pageSearchData.getPageSize(), pageSearchData.getSearch());
            map.put("records",courseApplyingPage.getContent());
            map.put("total",courseApplyingPage.getTotalElements());
            return Result.succ(map);
        }catch (Exception e){
            //e.printStackTrace();
            return Result.fail(870,e.getMessage());
        }

    }

}
