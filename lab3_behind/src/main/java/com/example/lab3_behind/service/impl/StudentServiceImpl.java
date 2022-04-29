package com.example.lab3_behind.service.impl;

import com.example.lab3_behind.common.FormatCheck;
import com.example.lab3_behind.common.forDomain.StudentStatus;
import com.example.lab3_behind.domain.Major;
import com.example.lab3_behind.domain.School;
import com.example.lab3_behind.domain.Student;
import com.example.lab3_behind.domain.dto.RevisableDataForAdmin;
import com.example.lab3_behind.domain.dto.RevisableDataForUser;
import com.example.lab3_behind.domain.dto.UserEnteringData;
import com.example.lab3_behind.repository.MajorRepository;
import com.example.lab3_behind.repository.SchoolRepository;
import com.example.lab3_behind.repository.StudentRepository;
import com.example.lab3_behind.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    StudentRepository studentRepository;
    SchoolRepository schoolRepository;
    MajorRepository majorRepository;
    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, SchoolRepository schoolRepository, MajorRepository majorRepository){
        this.studentRepository = studentRepository;
        this.schoolRepository = schoolRepository;
        this.majorRepository = majorRepository;
    }

    @Override
    public Page<Student> findAPageStudent(Integer page, Integer size, String search){
        Pageable pageable =  PageRequest.of(page - 1, size);
        if(search.isEmpty()){
            return studentRepository.findAll(pageable);
        }
        Student student = new Student();
        student.setName(search);
        student.setEmail(search);
        student.setPhoneNum(search);
        Major major = new Major();
        major.setName(search);
        student.setMajor(major);
        School school = new School();
        school.setName(search);
        student.setSchool(school);
        student.setStuNumber(search);
        student.setIdNum(search);
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("stuNum", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("email", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("phoneNum", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("major", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("school", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("idNum", ExampleMatcher.GenericPropertyMatcher::contains)
                .withIgnorePaths("id", "userAccount", "status");
        Example<Student> example = Example.of(student, matcher);
        return studentRepository.findAll(example,pageable);
    }

    @Override
    public Student insertStudent(UserEnteringData userData) throws Exception {
        if(studentRepository.findByStuNumber(userData.getNumber()) != null){
            throw new Exception("工号已注册");
        }
        if(studentRepository.findByIdNum(userData.getIdNum()) != null){
            throw new Exception("身份证号已注册");
        }
        School school = schoolRepository.findByName(userData.getSchool());
        if(school == null){
            throw new Exception("学院不存在");
        }
        if(majorRepository.findByNameAndSchool(userData.getMajor(),school) == null){
            throw new Exception("教师所属学院下不存在此专业");
        }
        Student student = new Student(userData, schoolRepository.findByName(userData.getSchool()), majorRepository.findByName(userData.getMajor()));
        try {
            FormatCheck.userEnteringDataCheck(userData);
        } catch (Exception e){
            throw e;
        }
        studentRepository.save(student);
        return student;
    }

    @Override
    public Student updateStudentInfo(RevisableDataForAdmin userData, String stuNumber) throws Exception {
        Student student = studentRepository.findByStuNumber(stuNumber);
        if(student == null){
            throw new Exception("该用户不存在");
        }
        student.setIdNum(userData.getIdNum());
        student.setEmail(userData.getEmail());
        student.setName(userData.getName());
        student.setPhoneNum(userData.getPhoneNum());
        student.setStatus(userData.getStuStatus());
        student.setMajor(majorRepository.findByName(userData.getMajor()));
        student.setSchool(schoolRepository.findByName(userData.getSchool()));
        student.getUserAccount().setPassword(userData.getPassword());
        if(!userData.getStuStatus().equals(StudentStatus.Normal)){
            student.getUserAccount().setPermission("false");
        } else {
            student.getUserAccount().setPermission("true");
        }
        studentRepository.save(student);
        return student;
    }

    @Override
    public Student maintainStudentInfo(RevisableDataForUser userData, String stuNumber) throws Exception {
        Student student = studentRepository.findByStuNumber(stuNumber);
        if(student == null){
            throw new Exception("该用户不存在");
        }
        student.setEmail(userData.getEmail());
        student.setPhoneNum(userData.getPhoneNum());
        studentRepository.save(student);
        return student;
    }

    @Override
    public Student getByStuNumber(String stuNumber) throws Exception {
        Student student = studentRepository.findByStuNumber(stuNumber);
        if(student == null){
            throw new Exception("该用户不存在");
        }
        return student;
    }

}
