package com.example.lab3_behind.common;

import com.example.lab3_behind.domain.Course;
import com.example.lab3_behind.domain.Major;
import com.example.lab3_behind.utils.EnumTool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseContent {
    private Integer courseId;
    private String courseNumber;
    private String courseName;
    private String teacherNum;
    private String teacherName;
    private String school;
    private String major;
    private String building;
    private String classroom ;
    private Integer creditHours;
    private Integer credits;
    private Integer capacity;
    private String introduction;
    private String selectTypeString;
    private String majorLimits;
    private String year;
    private String semester;
    private String occupyTime;

    public static CourseContent oneContent(Course course){
        String majorLim = "";
        for(Major major:course.getMajorsOptional()){
            majorLim= majorLim + major.getName() + ";";
        }
        if(majorLim.length()>0){
            majorLim = majorLim.substring(0,majorLim.length()-1);
        }
        return new CourseContent(course.getCourseId(),
                course.getCourseNumber(),
                course.getCourseName(),
                course.getTeacherNum(),
                course.getTeacherName(),
                course.getSchool().getName(),
                course.getMajor().getName(),
                course.getClassroom().getTeachingBuilding().getName(),
                course.getClassroom().getName(),
                course.getCreditHours(),
                course.getCourseId(),
                course.getCourseId(),
                course.getIntroduction(),
                EnumTool.transString(course.getCourseSelectType()),
                majorLim,
                EnumTool.transString(course.getSchoolYear()),
                EnumTool.transString(course.getSemester()),
                course.getClassTimeString());
    }

    public static List<CourseContent> getContent(List<Course> courses){
        List<CourseContent> courseContents = new ArrayList<>();
        for(Course course:courses){
            courseContents.add(CourseContent.oneContent(course));
        }
        return courseContents;
    }
}
