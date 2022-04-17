package com.example.lab3_behind.repository;

import com.example.lab3_behind.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findByJobNumber(String jobNumber);
    Teacher findByIdNum(String idNum);
    Teacher findByMajor(String major);
    Teacher findBySchool(String school);
    Teacher deleteByJobNumber(String jobNumber);
}
