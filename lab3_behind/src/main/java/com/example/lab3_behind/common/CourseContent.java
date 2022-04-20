package com.example.lab3_behind.common;

import com.example.lab3_behind.domain.Course;
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
    private String classPeriod ;
    private String classroom ;
    private Integer creditHours;
    private Integer credits;
    private Integer capacity;
    private String introduction;

    public static List<CourseContent> getContent(List<Course> courses){
        List<CourseContent> courseContents = new ArrayList<>();
        for(Course course:courses){
            courseContents.add(new CourseContent(course.getCourseId(),
                    course.getCourseNumber(),
                    course.getCourseName(),
                    course.getTeacherNum(),
                    course.getTeacherName(),
                    course.getSchool().getName(),
                    course.getMajor().getName(),
                    course.getClassPeriod(),
                    course.getClassroom(),
                    course.getCreditHours(),
                    course.getCourseId(),
                    course.getCourseId(),
                    course.getIntroduction()));
        }
        return courseContents;
    }
}
