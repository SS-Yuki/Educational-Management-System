package com.example.lab3_behind.repository;

import com.example.lab3_behind.domain.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    Classroom findByName(String name);
}
