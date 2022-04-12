package com.example.lab3_behind.repository;

import com.example.lab3_behind.domain.TeachingBuilding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeachingBuildingRepository extends JpaRepository<TeachingBuilding, Long> {
    TeachingBuilding findByName(String name);
}
