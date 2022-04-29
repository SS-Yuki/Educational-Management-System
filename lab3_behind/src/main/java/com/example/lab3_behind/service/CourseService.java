package com.example.lab3_behind.service;

import com.example.lab3_behind.common.forDomain.CourseApplyingType;
import com.example.lab3_behind.domain.Course;
import com.example.lab3_behind.domain.CourseApplying;
import com.example.lab3_behind.domain.dto.CourseApplyingData;
import org.springframework.data.domain.Page;


public interface CourseService {
    Page<Course> findAPageCourse(Integer page, Integer size, String search);
    Page<Course> findAPageCourseForSelecting(Integer page, Integer size, String search, String stuNum) throws Exception;
    Page<Course> findAPageCourseOfTeacher(Integer page, Integer size, String search, String jobNum) throws Exception;
    Page<CourseApplying> findAPageCourseApplying(Integer page, Integer size, String search);
    Page<CourseApplying> findCourseApplyingOfTeacher(Integer page, Integer size, String search, String jobNum) throws Exception;
    CourseApplying pushCourseApplying(CourseApplyingData courseData, CourseApplyingType applyingType) throws Exception;
    CourseApplying approveCourseApplying(Integer courseApplyingId) throws Exception;
    CourseApplying rejectCourseApplying(Integer courseApplyingId) throws Exception;
    Course insertCourse(CourseApplyingData courseApplyingData) throws Exception;
    Course updateCourse(CourseApplyingData courseApplyingData) throws Exception;
    Course deleteCourse(Integer courseId) throws Exception;
}
