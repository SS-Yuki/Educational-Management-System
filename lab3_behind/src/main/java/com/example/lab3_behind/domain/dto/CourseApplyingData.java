package com.example.lab3_behind.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseApplyingData {
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

}
