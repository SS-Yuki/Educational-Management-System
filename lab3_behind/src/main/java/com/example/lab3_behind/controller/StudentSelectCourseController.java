package com.example.lab3_behind.controller;

import com.example.lab3_behind.common.*;
import com.example.lab3_behind.domain.Course;
import com.example.lab3_behind.domain.dto.YearAndSemestersData;
import com.example.lab3_behind.domain.dto.YearSemesterPair;
import com.example.lab3_behind.domain.resp.Result;
import com.example.lab3_behind.service.AuthorityService;
import com.example.lab3_behind.service.CourseSelectingService;
import com.example.lab3_behind.service.CourseService;
import com.example.lab3_behind.service.StudentService;
import com.example.lab3_behind.utils.EnumTool;
import com.example.lab3_behind.utils.JwtUtil;
import com.example.lab3_behind.utils.TimeTool;
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
@RequestMapping("/student")
public class StudentSelectCourseController {
    @Autowired
    AuthorityService authorityService;
    @Autowired
    CourseService courseService;
    @Autowired
    CourseSelectingService courseSelectService;
    @Autowired
    StudentService studentService;



    @RequestMapping("/findSelectCoursePage")
    public Result findCoursePage(@RequestBody FullCoursePageSearch pageSearchData,
                                 HttpServletRequest request){
        String token = request.getHeader("token");
        JwtUserData jwtUserData = JwtUtil.getToken(token);
        String number = jwtUserData.getNumber().replace("\"", "");
        try{
            Boolean open = authorityService.checkCourseSelectingAuthority();
            if(!open){
                throw new Exception("当前选课未开放");
            }
            Map<String,Object> map = new HashMap<>();
            MyPage<Course> coursePage = courseService.findAPageCourseForSelecting(
                    pageSearchData.getPageNum(),
                    pageSearchData.getPageSize(),
                    pageSearchData.getSearch(),
                    number,
                    EnumTool.transSchoolYear(TimeTool.getPresentYearAndSemester().getYear()),
                    EnumTool.transSemester(TimeTool.getPresentYearAndSemester().getSemester()),
                    pageSearchData.getClassroom(),
                    pageSearchData.getSelectTime()
                    );
            List<CourseContent> courseContents = CourseContent.getContent(coursePage.getRecords());
            map.put("records",courseContents);
            map.put("total",coursePage.getTotal());
            return Result.succ(map);
        }catch (Exception e){
            //e.printStackTrace();
            return Result.fail(830,e.getMessage());
        }
    }

    @RequestMapping("/selectCourse")
    public Result selectCourse(@RequestBody Integer courseId,HttpServletRequest request){
        String token = request.getHeader("token");
        JwtUserData jwtUserData = JwtUtil.getToken(token);
        String number = jwtUserData.getNumber().replace("\"", "");
        try{
            courseSelectService.selectCourse(number,courseId,
                    EnumTool.transSchoolYear(TimeTool.getPresentYearAndSemester().getYear()),
                    EnumTool.transSemester(TimeTool.getPresentYearAndSemester().getSemester()));
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(910,e.getMessage());
        }
        return Result.succ(null);
    }

    @RequestMapping("/applyForSelectCourse")
    public Result applyForSelectCourse(@RequestBody StudentApplyForSelectCourse apply,HttpServletRequest request){
        String token = request.getHeader("token");
        JwtUserData jwtUserData = JwtUtil.getToken(token);
        String number = jwtUserData.getNumber().replace("\"", "");
        try{
            //courseSelectService.selectCourse(number,);
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(910,e.getMessage());
        }
        return Result.succ(null);
    }

    @RequestMapping("/dropCourse")
    public Result dropCourse(@RequestBody Integer courseId,HttpServletRequest request){
        String token = request.getHeader("token");
        JwtUserData jwtUserData = JwtUtil.getToken(token);
        String number = jwtUserData.getNumber().replace("\"", "");
        try{
            courseSelectService.dropCourse(number,courseId,
                    EnumTool.transSchoolYear(TimeTool.getPresentYearAndSemester().getYear()),
                    EnumTool.transSemester(TimeTool.getPresentYearAndSemester().getSemester()));
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(911,e.getMessage());
        }
        return Result.succ(null);
    }

    @RequestMapping("getMyCanDropCourse")
    public Result getMyCanDropCourse(HttpServletRequest request){
        String token = request.getHeader("token");
        JwtUserData jwtUserData = JwtUtil.getToken(token);
        String number = jwtUserData.getNumber().replace("\"", "");
        try {
            List<Course> courses = studentService.findCourseInSemester(number,
                    EnumTool.transSchoolYear(TimeTool.getPresentYearAndSemester().getYear()),
                    EnumTool.transSemester(TimeTool.getPresentYearAndSemester().getSemester()));
            List<CourseContent> courseContents = CourseContent.getContent(courses);
            return Result.succ(courseContents);
        } catch (Exception e){
            e.printStackTrace();
            return Result.fail(832,e.getMessage());
        }
    }

    @RequestMapping("getMyCourseInSemester")
    public Result getMyCourseInSemester(@RequestBody YearSemesterPair yearSemesterPair,HttpServletRequest request){
        String token = request.getHeader("token");
        JwtUserData jwtUserData = JwtUtil.getToken(token);
        String number = jwtUserData.getNumber().replace("\"", "");
        try {
            List<Course> courses = studentService.findCourseInSemester(number,
                    EnumTool.transSchoolYear(yearSemesterPair.getYear()),
                    EnumTool.transSemester(yearSemesterPair.getSemester()));
            List<CourseContent> courseContents = CourseContent.getContent(courses);
            return Result.succ(courseContents);
        } catch (Exception e){
            e.printStackTrace();
            return Result.fail(833,e.getMessage());
        }
    }

    @RequestMapping("getMyCourseTableInSemester")
    public Result getMyCourseTableInSemester(@RequestBody YearSemesterPair yearSemesterPair,HttpServletRequest request){
        String token = request.getHeader("token");
        JwtUserData jwtUserData = JwtUtil.getToken(token);
        String number = jwtUserData.getNumber().replace("\"", "");
        try {
            List<List<CourseNameString>> courses = studentService.getClassScheduleInSemester(number,
                    EnumTool.transSchoolYear(yearSemesterPair.getYear()),
                    EnumTool.transSemester(yearSemesterPair.getSemester()));
            return Result.succ(courses);
        } catch (Exception e){
            e.printStackTrace();
            return Result.fail(833,e.getMessage());
        }
    }


}
