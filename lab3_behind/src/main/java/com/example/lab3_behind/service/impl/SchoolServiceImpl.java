package com.example.lab3_behind.service.impl;

import com.example.lab3_behind.domain.Major;
import com.example.lab3_behind.domain.School;
import com.example.lab3_behind.domain.dto.SchoolAndMajorsData;
import com.example.lab3_behind.repository.SchoolRepository;
import com.example.lab3_behind.repository.StudentRepository;
import com.example.lab3_behind.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {
    SchoolRepository schoolRepository;
    @Autowired
    public SchoolServiceImpl(SchoolRepository schoolRepository){
        this.schoolRepository = schoolRepository;
    }

    @Override
    public List<SchoolAndMajorsData> getAllSchoolAndMajors(){
        List<SchoolAndMajorsData> results = new ArrayList<>();
        List<School> schools = schoolRepository.findAll();
        for(School oneSchool : schools){
            SchoolAndMajorsData temp= new SchoolAndMajorsData();
            temp.setSchool(oneSchool.getName());
            List<Major> majors = oneSchool.getMajors();
            List<String> majorNames = new ArrayList<>();
            for(Major oneMajor : majors){
                majorNames.add(oneMajor.getName());
            }
            temp.setMajors(majorNames);
            results.add(temp);
        }
        return results;
    }
}
