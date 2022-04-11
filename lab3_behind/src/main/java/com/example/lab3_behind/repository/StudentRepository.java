package com.example.lab3_behind.repository;

import com.example.lab3_behind.domain.Student;
import com.sun.xml.bind.v2.runtime.reflect.Lister;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByStuNumber(String stuNumber);
    Student findByIdNum(String idNum);
    Student deleteByStuNumber(String stuNumber);
}
