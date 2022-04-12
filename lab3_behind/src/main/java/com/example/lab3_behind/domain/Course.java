package com.example.lab3_behind.domain;

import com.example.lab3_behind.common.CourseStatus;
import com.example.lab3_behind.domain.dto.CourseApplyingData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "course_number", length = 36, nullable = true)
    private String courseNumber;

    @Column(name = "course_name", length = 256, nullable = true)
    private String courseName;

    @Column(name = "teacher_num", nullable = true)
    private String teacherNum;

    @Column(name = "teacher_name", nullable = true)
    private String teacherName;

    @Column(name = "major")
    private String major;

    @Column(name = "school")
    private String school;

    @Column(name = "class_period")
    private String classPeriod ;

    @Column(name = "classroom")
    private String classroom ;

    @Column(name = "credit_hours")
    private Integer creditHours;

    @Column(name = "credits")
    private Integer credits;

    @Column(name = "capacity")
    private Integer capacity;

    @Lob
    @Column(name = "introduction", columnDefinition="TEXT")
    private String introduction;

    @Enumerated(EnumType.STRING)
    @Column(name = "course_status")
    private CourseStatus courseStatus;

    public Course(CourseApplyingData courseApplyingData){
        this.teacherName = courseApplyingData.getApplicant();
        this.capacity = courseApplyingData.getCapacity();
        this.courseName = courseApplyingData.getCourseName();
        this.courseNumber = courseApplyingData.getCourseNumber();
        this.classroom = courseApplyingData.getClassroom();
        this.creditHours = courseApplyingData.getCreditHours();
        this.classPeriod = courseApplyingData.getClassPeriod();
        this.credits = courseApplyingData.getCredits();
        this.major = courseApplyingData.getMajor();
        this.school = courseApplyingData.getSchool();
        this.teacherNum = courseApplyingData.getTeacherNum();
        this.introduction = courseApplyingData.getIntroduction();
    }

    public Course(CourseApplying courseApplying){
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
        this.teacherName = courseApplying.getTeacherName();
    }
}
