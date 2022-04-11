package com.example.lab3_behind.service.impl;

import com.example.lab3_behind.common.StudentStatus;
import com.example.lab3_behind.domain.Student;
import com.example.lab3_behind.domain.dto.RevisableDataForAdmin;
import com.example.lab3_behind.domain.dto.RevisableDataForUser;
import com.example.lab3_behind.domain.dto.UserEnteringData;
import com.example.lab3_behind.repository.StudentRepository;
import com.example.lab3_behind.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    StudentRepository studentRepository;
    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    @Override
    public Student insertStudent(UserEnteringData userData) throws Exception {
        if(studentRepository.findByStuNumber(userData.getNumber()) != null){
            throw new Exception("工号已注册");
        }else if(studentRepository.findByIdNum(userData.getIdNum()) != null){
            throw new Exception("身份证号已注册");
        }
        Student student = new Student(userData);
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
        student.setMajor(userData.getMajor());
        student.setSchool(userData.getSchool());
        student.getUserAccount().setPassword(userData.getPassword());
        if(!userData.getStuStatus().equals(StudentStatus.Normal)){
            student.getUserAccount().setPermission("false");
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
