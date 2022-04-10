package com.example.lab3_behind.service;

import com.example.lab3_behind.domain.Course;
import com.example.lab3_behind.domain.dto.CourseEnteringData;

public interface CourseService {
    Course insertCourse(CourseEnteringData courseData);
}
