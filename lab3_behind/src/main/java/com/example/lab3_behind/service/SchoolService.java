package com.example.lab3_behind.service;

import com.example.lab3_behind.domain.Major;
import com.example.lab3_behind.domain.School;
import com.example.lab3_behind.domain.dto.MajorAddingData;
import com.example.lab3_behind.domain.dto.SchoolAddingData;
import com.example.lab3_behind.domain.dto.SchoolAndMajorsData;

import java.util.List;

public interface SchoolService {
    List<SchoolAndMajorsData> getAllSchoolAndMajors();
    Major insertMajor(MajorAddingData majorData) throws Exception;
    School insertSchool(SchoolAddingData schoolData) throws Exception;
}
