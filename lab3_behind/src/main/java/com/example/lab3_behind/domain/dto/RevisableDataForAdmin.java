package com.example.lab3_behind.domain.dto;

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

    public RevisableDataForAdmin(StudentUpdatingData studentUpdatingData){
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
