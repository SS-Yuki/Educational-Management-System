package com.example.lab3_behind.domain.vo;

import com.example.lab3_behind.common.StudentStatus;
import com.example.lab3_behind.common.TeacherStatus;
import lombok.Data;

@Data
public class StudentUpdatingData {
    private String stuNumber;
    private String name;

    private String password;

    private String idNum;

    private String phoneNum;

    private String email;

    private String stuStatus;

    private String school;

    private String major;
}
