package com.example.lab3_behind.repository;

import com.example.lab3_behind.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByStu_number(String Stu_number);
    Student findByID_num(String ID_num);
    Student deleteByStu_number(String Stu_number);
}
