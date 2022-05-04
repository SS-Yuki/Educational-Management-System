package com.example.lab3_behind.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectCourseApplyContent {
    String courseId;
    String courseNum;
    String courseName;
    String teacherName;
    String year;
    String semester;
    String description;
    String dealStatus;
    String advice;
}
