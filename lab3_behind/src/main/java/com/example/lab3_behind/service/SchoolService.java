package com.example.lab3_behind.service;

import com.example.lab3_behind.domain.Major;
import com.example.lab3_behind.domain.School;
import com.example.lab3_behind.domain.dto.*;

import java.util.List;

public interface SchoolService {
    List<SchoolAndMajorsData> getAllSchoolAndMajors();
    Major insertMajor(MajorAddingData majorData) throws Exception;
    Major updateMajor(MajorUpdatingData majorData);
    Major deleteMajor(String majorName);
    School insertSchool(SchoolAddingData schoolData) throws Exception;
    School updateSchool(SchoolUpdatingData schoolData);
    School deleteSchool(String schoolName);
}
