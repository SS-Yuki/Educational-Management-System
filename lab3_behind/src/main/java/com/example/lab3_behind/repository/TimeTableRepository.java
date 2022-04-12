package com.example.lab3_behind.repository;

import com.example.lab3_behind.domain.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {
    TimeTable findByName(String name);
}
