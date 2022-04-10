package com.example.lab3_behind.service.impl;

import com.example.lab3_behind.common.TeacherStatus;
import com.example.lab3_behind.domain.Teacher;
import com.example.lab3_behind.domain.dto.RevisableDataForAdmin;
import com.example.lab3_behind.domain.dto.RevisableDataForUser;
import com.example.lab3_behind.domain.dto.UserEnteringData;
import com.example.lab3_behind.repository.TeacherRepository;
import com.example.lab3_behind.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {
    TeacherRepository TeacherRepository;
    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository){
        this.TeacherRepository = teacherRepository;
    }

    @Override
    public Teacher insertTeacher(UserEnteringData userData) throws Exception {
        if(TeacherRepository.findByJobNumber(userData.getNumber()) != null){
            throw new Exception("工号已注册");
        }else if(TeacherRepository.findByIdNum(userData.getId_num()) != null){
            throw new Exception("身份证号已注册");
        }
        Teacher teacher = new Teacher(userData);
        TeacherRepository.save(teacher);
        return teacher;
    }

    @Override
    public Teacher updateTeacherInfo(RevisableDataForAdmin userData, String jobNumber) throws Exception {
        Teacher teacher = TeacherRepository.findByJobNumber(jobNumber);
        if(teacher == null){
            throw new Exception("该用户不存在");
        }
        teacher.setIdNum(userData.getId_num());
        teacher.setEmail(userData.getEmail());
        teacher.setName(userData.getName());
        teacher.setPhoneNum(userData.getPhone_num());
        teacher.setStatus(userData.getTea_status());
        teacher.setMajor(userData.getMajor());
        teacher.setSchool(userData.getSchool());
        teacher.getUserAccount().setPassword(userData.getPassword());
        if(!userData.getTea_status().equals(TeacherStatus.Normal)){
            teacher.getUserAccount().setPermission("false");
        }
        TeacherRepository.save(teacher);
        return teacher;
    }

    @Override
    public Teacher maintainTeacherInfo(RevisableDataForUser userData, String jobNumber) throws Exception {
        Teacher teacher = TeacherRepository.findByJobNumber(jobNumber);
        if(teacher == null){
            throw new Exception("该用户不存在");
        }
        teacher.setEmail(userData.getEmail());
        teacher.setPhoneNum(userData.getPhone_num());
        teacher.getUserAccount().setPassword(userData.getPassword());
        TeacherRepository.save(teacher);
        return teacher;
    }
}
