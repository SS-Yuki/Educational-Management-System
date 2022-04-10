package com.example.lab3_behind.repository;

import com.example.lab3_behind.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findByJobNumber(String jobNumber);
    Teacher findByIdNum(String idNum);
    Teacher deleteByJobNumber(String jobNumber);
}
