package com.example.lab3_behind.controller;

import com.example.lab3_behind.common.*;
import com.example.lab3_behind.domain.Course;
import com.example.lab3_behind.domain.Student;
import com.example.lab3_behind.domain.TimeTable;
import com.example.lab3_behind.domain.dto.YearSemesterPair;
import com.example.lab3_behind.domain.resp.Result;
import com.example.lab3_behind.service.CourseService;
import com.example.lab3_behind.service.SchoolService;
import com.example.lab3_behind.service.TeachingAffairsService;
import com.example.lab3_behind.utils.EnumTool;
import com.example.lab3_behind.utils.TimeTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("common")
public class CommonVisitController {

    @Autowired
    TeachingAffairsService teachingAffairsService;
    @Autowired
    CourseService courseService;
    @Autowired
    SchoolService schoolService;
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

    @RequestMapping("/getClassroomSpareTime")
    public Result getClassroomSpareTime(@RequestBody ClassroomAndYearAndSemester payloads){
        try{
            Map<String,Object> map = new HashMap<>();
            List<List<Boolean>> days= teachingAffairsService.getClassroomTime(payloads.getClassroom(), EnumTool.transSchoolYear(payloads.getYear()),EnumTool.transSemester(payloads.getSemester()));
            map.put("days",days);
            return Result.succ(map);
        } catch (Exception e){
            e.printStackTrace();
            return Result.fail(888,e.getMessage());
        }
    }

    @RequestMapping("/getClassroomSpareTimeExceptOneCourse")
    public Result getClassroomSpareTimeExceptOneCourse(@RequestBody ClassroomAndCourseId classroomAndCourseId){
        try{
            Map<String,Object> map = new HashMap<>();
            List<List<Boolean>> days = teachingAffairsService.getClassroomTime(classroomAndCourseId.getClassroom(), classroomAndCourseId.getCourseId());
            map.put("days",days);
            return Result.succ(map);
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(893,e.getMessage());
        }
    }
    @RequestMapping("/getClassroomOccupyByOneCourse")
    public Result getClassroomOccupyByOneCourse(@RequestBody ClassroomAndCourseId classroomAndCourseId){
        try{
            Map<String,Object> map = new HashMap<>();
            List<List<Boolean>> occupys = teachingAffairsService.getCourseTimeInClassroom(classroomAndCourseId.getClassroom(), classroomAndCourseId.getCourseId());
            map.put("occupys",occupys);
            return Result.succ(map);
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(894,e.getMessage());
        }
    }

    @RequestMapping("/allMajors")
    public Result allMajors(){
        Map<String,Object> map = new HashMap<>();
        map.put("schools",schoolService.getAllSchoolAndMajors());
        return Result.succ(map);
    }

    @RequestMapping("allClassrooms")
    public Result allClassrooms(){
        Map<String,Object> map = new HashMap<>();
        map.put("buildings",teachingAffairsService.getAllBuildingAndClassrooms());
        return Result.succ(map);
    }

    @RequestMapping("/getStudentListOfOneCourse")
    public Result getStudentListOfOneCourse(@RequestBody Integer courseId){
        Map<String,Object> map = new HashMap<>();
        List<Student> studentList = courseService.getStudentListOfOneCourse(courseId);
        List<StudentListContent> studentListContent = StudentListContent.getContents(studentList);
        map.put("records",studentListContent);
        return Result.succ(map);
    }
}
