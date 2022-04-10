package com.example.lab3_behind.service.Impl;

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
    StudentRepository StudentRepository;
    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository){
        this.StudentRepository = studentRepository;
    }

    @Override
    public Student insertStudent(UserEnteringData userData) throws Exception {
        if(StudentRepository.findByStuNumber(userData.getNumber()) != null){
            throw new Exception("工号已注册");
        }else if(StudentRepository.findByIDNum(userData.getId_num()) != null){
            throw new Exception("身份证号已注册");
        }
        Student student = new Student(userData);
        StudentRepository.save(student);
        return student;
    }

    @Override
    public Student updateStudentInfo(RevisableDataForAdmin userData, String stuNumber) throws Exception {
        Student student = StudentRepository.findByStuNumber(stuNumber);
        if(student == null){
            throw new Exception("该用户不存在");
        }
        student.setID_num(userData.getId_num());
        student.setEmail(userData.getEmail());
        student.setName(userData.getName());
        student.setPhone_num(userData.getPhone_num());
        student.setStatus(userData.getStu_status());
        student.getUserAccount().setPassword(userData.getPassword());
        StudentRepository.save(student);
        return student;
    }

    @Override
    public Student maintainStudentInfo(RevisableDataForUser userData, String stuNumber) throws Exception {
        Student student = StudentRepository.findByStuNumber(stuNumber);
        if(student == null){
            throw new Exception("该用户不存在");
        }
        student.setEmail(userData.getEmail());
        student.setPhone_num(userData.getPhone_num());
        student.getUserAccount().setPassword(userData.getPassword());
        StudentRepository.save(student);
        return student;
    }

}
