package com.example.lab3_behind.repository;

import com.example.lab3_behind.common.forDomain.SchoolYear;
import com.example.lab3_behind.common.forDomain.Semester;
import com.example.lab3_behind.domain.Classroom;
import com.example.lab3_behind.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByCourseId(Integer courseId);
    Course findByCourseNumberAndTeacherNumAndSchoolYearAndSemester(String courseNumber, String teacherNum, SchoolYear schoolYear, Semester semester);
    List<Course> findByClassroomAndSchoolYearAndSemester(Classroom classroom, SchoolYear schoolYear, Semester semester);
    Page<Course> findAllByMajor(String major, Pageable pageable);
    Page<Course> findAllByTeacherNum(String teacherNum, Pageable pageable);
}
