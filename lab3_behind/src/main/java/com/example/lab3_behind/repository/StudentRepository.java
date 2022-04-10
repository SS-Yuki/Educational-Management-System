package com.example.lab3_behind.repository;

import com.example.lab3_behind.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByStuNumber(String Stu_number);
    Student findByIDNum(String ID_num);
    Student deleteByStuNumber(String Stu_number);
}
