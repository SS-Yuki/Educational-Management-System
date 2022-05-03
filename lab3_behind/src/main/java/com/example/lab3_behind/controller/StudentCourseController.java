package com.example.lab3_behind.controller;


import com.example.lab3_behind.common.*;
import com.example.lab3_behind.domain.Course;
import com.example.lab3_behind.domain.resp.Result;
import com.example.lab3_behind.utils.EnumTool;
import com.example.lab3_behind.utils.JwtUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentCourseController {
//    @RequestMapping("/findCourseOfSemester")
//    public Result findCoursePage(@RequestBody PageSearchWithYearAndSemester pageSearchData,
//                                 HttpServletRequest request){
//        String token = request.getHeader("token");
//        JwtUserData jwtUserData = JwtUtil.getToken(token);
//        String number = jwtUserData.getNumber().replace("\"", "");
//        try{
//            Boolean open = authorityService.checkCourseSelectingAuthority();
//            if(!open){
//                throw new Exception("当前选课未开放");
//            }
//            Map<String,Object> map = new HashMap<>();
//            MyPage<Course> coursePage = courseService.findAPageCourseForSelecting(
//                    pageSearchData.getPageNum(),
//                    pageSearchData.getPageSize(),
//                    pageSearchData.getSearch(),
//                    number,
//                    EnumTool.transSchoolYear(pageSearchData.getYear()),
//                    EnumTool.transSemester(pageSearchData.getSemester()),
//                    pageSearchData.getClassroom(),
//                    pageSearchData.getSelectTime()
//            );
//            List<CourseContent> courseContents = CourseContent.getContent(coursePage.getRecords());
//            map.put("records",courseContents);
//            map.put("total",coursePage.getTotal());
//            return Result.succ(map);
//        }catch (Exception e){
//            //e.printStackTrace();
//            return Result.fail(830,e.getMessage());
//        }
//    }
}
