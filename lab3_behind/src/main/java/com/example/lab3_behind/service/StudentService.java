package com.example.lab3_behind.service;

import com.example.lab3_behind.common.CourseNameString;
import com.example.lab3_behind.common.MyPage;
import com.example.lab3_behind.common.forDomain.SchoolYear;
import com.example.lab3_behind.common.forDomain.Semester;
import com.example.lab3_behind.domain.Course;
import com.example.lab3_behind.domain.Student;
import com.example.lab3_behind.domain.dto.RevisableDataForAdmin;
import com.example.lab3_behind.domain.dto.RevisableDataForUser;
import com.example.lab3_behind.domain.dto.UserEnteringData;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {
    Page<Student> findAPageStudent(Integer page, Integer size, String search);
    List<Course> findCourseInSemester(String stuNum, SchoolYear schoolYear, Semester semester) throws Exception;
    List<List<CourseNameString>> getClassScheduleInSemester(String stuNum, SchoolYear schoolYear, Semester semester) throws Exception;
    Student insertStudent(UserEnteringData userData) throws Exception;
    Student updateStudentInfo(RevisableDataForAdmin userData, String stuNumber) throws Exception;
    Student maintainStudentInfo(RevisableDataForUser userData, String stuNumber) throws Exception;
    Student getByStuNumber(String stuNumber) throws Exception;

}
