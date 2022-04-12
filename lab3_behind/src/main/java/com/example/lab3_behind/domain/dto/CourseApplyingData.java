package com.example.lab3_behind.domain.dto;

import com.example.lab3_behind.domain.CourseApplying;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseApplyingData {
    private Integer id;

    private String courseNumber;

    private String courseName;

    private String teacherNum;

    private String major;

    private String school;

    private String classPeriod ;

    private String classroom ;

    private Integer creditHours;

    private Integer credits;

    private Integer capacity;

    private String introduction;

    private String applicant;

    public CourseApplyingData(CourseApplying courseApplying){
        this.id = courseApplying.getCourseId();
        this.applicant = courseApplying.getApplicant();
        this.capacity = courseApplying.getCapacity();
        this.courseName = courseApplying.getCourseName();
        this.courseNumber = courseApplying.getCourseNumber();
        this.classroom = courseApplying.getClassroom();
        this.creditHours = courseApplying.getCreditHours();
        this.classPeriod = courseApplying.getClassPeriod();
        this.credits = courseApplying.getCredits();
        this.major = courseApplying.getMajor();
        this.school = courseApplying.getSchool();
        this.teacherNum = courseApplying.getTeacherNum();
        this.introduction = courseApplying.getIntroduction();
    }

}
