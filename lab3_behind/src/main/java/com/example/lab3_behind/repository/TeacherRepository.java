package com.example.lab3_behind.repository;

import com.example.lab3_behind.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findByJobNumber(String Job_number);
    Teacher findByIDNum(String ID_num);
    Teacher deleteByJobNumber(String Job_number);
}
