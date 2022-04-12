package com.example.lab3_behind.service;

import com.example.lab3_behind.common.CourseApplyingType;
import com.example.lab3_behind.domain.Course;
import com.example.lab3_behind.domain.CourseApplying;
import com.example.lab3_behind.domain.dto.CourseApplyingData;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {
    Page<Course> findAPageCourse(Integer page, Integer size, String search);
//    Page<Course> findApageCourseOfMajor(Integer page, Integer size, String search);
    Page<CourseApplying> findAPageCourseApplying(Integer page, Integer size, String search);
    Page<CourseApplying> findCourseApplyingOfTeacher(Integer page, Integer size, String search, String jobNum) throws Exception;
    CourseApplying pushCourseApplying(CourseApplyingData courseData, CourseApplyingType applyingType) throws Exception;
    CourseApplying approveCourseApplying(Integer courseApplyingId) throws Exception;
    CourseApplying rejectCourseApplying(Integer courseApplyingId) throws Exception;
    Course insertCourse(CourseApplyingData courseApplyingData) throws Exception;
    Course updateCourse(CourseApplyingData courseApplyingData) throws Exception;
    Course deleteCourse(Integer courseId) throws Exception;
}
