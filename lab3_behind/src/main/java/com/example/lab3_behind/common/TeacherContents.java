package com.example.lab3_behind.common;


import com.example.lab3_behind.common.forDomain.TeacherStatus;
import com.example.lab3_behind.domain.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherContents {

    private String name;
    private String password;
    private String number;
    private String idNum;
    private String phoneNum;
    private String email;
    private String teaStatus;
    private String school;
    private String major;

    public static List<TeacherContents> getContents(List<Teacher> teachers){
        List<TeacherContents> teacherContents = new ArrayList<>();
        for(Teacher teacher:teachers){
            TeacherContents temp = new TeacherContents();
            temp.setName(teacher.getName());
            temp.setNumber(teacher.getJobNumber());
            temp.setIdNum(teacher.getIdNum());
            temp.setPhoneNum(teacher.getPhoneNum());
            temp.setEmail(teacher.getEmail());
            if(teacher.getStatus().equals(TeacherStatus.Dimission)){
                temp.setTeaStatus("Dimission");
            }
            else temp.setTeaStatus("Normal");
            temp.setSchool(teacher.getSchool().getName());
            temp.setMajor(teacher.getMajor().getName());
            temp.setPassword(teacher.getUserAccount().getPassword());
            teacherContents.add(temp);
        }
        return teacherContents;
    }
}
