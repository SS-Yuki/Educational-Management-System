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

    @Column(name = "teacher", nullable = true)
    private String teacher;

    @Column(name = "department ")
    private String department ;

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
        this.capacity = courseApplyingData.getCapacity();
        this.courseName = courseApplyingData.getCourseName();
        this.courseNumber = courseApplyingData.getCourseNumber();
        this.classroom = courseApplyingData.getClassroom();
        this.creditHours = courseApplyingData.getCreditHours();
        this.classPeriod = courseApplyingData.getClassPeriod();
        this.credits = courseApplyingData.getCredits();
        this.department = courseApplyingData.getDepartment();
        this.teacher = courseApplyingData.getTeacher();
        this.introduction = courseApplyingData.getIntroduction();
    }
}
