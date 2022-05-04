package com.example.lab3_behind.repository;

import com.example.lab3_behind.domain.SelectCourseApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectCourseApplicationRepository extends JpaRepository<SelectCourseApplication, Long> {

}
