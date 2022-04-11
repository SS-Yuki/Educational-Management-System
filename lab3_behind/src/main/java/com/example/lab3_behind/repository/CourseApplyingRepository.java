package com.example.lab3_behind.repository;

import com.example.lab3_behind.domain.CourseApplying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseApplyingRepository extends JpaRepository<CourseApplying, Long> {
}
