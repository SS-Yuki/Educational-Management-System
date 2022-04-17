package com.example.lab3_behind.service.impl;

import com.example.lab3_behind.domain.Major;
import com.example.lab3_behind.domain.School;
import com.example.lab3_behind.domain.Student;
import com.example.lab3_behind.domain.Teacher;
import com.example.lab3_behind.domain.dto.*;
import com.example.lab3_behind.repository.MajorRepository;
import com.example.lab3_behind.repository.SchoolRepository;
import com.example.lab3_behind.repository.StudentRepository;
import com.example.lab3_behind.repository.TeacherRepository;
import com.example.lab3_behind.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {
    SchoolRepository schoolRepository;
    MajorRepository majorRepository;
    TeacherRepository teacherRepository;
    StudentRepository studentRepository;
    @Autowired
    public SchoolServiceImpl(SchoolRepository schoolRepository, MajorRepository majorRepository,
    TeacherRepository teacherRepository, StudentRepository studentRepository){
        this.schoolRepository = schoolRepository;
        this.majorRepository = majorRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<String> getAllSchool(){
        List<String> results = new ArrayList<>();
        List<School> schools = schoolRepository.findAll();
        for(School school : schools){
            results.add(school.getName());
        }
        return results;
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

    @Override
    public Major insertMajor(MajorAddingData majorData) throws Exception {
        School school = schoolRepository.findByName(majorData.getSchoolName());
        if(school == null){
            throw new Exception("学院信息错误，学院不存在");
        }
        Major major = new Major(null, majorData.getMajorName(), school, majorData.getIntroduction());
        school.getMajors().add(major);
//        Major major = new Major();
//        major.setSchool(school);
//        major.setName(majorData.getMajorName());
//        major.setIntroduction(majorData.getIntroduction());
//        school.getMajors().add(major);
        schoolRepository.save(school);
        return major;
    }

    @Override
    public School insertSchool(SchoolAddingData schoolData) throws Exception {
        School school = schoolRepository.findByName(schoolData.getSchoolName());
        if(school != null){
            throw new Exception("学院已存在");
        }
        School newSchool = new School();
        newSchool.setName(schoolData.getSchoolName());
        newSchool.setIntroduction(schoolData.getIntroduction());
        schoolRepository.save(newSchool);
        return newSchool;
    }

    @Override
    public Major updateMajor(MajorUpdatingData majorData) throws Exception {
        School oldSchool = schoolRepository.findByName(majorData.getMajorOldSchool());
        if(oldSchool == null){
            throw new Exception("专业信息错误，原学院不存在");
        }
        Major oldMajor = majorRepository.findByNameAndSchool(majorData.getMajorOldName(), oldSchool);
        if(oldMajor == null){
            throw new Exception("该专业不存在");
        }
        School newSchool = schoolRepository.findByName(majorData.getMajorNewSchool());
        if(newSchool == null){
            throw new Exception("专业信息错误，新学院不存在");
        }
        if(oldSchool.equals(newSchool)){
            int oldIndex = newSchool.getMajors().indexOf(oldMajor);
            Major thisMajor = newSchool.getMajors().get(oldIndex);
            thisMajor.setName(majorData.getMajorNewName());
            thisMajor.setIntroduction((majorData.getIntroduction()));
            schoolRepository.save(newSchool);
            return thisMajor;
        }else{
            this.deleteMajor(majorData.getMajorOldName(),majorData.getMajorOldSchool());
            return this.insertMajor(new MajorAddingData(majorData.getMajorOldName(), majorData.getMajorNewSchool(), majorData.getIntroduction()));
        }
    }

    @Override
    public Major deleteMajor(String majorName, String schoolName) throws Exception {
        School school = schoolRepository.findByName(schoolName);
        if(school == null){
            throw new Exception("专业信息有误，学院不存在");
        }
        Student student = studentRepository.findByMajor(majorName);
        if(student != null){
            throw new Exception("该专业下有学生存在，无法删除");
        }
        Teacher teacher = teacherRepository.findByMajor(majorName);
        if(teacher != null){
            throw new Exception("该专业下有教师存在，无法删除");
        }
        Major major = majorRepository.findByNameAndSchool(majorName, school);
        Major thisMajor = school.getMajors().get(school.getMajors().indexOf(major));
        school.getMajors().remove(thisMajor);
        majorRepository.delete(thisMajor);
        schoolRepository.save(school);
        return thisMajor;
    }

    @Override
    public School updateSchool(SchoolUpdatingData schoolData) throws Exception {
        School school = schoolRepository.findByName(schoolData.getOldName());
        if(school == null){
            throw new Exception("学院不存在");
        }
        school.setName(schoolData.getNewName());
        school.setIntroduction(schoolData.getIntroduction());
        schoolRepository.save(school);
        return school;
    }

    @Override
    public School deleteSchool(String schoolName) throws Exception {
        School school = schoolRepository.findByName(schoolName);
        if(school == null){
            throw new Exception("学院不存在");
        }
        Student student = studentRepository.findBySchool(schoolName);
        if(student != null){
            throw new Exception("该学院下有学生存在，无法删除");
        }
        Teacher teacher = teacherRepository.findBySchool(schoolName);
        if(teacher != null){
            throw new Exception("该学院下有教师存在，无法删除");
        }
        schoolRepository.delete(school);
        return school;
    }

    @Override
    public Page<School> findAPageSchool(Integer page, Integer size, String search){
        Pageable pageable =  PageRequest.of(page - 1, size);
        if(search.isEmpty()){
            return schoolRepository.findAll(pageable);
        }
        School school = new School();
        school.setName(search);
        school.setIntroduction(search);
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withIgnoreCase(true)
                .withMatcher("name", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("introduction", ExampleMatcher.GenericPropertyMatcher::contains)
                .withIgnorePaths("id");
        Example<School> example = Example.of(school, matcher);
        return schoolRepository.findAll(example,pageable);
    }

    @Override
    public Page<Major> findAPageMajor(Integer page, Integer size, String search){
        Pageable pageable =  PageRequest.of(page - 1, size);
        if(search.isEmpty()){
            return majorRepository.findAll(pageable);
        }
        Major major = new Major();
        major.setName(search);
        major.setIntroduction(search);
        School school = new School();
        school.setName(search);
        major.setSchool(school);
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("introduction", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("school", ExampleMatcher.GenericPropertyMatcher::contains)
                .withIgnorePaths("id");
        Example<Major> example = Example.of(major, matcher);
        return majorRepository.findAll(example,pageable);
    }
}
