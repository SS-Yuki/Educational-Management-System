package com.example.lab3_behind.domain.dto;

import com.example.lab3_behind.common.forDomain.Grade;
import com.example.lab3_behind.common.forDomain.StudentStatus;
import com.example.lab3_behind.common.forDomain.TeacherStatus;
import com.example.lab3_behind.domain.vo.StudentUpdatingData;
import com.example.lab3_behind.domain.vo.TeacherUpdatingData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevisableDataForAdmin{

    private String name;

    private String password;

    private String idNum;

    private String phoneNum;

    private String email;

    private StudentStatus stuStatus;

    private TeacherStatus teaStatus;

    private String school;

    private String major;

    private Grade grade;

    public RevisableDataForAdmin(StudentUpdatingData studentUpdatingData) throws Exception {
        this.name = studentUpdatingData.getName();
        this.password = studentUpdatingData.getPassword();
        this.idNum = studentUpdatingData.getIdNum();
        this.phoneNum = studentUpdatingData.getPhoneNum();
        this.email = studentUpdatingData.getEmail();
        if(studentUpdatingData.getStuStatus().equals("Graduated")){
            this.stuStatus = StudentStatus.Graduated;
        }
        else this.stuStatus = StudentStatus.Normal;
        this.school = studentUpdatingData.getSchool();
        this.major = studentUpdatingData.getMajor();

        switch (studentUpdatingData.getGrade()){
            case "2019":grade = Grade.G2019;break;
            case "2020":grade = Grade.G2020;break;
            case "2021":grade = Grade.G2021;break;
            case "2022":grade = Grade.G2022;break;
            default:throw new Exception("年级信息错误");
        }
    }

    public RevisableDataForAdmin(TeacherUpdatingData teacherUpdatingData){
        this.name = teacherUpdatingData.getName();
        this.password = teacherUpdatingData.getPassword();
        this.idNum = teacherUpdatingData.getIdNum();
        this.phoneNum = teacherUpdatingData.getPhoneNum();
        this.email = teacherUpdatingData.getEmail();
        if(teacherUpdatingData.getTeaStatus().equals("Dimission")){
            this.teaStatus = TeacherStatus.Dimission;
        }
        else this.teaStatus = TeacherStatus.Normal;
        this.school = teacherUpdatingData.getSchool();
        this.major = teacherUpdatingData.getMajor();
    }
}
