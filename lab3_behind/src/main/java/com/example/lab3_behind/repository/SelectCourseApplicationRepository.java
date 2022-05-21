package com.example.lab3_behind.repository;

import com.example.lab3_behind.domain.SelectCourseApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SelectCourseApplicationRepository extends JpaRepository<SelectCourseApplication, Long> {
    SelectCourseApplication findById(Integer id);
    List<SelectCourseApplication> findByStuNumber(String stuNumber);
    List<SelectCourseApplication> findByStuNumberAndCourseId(String stuNumber, Integer courseId);
}
