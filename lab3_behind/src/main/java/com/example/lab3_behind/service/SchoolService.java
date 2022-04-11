package com.example.lab3_behind.service;

import com.example.lab3_behind.domain.Major;
import com.example.lab3_behind.domain.dto.MajorAddingData;
import com.example.lab3_behind.domain.dto.SchoolAndMajorsData;

import java.util.List;

public interface SchoolService {
    List<SchoolAndMajorsData> getAllSchoolAndMajors();
    Major insertMajor(MajorAddingData majorData) throws Exception;
}
