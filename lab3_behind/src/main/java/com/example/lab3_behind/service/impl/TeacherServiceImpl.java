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
    TeacherRepository teacherRepository;
    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository){
        this.teacherRepository = teacherRepository;
    }

    @Override
    public Teacher insertTeacher(UserEnteringData userData) throws Exception {
        if(teacherRepository.findByJobNumber(userData.getNumber()) != null){
            throw new Exception("工号已注册");
        }else if(teacherRepository.findByIdNum(userData.getIdNum()) != null){
            throw new Exception("身份证号已注册");
        }
        Teacher teacher = new Teacher(userData);
        teacherRepository.save(teacher);
        return teacher;
    }

    @Override
    public Teacher updateTeacherInfo(RevisableDataForAdmin userData, String jobNumber) throws Exception {
        Teacher teacher = teacherRepository.findByJobNumber(jobNumber);
        if(teacher == null){
            throw new Exception("该用户不存在");
        }
        teacher.setIdNum(userData.getIdNum());
        teacher.setEmail(userData.getEmail());
        teacher.setName(userData.getName());
        teacher.setPhoneNum(userData.getPhoneNum());
        teacher.setStatus(userData.getTeaStatus());
        teacher.setMajor(userData.getMajor());
        teacher.setSchool(userData.getSchool());
        teacher.getUserAccount().setPassword(userData.getPassword());
        if(!userData.getTeaStatus().equals(TeacherStatus.Normal)){
            teacher.getUserAccount().setPermission("false");
        }
        teacherRepository.save(teacher);
        return teacher;
    }

    @Override
    public Teacher maintainTeacherInfo(RevisableDataForUser userData, String jobNumber) throws Exception {
        Teacher teacher = teacherRepository.findByJobNumber(jobNumber);
        if(teacher == null){
            throw new Exception("该用户不存在");
        }
        teacher.setEmail(userData.getEmail());
        teacher.setPhoneNum(userData.getPhoneNum());
        teacherRepository.save(teacher);
        return teacher;
    }

    @Override
    public Teacher getByJobNumber(String jobNumber)throws Exception{
        Teacher teacher = teacherRepository.findByJobNumber(jobNumber);
        if(teacher==null){
            throw new Exception("该工号不存在");
        }
        return teacher;
    }
}
