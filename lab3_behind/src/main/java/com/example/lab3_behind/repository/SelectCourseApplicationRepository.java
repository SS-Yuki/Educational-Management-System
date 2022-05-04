package com.example.lab3_behind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectCourseApplicationRepository extends JpaRepository<SelectCourseApplicationRepository, Long> {

}
