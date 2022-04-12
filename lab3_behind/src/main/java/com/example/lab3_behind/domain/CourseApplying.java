package com.example.lab3_behind.domain;

import com.example.lab3_behind.common.CourseApplyingType;
import com.example.lab3_behind.common.StudentStatus;
import com.example.lab3_behind.domain.dto.CourseApplyingData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courseApplying")
public class CourseApplying {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_id")
    private Integer applyId;

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

    @Column(name = "applicant")
    private String applicant;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CourseApplyingType type;

    public CourseApplying(CourseApplyingData courseApplyingData){
        this.teacherName = courseApplyingData.getApplicant();
        this.applicant = courseApplyingData.getApplicant();
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
}
