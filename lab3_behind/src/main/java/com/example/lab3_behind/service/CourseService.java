package com.example.lab3_behind.service;

import com.example.lab3_behind.domain.Course;
import com.example.lab3_behind.domain.CourseApplying;
import com.example.lab3_behind.domain.dto.CourseApplyingData;
import org.springframework.data.domain.Page;

public interface CourseService {
//    Page<Course> findAPageCourse(Integer page, Integer size, String search);
//    Page<CourseApplying> findAPageCourseApplying(Integer page, Integer size, String search);
    CourseApplying applyToAddCourse(CourseApplyingData courseData);
    CourseApplying applyToUpdateCourse(CourseApplyingData courseData);
    CourseApplying applyToDeleteCourse(CourseApplyingData courseData);
//    Course insertCourse(CourseApplyingData courseData);
//    Course updateCourse(CourseApplyingData courseData);
//    Course deleteCourse(CourseApplyingData courseData);
}
