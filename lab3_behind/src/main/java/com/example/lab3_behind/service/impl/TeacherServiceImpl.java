package com.example.lab3_behind.service.impl;

import com.example.lab3_behind.common.FormatCheck;
import com.example.lab3_behind.common.forDomain.TeacherStatus;
import com.example.lab3_behind.domain.Major;
import com.example.lab3_behind.domain.School;
import com.example.lab3_behind.domain.Teacher;
import com.example.lab3_behind.domain.dto.RevisableDataForAdmin;
import com.example.lab3_behind.domain.dto.RevisableDataForUser;
import com.example.lab3_behind.domain.dto.UserEnteringData;
import com.example.lab3_behind.repository.MajorRepository;
import com.example.lab3_behind.repository.SchoolRepository;
import com.example.lab3_behind.repository.TeacherRepository;
import com.example.lab3_behind.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {
    TeacherRepository teacherRepository;
    SchoolRepository schoolRepository;
    MajorRepository majorRepository;
    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository, SchoolRepository schoolRepository, MajorRepository majorRepository){
        this.teacherRepository = teacherRepository;
        this.schoolRepository = schoolRepository;
        this.majorRepository = majorRepository;
    }

    @Override
    public Teacher insertTeacher(UserEnteringData userData) throws Exception {
        if(teacherRepository.findByJobNumber(userData.getNumber()) != null){
            throw new Exception("工号已注册");
        }else if(teacherRepository.findByIdNum(userData.getIdNum()) != null){
            throw new Exception("身份证号已注册");
        }
        School school = schoolRepository.findByName(userData.getSchool());
        if(school == null){
            throw new Exception("学院不存在");
        }
        if(majorRepository.findByNameAndSchool(userData.getMajor(),school) == null){
            throw new Exception("学生所属学院下不存在此专业");
        }
        Teacher teacher = new Teacher(userData, schoolRepository.findByName(userData.getSchool()), majorRepository.findByName(userData.getMajor()));
        try {
            FormatCheck.userEnteringDataCheck(userData);
        } catch (Exception e){
            throw e;
        }
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
        teacher.setMajor(majorRepository.findByName(userData.getMajor()));
        teacher.setSchool(schoolRepository.findByName(userData.getSchool()));
        teacher.getUserAccount().setPassword(userData.getPassword());
        if(!userData.getTeaStatus().equals(TeacherStatus.Normal)){
            teacher.getUserAccount().setPermission("false");
        } else {
            teacher.getUserAccount().setPermission("true");
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

    @Override
    public Page<Teacher> findAPageTeacher(Integer page, Integer size, String search){
        Pageable pageable =  PageRequest.of(page - 1, size);
        if(search.isEmpty()){
            return teacherRepository.findAll(pageable);
        }
        Teacher teacher = new Teacher();
        teacher.setName(search);
        teacher.setEmail(search);
        teacher.setPhoneNum(search);
        Major major = new Major();
        major.setName(search);
        teacher.setMajor(major);
        School school = new School();
        school.setName(search);
        teacher.setSchool(school);
        teacher.setJobNumber(search);
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("jobNum", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("email", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("phoneNum", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("major", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("school", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("idNum", ExampleMatcher.GenericPropertyMatcher::contains)
                .withIgnorePaths("id", "userAccount", "status");
        Example<Teacher> example = Example.of(teacher, matcher);
        return teacherRepository.findAll(example,pageable);
    }
}
