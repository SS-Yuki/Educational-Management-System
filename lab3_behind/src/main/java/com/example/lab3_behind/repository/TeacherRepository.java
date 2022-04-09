package com.example.lab3_behind.repository;

import com.example.lab3_behind.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findByJob_number(String Job_number);
    Teacher findByID_num(String ID_num);
    Teacher deleteByJob_number(String Job_number);
}
