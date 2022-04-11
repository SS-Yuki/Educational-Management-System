package com.example.lab3_behind.service;

import com.example.lab3_behind.domain.dto.SchoolAndMajorsData;

import java.util.List;

public interface SchoolService {
    List<SchoolAndMajorsData> getAllSchoolAndMajors();
}
