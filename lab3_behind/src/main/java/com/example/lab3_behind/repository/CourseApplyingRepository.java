package com.example.lab3_behind.repository;

import com.example.lab3_behind.domain.CourseApplying;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseApplyingRepository extends JpaRepository<CourseApplying, Long> {
    CourseApplying findByApplyId(Integer applyId);
    Page<CourseApplying> findAllByTeacherNum(String teacherNum, Pageable pageable);
   // Page<CourseApplying> findAllByTeacherNum(String teacherNum, Example example, Pageable pageable);
}
