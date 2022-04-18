package com.example.lab3_behind.repository;

import com.example.lab3_behind.domain.Major;
import com.example.lab3_behind.domain.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {
    Major findByNameAndSchool(String name, School school);
    Major findByName(String name);
}
