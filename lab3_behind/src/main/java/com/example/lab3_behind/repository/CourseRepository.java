package com.example.lab3_behind.repository;

import com.example.lab3_behind.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByCourseId(Integer courseId);
    Page<Course> findAllByMajor(String major, Pageable pageable);
    Page<Course> findAllByTeacherNum(String teacherNum, Pageable pageable);
}
