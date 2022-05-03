package com.example.lab3_behind.repository;


import com.example.lab3_behind.domain.Classroom;
import com.example.lab3_behind.domain.CourseApplying;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseApplyingRepository extends JpaRepository<CourseApplying, Long> {
    CourseApplying findByApplyId(Integer applyId);
    List<CourseApplying> findByClassroom(Classroom classroom);
    CourseApplying findByCourseId(Integer courseId);
    CourseApplying deleteByCourseId(Integer courseId);
    Page<CourseApplying> findAllByTeacherNum(String teacherNum, Pageable pageable);
    //Page<CourseApplying> findByTeacherNum(String teacherNum, Example example, Pageable pageable);
}
