package com.example.lab3_behind.service;

import com.example.lab3_behind.domain.Major;
import com.example.lab3_behind.domain.School;
import com.example.lab3_behind.domain.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SchoolService {
    List<String> getAllSchool();
    List<SchoolAndMajorsData> getAllSchoolAndMajors();
    Page<Major> findAPageMajor(Integer page, Integer size, String search);
    Page<School> findAPageSchool(Integer page, Integer size, String search);
    Major insertMajor(MajorAddingData majorData) throws Exception;
    Major updateMajor(MajorUpdatingData majorData) throws Exception;
    Major deleteMajor(String majorName, String schoolName) throws Exception;
    School insertSchool(SchoolAddingData schoolData) throws Exception;
    School updateSchool(SchoolUpdatingData schoolData) throws Exception;
    School deleteSchool(String schoolName) throws Exception;
}
