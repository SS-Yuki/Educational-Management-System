package com.example.lab3_behind.service;

import com.example.lab3_behind.common.MyPage;
import com.example.lab3_behind.common.forDomain.CourseApplyingType;
import com.example.lab3_behind.common.forDomain.SchoolYear;
import com.example.lab3_behind.common.forDomain.Semester;
import com.example.lab3_behind.domain.Course;
import com.example.lab3_behind.domain.CourseApplying;
import com.example.lab3_behind.domain.School;
import com.example.lab3_behind.domain.Student;
import com.example.lab3_behind.domain.dto.CourseApplyingData;
import org.springframework.data.domain.Page;

import java.util.List;


public interface CourseService {
    Course getCourse(Integer courseId) throws Exception;
    MyPage<Course> findAPageCourse(Integer page, Integer size, String search, SchoolYear schoolYear, Semester semester,
                                   String classroomName, List<List<Integer>> selectTime);
    MyPage<Course> findAPageCourseForSelecting(Integer page, Integer size, String search, String stuNum,
                                               SchoolYear schoolYear, Semester semester,
                                               String classroomName, List<List<Integer>> selectTime) throws Exception;
    MyPage<Course> findAPageCourseOfTeacher(Integer page, Integer size, String jobNum, SchoolYear schoolYear, Semester semester) throws Exception;
    Page<CourseApplying> findAPageCourseApplying(Integer page, Integer size, String search);
    Page<CourseApplying> findCourseApplyingOfTeacher(Integer page, Integer size, String search, String jobNum) throws Exception;
    List<Student> getStudentListOfOneCourse(Integer courseId) throws Exception;
    CourseApplying pushCourseApplying(CourseApplyingData courseData, CourseApplyingType applyingType) throws Exception;
    CourseApplying approveCourseApplying(Integer courseApplyingId) throws Exception;
    CourseApplying rejectCourseApplying(Integer courseApplyingId) throws Exception;
    Course insertCourse(CourseApplyingData courseApplyingData) throws Exception;
    Course updateCourse(CourseApplyingData courseApplyingData) throws Exception;
    Course deleteCourse(Integer courseId) throws Exception;
}
