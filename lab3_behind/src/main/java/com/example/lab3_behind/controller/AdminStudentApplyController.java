package com.example.lab3_behind.controller;

import com.example.lab3_behind.common.*;
import com.example.lab3_behind.domain.CourseApplying;
import com.example.lab3_behind.domain.SelectCourseApplication;
import com.example.lab3_behind.domain.resp.Result;
import com.example.lab3_behind.service.CourseSelectingService;
import com.example.lab3_behind.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminStudentApplyController {
    @Autowired
    CourseSelectingService courseSelectingService;
    @Autowired
    CourseService courseService;

    @RequestMapping("/acceptSelectCourseApply")
    public Result acceptApply(@RequestBody AdminDealApplyData adminDealApplyData){
        try{
            courseSelectingService.approveSelectCourseApplication(adminDealApplyData.getApplyId(),adminDealApplyData.getAdvice());
            return Result.succ(null);
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(800,e.getMessage());
        }
    }

    @RequestMapping("/rejectSelectCourseApply")
    public Result rejectApply(@RequestBody AdminDealApplyData adminDealApplyData){
        try{
            courseSelectingService.rejectSelectCourseApplication(adminDealApplyData.getApplyId(),adminDealApplyData.getAdvice());
            return Result.succ(null);
        }catch (Exception e){
            return Result.fail(801,e.getMessage());
        }
    }

    @RequestMapping("/findSelectCourseApplyPage")
    public Result findCoursePage(@RequestBody PageSearchData pageSearchData,
                                 HttpServletRequest request){
        try{
            Map<String,Object> map = new HashMap<>();
            MyPage<SelectCourseApplication> courseApplyingPage = courseSelectingService.findAPageSelectCourseApplicationToDeal(pageSearchData.getPageNum(),pageSearchData.getPageSize());
            List<SelectCourseApplyContent> contents = SelectCourseApplyContent.getContents(courseApplyingPage.getRecords(),courseService);
            map.put("records", contents);
            map.put("total",courseApplyingPage.getTotal());
            return Result.succ(map);
        }catch (Exception e){
            return Result.fail(870,e.getMessage());
        }
    }

}
